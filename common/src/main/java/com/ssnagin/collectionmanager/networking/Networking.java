package com.ssnagin.collectionmanager.networking;

import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;
import com.ssnagin.collectionmanager.networking.serlialization.DataStream;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.*;

public class Networking {

    private static final int BUFFER_SIZE = 65536;

    @Getter
    private int connectionTimeout;
    @Setter
    private DatagramSocket datagramSocket;
    @Getter
    private InetAddress inetAddress;
    @Getter
    @Setter
    private int port;

    public Networking() throws SocketException {
        setDatagramSocket(new DatagramSocket());
    }

    public Networking(String host, int port) throws UnknownHostException, SocketException {
        this(host, port, 3000);
    }

    public Networking(String host, int port, int connectionTimeout) throws UnknownHostException, SocketException {
        setInetAddress(host);
        setPort(port);
        setDatagramSocket(new DatagramSocket());
        setConnectionTimeout(connectionTimeout);
    }

    public Networking(InetAddress address, int port, int connectionTimeout) throws SocketException {


    }

    public void setInetAddress(String address) throws UnknownHostException {
        setInetAddress(InetAddress.getByName(address));
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public ServerResponse sendClientRequest(ClientRequest request) throws IOException, ClassNotFoundException, SocketTimeoutException {
        byte[] requestData = DataStream.serialize(request);
        byte[] responseBuffer = new byte[BUFFER_SIZE];

        DatagramPacket requestPacket = new DatagramPacket(
                requestData,
                requestData.length,
                inetAddress,
                port
        );

        datagramSocket.send(requestPacket);

        DatagramPacket receivePacket = new DatagramPacket(
                responseBuffer,
                responseBuffer.length
        );
        this.datagramSocket.receive(receivePacket);

        return DataStream.deserialize(receivePacket.getData());
    }

    public void sendServerResponse(ServerResponse serverResponse) throws IOException {
        byte[] responseData = DataStream.serialize(serverResponse);

        DatagramPacket responsePacket = new DatagramPacket(
                responseData,
                responseData.length,
                inetAddress,
                port
        );

        this.datagramSocket.send(responsePacket);
    }

    public void close() {
        if (datagramSocket != null && !datagramSocket.isClosed())
            datagramSocket.close();
    }

    public void setConnectionTimeout(int connectionTimeout) throws SocketException {
        this.connectionTimeout = connectionTimeout;
        this.datagramSocket.setSoTimeout(connectionTimeout);
    }
}