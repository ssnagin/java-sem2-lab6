package com.ssnagin.collectionmanager.gui.commands;

import com.ssnagin.collectionmanager.commands.Command;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.session.SessionKeyManager;

public abstract class GUINetworkCommand extends GUICommand {

    protected SessionKeyManager sessionKeyManager = SessionKeyManager.getInstance();

    protected Networking networking;

    public GUINetworkCommand(String name, String description, Networking networking) {
        super(name, description);

        this.networking = networking;
    }

    public GUINetworkCommand(String name, Networking networking) {
        this(name, "", networking);
    }
}
