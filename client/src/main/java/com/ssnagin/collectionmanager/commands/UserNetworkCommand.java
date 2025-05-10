package com.ssnagin.collectionmanager.commands;

import com.ssnagin.collectionmanager.networking.Networking;

public class UserNetworkCommand extends UserCommand {

    protected Networking networking;

    public UserNetworkCommand(String name, String description, Networking networking) {
        super(name, description);

        this.networking = networking;
    }
}
