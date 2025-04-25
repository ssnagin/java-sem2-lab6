/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.commands.ServerCollectionCommand;
import com.ssnagin.collectionmanager.config.Config;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;
import com.ssnagin.collectionmanager.networking.serlialization.types.SerializableTreeSet;

import java.util.NavigableSet;
import java.util.stream.Collectors;

/**
 * Shows brief description about available commands
 *
 * @author developer
 */
public class CommandShow extends ServerCollectionCommand {

    public CommandShow(String name, String description, CollectionManager collectionManager) {
        super(name, description, collectionManager);
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {

        ServerResponse serverResponse = new ServerResponse(ResponseStatus.OK);
        int shownElements;

        if (this.collectionManager.isEmpty()) {
            serverResponse.appendMessage("Collection is empty");
            return serverResponse;
        }

        serverResponse.appendMessage("Collection contains " + this.collectionManager.getSize() + " elements ");

        shownElements = this.collectionManager.getSize();

        if (this.collectionManager.getSize() > Config.Commands.MAX_SHOWN_COLLECTION_ELEMENTS) {
            shownElements = Config.Commands.MAX_SHOWN_COLLECTION_ELEMENTS;
            serverResponse.appendMessage("(shown first " + shownElements + " elements, sorted by coordinates)");
        }

        NavigableSet<MusicBand> sortedMusicBands = this.collectionManager.getCollection().descendingSet();

        // Limits for the response
        serverResponse.setData(
                sortedMusicBands.stream().limit(shownElements)
                        .collect(
                                Collectors.toCollection(
                                        () -> new SerializableTreeSet<>(sortedMusicBands.comparator())
                                )
                        )
        );

        return serverResponse;
    }
}
