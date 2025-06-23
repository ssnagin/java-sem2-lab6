package com.ssnagin.collectionmanager.commands;

import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.session.SessionKeyManager;

public abstract class UserNetworkCommand extends UserCommand {

    protected SessionKeyManager sessionKeyManager = SessionKeyManager.getInstance();

    protected Networking networking;

    public UserNetworkCommand(String name, String description, Networking networking) {
        super(name, description);

        this.networking = networking;
    }
}
