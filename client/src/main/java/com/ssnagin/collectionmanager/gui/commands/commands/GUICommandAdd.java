package com.ssnagin.collectionmanager.gui.commands.commands;

import com.ssnagin.collectionmanager.collection.model.Album;
import com.ssnagin.collectionmanager.collection.model.Coordinates;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.collection.model.MusicGenre;
import com.ssnagin.collectionmanager.collection.wrappers.LocalDateWrapper;
import com.ssnagin.collectionmanager.events.EventType;
import com.ssnagin.collectionmanager.gui.alert.InfoAlert;
import com.ssnagin.collectionmanager.gui.commands.GUINetworkCommand;
import com.ssnagin.collectionmanager.gui.nodes.form.GUIForm;
import com.ssnagin.collectionmanager.gui.nodes.form.GUIFormWithLogs;
import com.ssnagin.collectionmanager.gui.window.WindowManager;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import com.ssnagin.collectionmanager.networking.wrappers.SessionClientRequest;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.util.List;

public class GUICommandAdd extends GUINetworkCommand {

    WindowManager windowManager;

    public GUICommandAdd(String name, Networking networking, WindowManager windowManager) {
        super(name, networking);

        this.windowManager = windowManager;
    }

    public void executeCommand(MouseEvent event) {
        windowManager.get("add").show();
    }

    public void executeCommand(MouseEvent event, GUIForm form) {
        ParsedString parsedString = new ParsedString(form.getName());
        parsedString.setCommand(form.getName());

        if (form instanceof GUIFormWithLogs) {
            setOutputText(((GUIFormWithLogs) form).getLogArea());
        }

        List<Node> argsList = form.getFields();
        MusicBand musicBand = new MusicBand();

        try {
            musicBand.setName(((TextField) argsList.get(0)).getText());

            musicBand.setCoordinates(
                    new Coordinates(
                            Long.parseLong(((TextField) argsList.get(1)).getText()),
                            Integer.parseInt(((TextField) argsList.get(2)).getText())
                    )
            );
            musicBand.setNumberOfParticipants(
                    Long.parseLong(((TextField) argsList.get(3)).getText())
            );

            musicBand.setSinglesCount(
                    Integer.parseInt(((TextField) argsList.get(4)).getText())
            );

            musicBand.setGenre(
                    (MusicGenre) ((ComboBox<?>) argsList.get(5)).getValue()
            );

            musicBand.setBestAlbum(
                    new Album(
                            ((TextField) argsList.get(6)).getText(),
                            Long.parseLong(((TextField) argsList.get(7)).getText())
                    )
            );

            musicBand = new LocalDateWrapper(musicBand);

            if (parsedString.getCommand().equals("update")) {
                executeUpdateCommand(parsedString, musicBand);
                return;
            }

            SessionClientRequest request = new SessionClientRequest(
                    new ClientRequest(
                        parsedString, musicBand
                    ),
                    sessionKeyManager.getSessionKey()
            );

            ServerResponse serverResponse = networking.sendClientRequest(request);

            out(serverResponse.getMessage());

            eventManager.publish(EventType.COLLECTION_DATA_CHANGED.toString(), null);

        } catch (IllegalArgumentException | IOException | ClassNotFoundException e) {
            out(e.getMessage());
        }
    }

    private void executeUpdateCommand(ParsedString parsedString, MusicBand musicBand) {
        Integer stage;

        Long idToChange = InfoAlert.showAndWait(
                "Введите число (Long)",
                "Пожалуйста, введите целое число",
                "Число для ввода:"
        );
        if (idToChange == null) return;
        parsedString.addArgument(idToChange.toString());

        musicBand.setId(idToChange);

        try {
            ServerResponse serverResponse = networking.sendClientRequest(
                new SessionClientRequest(
                    new ClientRequest(
                            parsedString, idToChange, 1
                    ),
                    sessionKeyManager.getSessionKey()
                )
            );

            if (serverResponse.getResponseStatus() != ResponseStatus.OK) {
                out(serverResponse.getMessage());
                return;
            }

            stage = serverResponse.getStage();

            serverResponse = this.networking.sendClientRequest(
                    new SessionClientRequest(
                            new ClientRequest(
                                    parsedString,
                                    musicBand,
                                    stage
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
