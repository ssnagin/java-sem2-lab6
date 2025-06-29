package com.ssnagin.collectionmanager.gui.commands.commands;

import com.ssnagin.collectionmanager.console.ClientConsole;
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

public class GUICommandClear extends GUINetworkCommand {
    public GUICommandClear(String name, Networking networking, TextArea textArea) {
        super(name, networking);

        setOutputText(textArea);
    }


    public void executeCommand(MouseEvent event) {
        boolean execute = InfoAlert.showConfirmationAlert(
                "Подтверждение",
                "Вы уверены что хотите стереть всё с лица Земли? (отчистить коллекцию)",
                "Это действие нельзя будет отменить."
        );

        if (!execute) return;

        ServerResponse response = null;
        try {
            response = this.networking.sendClientRequest(
                    new SessionClientRequest(
                            new ClientRequest(new ParsedString("clear", "clear", "")),
                            sessionKeyManager.getSessionKey()
                    )
            );
        } catch (IOException | ClassNotFoundException e) {
            out(e.getMessage());
        }

        if (response == null) return;

        out(response.getData() + " " + response.getMessage());

        // Кидаем event на обновление данных таблицы
        eventManager.publish(EventType.COLLECTION_DATA_CHANGED.toString(), null);
    }
}
