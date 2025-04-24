package com.ssnagin.collectionmanager.core;


import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.commands.ServerCommand;
import com.ssnagin.collectionmanager.commands.commands.*;
import com.ssnagin.collectionmanager.config.Config;
import com.ssnagin.collectionmanager.files.FileManager;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;
import com.ssnagin.collectionmanager.networking.serlializer.DataStream;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.TreeSet;

@ToString
public class ServerCore extends Core {
    @Getter
    private static ServerCore instance = new ServerCore();

    private static final Logger logger = LoggerFactory.getLogger(ServerCore.class);
    protected FileManager fileManager;

    private static final String LOGO = "CollectionManager SERVER ver. " + Config.Core.VERSION;


    @Getter
    @Setter
    protected String collectionPath = "collection.json";

    private DatagramSocket datagramSocket;

    public ServerCore() {
        super();

        this.fileManager = FileManager.getInstance();

        registerCommands();
    }

    @Override
    public void start(String[] args) {
        logger.info(LOGO);

        // 1. Load file if given here:

        if (args.length > 0) {
            String path = String.join("", args);
            try {
                TreeSet<MusicBand> elements = fileManager.readCollection(path);
                this.collectionManager.setCollection(elements);
            } catch (Exception e) {
                logger.warn("Error while reading file, skip adding into collection / {}", String.valueOf(e));
            }
        }

        listening();
    }

    public void listening() {

        ServerResponse response;

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

                response = runCommand(request);

                logger.info(
                        response.getResponseStatus() + " {} {}",
                        response.getId(),
                        response.getMessage().substring(0, Math.min(response.getMessage().length(), 100))
                );

                // Change this code in the future
                this.networking = new Networking(receivePacket.getAddress(), receivePacket.getPort(), 3000);


                this.networking.sendServerResponse(response);

            } catch (IOException e) {
                logger.error("Error processing UDP packet", e);
            } catch (ClassNotFoundException e) {
                logger.error("Error ClassNotFoundException");
            }
        }
    }

    protected ServerResponse runCommand(ClientRequest clientRequest) {
        logger.debug(clientRequest.toString());
        ServerCommand command = (ServerCommand) this.commandManager.get(clientRequest.getParsedString().getCommand());

        ServerResponse result = command.executeCommand(clientRequest);
        logger.debug(result.toString());
        ((ServerCommand) this.commandManager.get("save"))
                .executeCommand(new ClientRequest());

        return result;
    }

    private void registerCommands() {
        this.commandManager.register(new CommandAdd("add", "add an object to collection", collectionManager));
        this.commandManager.register(new CommandShow("show", "show collection's elements", collectionManager));
        this.commandManager.register(new CommandClear("clear", "clear collection elements", collectionManager));
        // this.commandManager.register(new CommandUpdate("update", "update <id> | update values of selected collection by id", collectionManager, commandManager));
        // this.commandManager.register(new CommandRemoveById("remove_by_id", "remove_by_id <id> | removes an element with selected id", collectionManager));
        this.commandManager.register(new CommandAddIfMin("add_if_min", "adds an element into collection if it is the lowest element in it", collectionManager, commandManager, scriptManager));
        // this.commandManager.register(new CommandHistory("history", "shows last 9 executed commands", commandManager));
        // this.commandManager.register(new CommandPrintDescending("print_descending", "show collection's elements in reversed order", collectionManager));
        // this.commandManager.register(new CommandCountByNumberOfParticipants("count_by_number_of_participants", "count_by_number_of_participants <numberOfParticipants>| shows the amount of fields with the same amount of participants", collectionManager));
        // this.commandManager.register(new CommandRemoveLower("remove_lower", "removes elements that are lower than given", collectionManager, scriptManager));
        // this.commandManager.register(new CommandGroupCountingByCreationDate("group_counting_by_creation_date", "groups collection elements by creation date", collectionManager));
        this.commandManager.register(new CommandRandom("random", "random <amount> | adds to collection <amount> random elements", collectionManager));
    }
}