package com.ssnagin.collectionmanager.commands;

import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ServerCommand extends Command {

    protected static final Logger logger = LoggerFactory.getLogger(ServerCommand.class);

    public ServerCommand(String name, String description) {
        super(name, description);
    }

    public abstract ServerResponse executeCommand(ClientRequest clientRequest);
}
