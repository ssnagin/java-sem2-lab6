package com.ssnagin.collectionmanager.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import com.ssnagin.collectionmanager.networking.wrappers.SessionClientRequest;
import lombok.Getter;
import lombok.Setter;

// ALL SERVER COLLECTION COMMANDS REQUIRE AUTHENTICATION

@Setter
@Getter
public abstract class ServerCollectionCommand extends ServerCommand {

    private static String UNAUTHORIZED = "Unauthorized user. Please, log in first.";

    protected CollectionManager collectionManager;

    public ServerCollectionCommand(String name, CollectionManager collectionManager) {
        super(name);
        setCollectionManager(collectionManager);
    }

    public ServerResponse executeCommand(ClientRequest clientRequest) {
        ServerResponse serverResponse = new ServerResponse(ResponseStatus.OK);

        if (!(clientRequest instanceof SessionClientRequest))
            return serverResponse.error(UNAUTHORIZED);

        // Проверка внутри SessionManager

        return serverResponse;
    }
}
