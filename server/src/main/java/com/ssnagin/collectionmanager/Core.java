package com.ssnagin.collectionmanager;


import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.commands.Command;
import com.ssnagin.collectionmanager.commands.ServerCommand;
import com.ssnagin.collectionmanager.commands.commands.*;
import com.ssnagin.collectionmanager.config.Config;
import com.ssnagin.collectionmanager.database.DatabaseCredentials;
import com.ssnagin.collectionmanager.database.DatabaseManager;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import com.ssnagin.collectionmanager.networking.serlialization.DataStream;
import com.ssnagin.collectionmanager.session.SessionManager;
import com.ssnagin.collectionmanager.threads.factory.ThreadFactoryBuilder;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@ToString
public class Core extends AbstractCore {

    private static final Logger logger = LoggerFactory.getLogger(Core.class);

    private static final String LOGO = "CollectionManager SERVER ver. " + Config.Core.VERSION;

    private DatabaseManager databaseManager;
    private DatagramSocket datagramSocket;
    private CollectionManager collectionManager;
    private SessionManager sessionManager;

    private ExecutorService requestReceiverPool; // Fixed thread pool для чтения запросов
    private ExecutorService requestProcessorPool; // Cached thread pool для обработки
    private ForkJoinPool responseSenderPool; // ForkJoinPool для отправки ответов

    @SneakyThrows
    public Core(String[] args) {
        super(args);

        this.sessionManager = SessionManager.getInstance();

        DatabaseManager.getInstance().init(
                new DatabaseCredentials(args)
        );
        this.databaseManager = DatabaseManager.getInstance();

        this.collectionManager = CollectionManager.getInstance();
        this.collectionManager.loadFromDatabase();

        this.networking = new Networking();
        this.networking.setConnectionTimeout(3000);

        registerCommands();
        registerThreads();
    }

    @SneakyThrows
    public void registerThreads() {
        this.requestReceiverPool = Executors.newFixedThreadPool(
                Config.Core.REQUEST_RECEIVER_THREADS,
                new ThreadFactoryBuilder().setNamePrefix("request-receiver-").build()
        );

        this.requestProcessorPool = Executors.newCachedThreadPool(
                new ThreadFactoryBuilder().setNamePrefix("request-processor-").build()
        );

        this.responseSenderPool = new ForkJoinPool(
                Config.Core.RESPONSE_SENDER_PARALLELISM,
                ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                null,
                true
        );
    }

    @Override
    public void start() {
        super.start();

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

        requestReceiverPool.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    byte[] receiveBuffer = new byte[Config.Networking.BUFFER_SIZE];
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    datagramSocket.receive(receivePacket);

                    // Передаем обработку в Cached pool
                    requestProcessorPool.submit(() -> processRequest(receivePacket));
                } catch (IOException e) {
                    logger.error("Error receiving UDP packet", e);
                }
            }
        });
    }

    private void processRequest(DatagramPacket receivePacket) {
        try {
            ClientRequest request = DataStream.deserialize(receivePacket.getData());

            logger.debug("{}:{} sent a package ({})",
                    receivePacket.getAddress(),
                    receivePacket.getPort(),
                    request.getId()
            );

            ServerResponse response = runCommand(request);

            logger.info(response.getResponseStatus() + " {} {}",
                    response.getId(),
                    response.getMessage().substring(0, Math.min(response.getMessage().length(), 100))
            );


            responseSenderPool.submit(() -> {
                try {
                    networking.setInetAddress(receivePacket.getAddress());
                    networking.setPort(receivePacket.getPort());
                    networking.sendServerResponse(response);
                } catch (IOException e) {
                    logger.error("Error sending response", e);
                }
            });
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error processing request", e);
        }
    }

    protected ServerResponse runCommand(ClientRequest clientRequest) {
        logger.debug(clientRequest.toString());

        logger.debug(sessionManager.getActiveSessions().toString());

        ServerResponse result = new ServerResponse();

        Command command = this.commandManager.get(clientRequest.getParsedString().getCommand());

        if (!(command instanceof ServerCommand))
            return result;

        ServerCommand serverCommand = (ServerCommand) command;

        if (serverCommand.isAccessible())
            result = serverCommand.executeCommand(clientRequest);

        logger.debug(result.toString());

        return result;
    }

    private void registerCommands() {
        this.commandManager.register(new CommandAdd("add" , collectionManager));
        this.commandManager.register(new CommandShow("show" , collectionManager));
        this.commandManager.register(new CommandClear("clear" , collectionManager));
        this.commandManager.register(new CommandUpdate("update" , collectionManager));
        this.commandManager.register(new CommandRemoveById("remove_by_id" , collectionManager));
        this.commandManager.register(new CommandAddIfMin("add_if_min" , collectionManager));
        this.commandManager.register(new CommandCountByNumberOfParticipants("count_by_number_of_participants" , collectionManager));
        this.commandManager.register(new CommandRandom("random" , collectionManager));

        this.commandManager.register(new CommandLogin("login", databaseManager, sessionManager));
        this.commandManager.register(new CommandRegister("register", databaseManager));
    }

    @Override
    public void onExit() {

        requestReceiverPool.shutdownNow();
        requestProcessorPool.shutdownNow();
        responseSenderPool.shutdown();

        try {
            if (!requestReceiverPool.awaitTermination(5, TimeUnit.SECONDS)) {
                logger.warn("Request receiver pool did not terminate gracefully");
            }
            if (!requestProcessorPool.awaitTermination(5, TimeUnit.SECONDS)) {
                logger.warn("Request processor pool did not terminate gracefully");
            }
            if (!responseSenderPool.awaitTermination(5, TimeUnit.SECONDS)) {
                logger.warn("Response sender pool did not terminate gracefully");
            }
        } catch (InterruptedException e) {
            logger.warn("Interrupted during pool shutdown", e);
        }

        // Some code here, for example saving json.
        logger.info("Bye, have a great time!");
        System.exit(0);
    }
}