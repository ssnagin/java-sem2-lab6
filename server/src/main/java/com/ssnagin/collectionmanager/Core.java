package com.ssnagin.collectionmanager;


import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.commands.Command;
import com.ssnagin.collectionmanager.commands.ServerCollectionCommand;
import com.ssnagin.collectionmanager.commands.ServerCommand;
import com.ssnagin.collectionmanager.commands.commands.*;
import com.ssnagin.collectionmanager.config.Config;
import com.ssnagin.collectionmanager.files.FileManager;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;
import com.ssnagin.collectionmanager.networking.serlialization.DataStream;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.TreeSet;

@ToString
public class Core extends AbstractCore {
    @Getter
    private static Core instance = new Core();

    private static final Logger logger = LoggerFactory.getLogger(Core.class);
    protected FileManager fileManager;

    private static final String LOGO = "CollectionManager SERVER ver. " + Config.Core.VERSION;

    private int commandSaveCounter = 0;
    private static final int MAX_COMMAND_SAVE_INTERVAL = 10;

    @Getter
    @Setter
    protected String collectionPath = "collection.json";

    private DatagramSocket datagramSocket;

    @SneakyThrows
    public Core() {
        super();

        this.fileManager = FileManager.getInstance();
//        this.networking = new Networking(receivePacket.getAddress(), receivePacket.getPort(), 3000);
        this.networking = new Networking();
        this.networking.setConnectionTimeout(3000);

        registerCommands();
    }

    @Override
    public void start(String[] args) {
        super.start(args);

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

        logger.debug(this.collectionManager.getCollection().toString());

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

                ServerResponse response = runCommand(request);

                logger.info(
                        response.getResponseStatus() + " {} {}",
                        response.getId(),
                        response.getMessage().substring(0, Math.min(response.getMessage().length(), 100))
                );

                // Change this code in the future
                networking.setInetAddress(receivePacket.getAddress());
                this.networking.setPort(receivePacket.getPort());
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

        ServerResponse result = new ServerResponse();

        Command command = this.commandManager.get(clientRequest.getParsedString().getCommand());

        if (!(command instanceof ServerCommand))
            return result;

        ServerCommand serverCommand = (ServerCommand) command;

        if (serverCommand.isAccessible())
            result = serverCommand.executeCommand(clientRequest);

        logger.debug(result.toString());

        if (commandSaveCounter > MAX_COMMAND_SAVE_INTERVAL) commandSaveCounter = 0;
        if (command instanceof ServerCollectionCommand && commandSaveCounter >= MAX_COMMAND_SAVE_INTERVAL) {
            saveCollection();
            commandSaveCounter += 1;
        }

        return result;
    }

    private void registerCommands() {
        this.commandManager.register(new CommandSave("save", collectionManager, fileManager, collectionPath));
        this.commandManager.register(new CommandAdd("add", collectionManager));
        this.commandManager.register(new CommandShow("show", collectionManager));
        this.commandManager.register(new CommandClear("clear", collectionManager));
        this.commandManager.register(new CommandUpdate("update", collectionManager));
        this.commandManager.register(new CommandRemoveById("remove_by_id", collectionManager));
        this.commandManager.register(new CommandAddIfMin("add_if_min", collectionManager));
        // this.commandManager.register(new CommandPrintDescending("print_descending", collectionManager));
        this.commandManager.register(new CommandCountByNumberOfParticipants("count_by_number_of_participants", collectionManager));
        // this.commandManager.register(new CommandRemoveLower("remove_lower", "removes elements that are lower than given", collectionManager));
        // this.commandManager.register(new CommandGroupCountingByCreationDate("group_counting_by_creation_date", collectionManager));
        this.commandManager.register(new CommandRandom("random", collectionManager));
    }

    @Override
    public void onExit() {
        // Some code here, for example saving json.
        saveCollection();
        logger.info("Bye, have a great time!");
        System.exit(0);
    }

    private void saveCollection() {
        ((ServerCommand) this.commandManager.get("save"))
                .executeCommand(new ClientRequest());
    }
}