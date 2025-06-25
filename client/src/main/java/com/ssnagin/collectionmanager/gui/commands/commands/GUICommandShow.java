package com.ssnagin.collectionmanager.gui.commands.commands;

import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.console.ClientConsole;
import com.ssnagin.collectionmanager.events.EventType;
import com.ssnagin.collectionmanager.gui.commands.GUINetworkCommand;
import com.ssnagin.collectionmanager.gui.nodes.table.main.GUITableMain;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import com.ssnagin.collectionmanager.networking.wrappers.SessionClientRequest;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class GUICommandShow extends GUINetworkCommand {

    public GUICommandShow(String name, Networking networking) {
        super(name, networking);
    }

    @Override
    public void executeCommand(MouseEvent event) {
        super.executeCommand(event);
    }

    public void executeCommand(GUITableMain table) {
        ObservableList<MusicBand> musicBands = FXCollections.observableArrayList();

        ServerResponse response;

        ClientRequest clientRequest = new SessionClientRequest(
                new ClientRequest(new ParsedString("show", "show"), 1L, 1),
                sessionKeyManager.getSessionKey()
        );

        try {
            response = this.networking.sendClientRequest(clientRequest);

            clientRequest.setStage(100);

            do {
                response = this.networking.sendClientRequest(clientRequest);
                clientRequest.setStage(response.getStage());

                if (response.getResponseStatus() != ResponseStatus.OK) break;

                if (response.getData() == null) break;
                if (!(response.getData() instanceof MusicBand)) break;

                var data = (MusicBand) response.getData();

                musicBands.add(data);

            } while (response.getStage() != 99 || response.getResponseStatus() == ResponseStatus.OK);

            Platform.runLater(() -> {
                table.getTableView().setItems(musicBands);
                eventManager.publish(EventType.COLLECTION_DATA_LOADED.toString(), musicBands);
            });
        } catch (IOException | ClassNotFoundException e) {
            ClientConsole.println("[ERROR] " + e.getMessage());
        }
    }
}
