package com.ssnagin.collectionmanager.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.database.DatabaseManager;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import com.ssnagin.collectionmanager.networking.wrappers.SessionClientRequest;
import com.ssnagin.collectionmanager.session.SessionManager;
import com.ssnagin.collectionmanager.session.SessionStatus;
import lombok.Getter;
import lombok.Setter;

// ALL SERVER COLLECTION COMMANDS REQUIRE AUTHENTICATION

@Setter
@Getter
public abstract class ServerCollectionCommand extends ServerCommand {

    private static String UNAUTHORIZED = "Unauthorized user. Please, log in first.";
    private static String SESSION_EXPIRED = "Session expired. Please, log in again.";

    protected CollectionManager collectionManager;

    protected DatabaseManager databaseManager;

    protected SessionManager sessionManager;

    public ServerCollectionCommand(String name, CollectionManager collectionManager) {
        super(name);
        setCollectionManager(collectionManager);

        setSessionManager(SessionManager.getInstance());

        setDatabaseManager(collectionManager.getDatabaseManager());
    }

    public ServerResponse executeCommand(ClientRequest clientRequest) {
        ServerResponse serverResponse = new ServerResponse(ResponseStatus.OK);

        if (!(clientRequest instanceof SessionClientRequest))
            return serverResponse.error(UNAUTHORIZED);

        // Проверка токена внутри SessionManager

        SessionClientRequest sessionRequest = (SessionClientRequest) clientRequest;

        SessionStatus status = SessionManager.getInstance().checkAuth(sessionRequest.getSessionKey());

        return switch (status) {
            case UNAUTHORIZED -> serverResponse.error(UNAUTHORIZED);
            case EXPIRED -> serverResponse.error(SESSION_EXPIRED);
            case LOGGED_IN -> serverResponse;
            default -> serverResponse.error("Unknown session status");
        };
    }
}
