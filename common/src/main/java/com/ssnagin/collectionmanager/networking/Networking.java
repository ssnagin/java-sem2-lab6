package com.ssnagin.collectionmanager.networking;

import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;
import com.ssnagin.collectionmanager.networking.serlializer.DataStream;
import lombok.Getter;

import java.io.IOException;
import java.net.*;

public class Networking {

    private static final int BUFFER_SIZE = 65536;

    @Getter
    private int connectionTimeout;

    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private int port;

    public Networking(String host, int port) throws UnknownHostException, SocketException {
        this(host, port, 3000);
    }

    public Networking(String host, int port, int connectionTimeout) throws UnknownHostException, SocketException {
        this.inetAddress = InetAddress.getByName(host);
        this.port = port;

        this.datagramSocket = new DatagramSocket();
        this.setConnectionTimeout(connectionTimeout);
    }

    public ServerResponse send(ClientRequest request) throws IOException, ClassNotFoundException, SocketTimeoutException {
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

        return DataStream.deserialize(requestPacket.getData());
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
