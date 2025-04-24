package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.commands.UserCommand;
import com.ssnagin.collectionmanager.networking.Networking;

public class UserNetworkCommand extends UserCommand {

    Networking networking;

    public UserNetworkCommand(String name, String description, Networking networking) {
        super(name, description);

        this.networking = networking;
    }
}
