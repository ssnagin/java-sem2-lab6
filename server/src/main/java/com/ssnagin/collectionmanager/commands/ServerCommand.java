package com.ssnagin.collectionmanager.commands;

import com.ssnagin.collectionmanager.networking.ClientRequest;
import com.ssnagin.collectionmanager.networking.ServerResponse;

public abstract class ServerCommand extends Command {
    public ServerCommand(String name, String description) {
        super(name, description);
    }

    public abstract ServerResponse executeCommand(ClientRequest clientRequest);
}
