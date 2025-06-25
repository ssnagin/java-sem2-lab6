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

public class GUICommandRandom extends GUINetworkCommand {

    public GUICommandRandom(String name, Networking networking, TextArea textArea) {
        super(name, networking);

        setOutputText(textArea);
    }

    public void executeCommand(MouseEvent event) {
        Long randomNum = InfoAlert.showAndWait(
                "Введите число",
                "Пожалуйста, введите целое число от 1 до 50",
                "Число для ввода:"
        );
        if (randomNum == null) return;

        try {
            ServerResponse response = this.networking.sendClientRequest(
                    new SessionClientRequest(
                            new ClientRequest(
                                    new ParsedString("random", "random", randomNum.toString()),
                                    randomNum
                            ),
                            sessionKeyManager.getSessionKey()
                    )
            );

            out(response.getMessage());

            eventManager.publish(EventType.COLLECTION_DATA_CHANGED.toString(), null);
        } catch (IOException | ClassNotFoundException e) {
            out(e.getMessage());
        }

    }
}
