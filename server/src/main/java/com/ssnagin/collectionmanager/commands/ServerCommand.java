package com.ssnagin.collectionmanager.commands;

import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ServerCommand extends Command {

    protected static final Logger logger = LoggerFactory.getLogger(ServerCommand.class);

    @Getter
    @Setter
    protected boolean isAccessible = true;

    public ServerCommand(String name) {
        super(name);
    }

    public abstract ServerResponse executeCommand(ClientRequest clientRequest);
}