package com.ssnagin.collectionmanager.gui.commands.commands;

import com.ssnagin.collectionmanager.events.EventType;
import com.ssnagin.collectionmanager.gui.alert.InfoAlert;
import com.ssnagin.collectionmanager.gui.commands.GUINetworkCommand;
import com.ssnagin.collectionmanager.networking.Networking;
import javafx.scene.input.MouseEvent;

public class GUICommandLogout extends GUINetworkCommand {

    public GUICommandLogout(String name, Networking networking) {
        super(name, networking);
    }

    public void executeCommand(MouseEvent event) {
        sessionKeyManager.setSessionKey(null);

        InfoAlert.showInfoAlert("Log out", "Successfully logged out!", "");

        eventManager.publish(EventType.USER_LOGGED_OUT.toString(), null);
    }

}
