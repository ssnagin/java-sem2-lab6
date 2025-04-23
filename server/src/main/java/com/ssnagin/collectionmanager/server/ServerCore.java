package com.ssnagin.collectionmanager.server;


import com.ssnagin.collectionmanager.Core;
import com.ssnagin.collectionmanager.config.Config;
import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.serlializer.DataStream;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

@ToString
public class ServerCore extends Core {
    @Getter
    private static ServerCore instance = new ServerCore();

    private static final Logger logger = LoggerFactory.getLogger(ServerCore.class);

    private static final String LOGO = "CollectionManager SERVER ver. " + Config.General.VERSION;

    private DatagramSocket datagramSocket;

    public ServerCore() {
        super();


    }

    @Override
    public void start(String[] args) {
        logger.info(LOGO);

        listening();
    }

    public void listening() {
        try {
            datagramSocket = new DatagramSocket(Config.Networking.PORT);
        } catch (SocketException e) {
            logger.error("could not start the server on port {}", Config.Networking.PORT);
            return;
        }

        logger.info("Started listening on port {}", Config.Networking.PORT);

        while (true) {
            try {
                byte[] receiveBuffer = new byte[Config.Networking.BUFFER_SIZE];

                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                datagramSocket.receive(receivePacket);

                ClientRequest request = DataStream.deserialize(receivePacket.getData());

                logger.debug(
                "{}:{} sent a package ({})",
                        receivePacket.getAddress(),
                        receivePacket.getPort(),
                        request.getId()
                );



            } catch (IOException e) {
                logger.error("Error processing UDP packet", e);
            } catch (ClassNotFoundException e) {
                logger.error("Error ClassNotFoundException");
            }
        }
    }
}
