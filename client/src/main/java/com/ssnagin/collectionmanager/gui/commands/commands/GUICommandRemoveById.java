package com.ssnagin.collectionmanager.gui.commands.commands;

import com.ssnagin.collectionmanager.events.EventType;
import com.ssnagin.collectionmanager.gui.alert.InfoAlert;
import com.ssnagin.collectionmanager.gui.commands.GUINetworkCommand;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import com.ssnagin.collectionmanager.networking.wrappers.SessionClientRequest;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class GUICommandRemoveById extends GUINetworkCommand {

    public GUICommandRemoveById(String name, Networking networking, TextArea textArea) {
        super(name, networking);

        setOutputText(textArea);
    }

    @Override
    public void executeCommand(MouseEvent event) {
        Long idToRemove = InfoAlert.showAndWait(
                "Удаление",
                "Пожалуйста, введите целое число",
                "Число для ввода:"
        );
        if (idToRemove == null) return;

        ServerResponse serverResponse;

        try {
            serverResponse = this.networking.sendClientRequest(
                    new SessionClientRequest(
                            new ClientRequest(
                                    new ParsedString("remove_by_id", "remove_by_id", idToRemove.toString()),
                                    idToRemove
                            ),
                            sessionKeyManager.getSessionKey()
                    )
            );

            out(serverResponse.getMessage());

            eventManager.publish(EventType.COLLECTION_DATA_CHANGED.toString(), null);

        } catch (IOException | ClassNotFoundException e) {
            out(e.getMessage());
        }


    }
}
