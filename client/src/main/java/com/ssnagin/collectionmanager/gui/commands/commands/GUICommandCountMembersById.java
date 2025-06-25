package com.ssnagin.collectionmanager.gui.commands.commands;

import com.ssnagin.collectionmanager.gui.alert.InfoAlert;
import com.ssnagin.collectionmanager.gui.commands.GUINetworkCommand;
import com.ssnagin.collectionmanager.gui.window.WindowManager;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import com.ssnagin.collectionmanager.networking.wrappers.SessionClientRequest;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.IOException;


public class GUICommandCountMembersById extends GUINetworkCommand {

    private WindowManager windowManager;

    private final String TEMP_COMMAND_NAME = "count_by_number_of_participants";

    public GUICommandCountMembersById(String name, Networking networking, TextArea textArea) {
        super(name, networking);

        setOutputText(textArea);
    }

    @Override
    public void executeCommand(MouseEvent event) {
        Long numberOfParticipants = InfoAlert.showAndWait(
                "Введите число",
                "Пожалуйста, введите целое число",
                "Число для ввода:"
        );
        if (numberOfParticipants == null) return;

        ServerResponse serverResponse = null;
        try {
            serverResponse = this.networking.sendClientRequest(
                new SessionClientRequest(
                        new ClientRequest(
                            new ParsedString(TEMP_COMMAND_NAME, TEMP_COMMAND_NAME),
                            numberOfParticipants
                        ),
                        sessionKeyManager.getSessionKey()
                )
            );

            out(serverResponse.getMessage());

        } catch (IOException | ClassNotFoundException e) {
            out(e.getMessage());
        }


    }
}
