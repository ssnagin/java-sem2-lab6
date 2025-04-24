/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.commands.ServerCommand;
import com.ssnagin.collectionmanager.config.Config;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;

import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Shows brief description about available commands
 *
 * @author developer
 */
public class CommandShow extends ServerCommand {

    CollectionManager collectionManager;

    public CommandShow(String name, String description, CollectionManager collectionManager) {
        super(name, description);

        this.collectionManager = collectionManager;
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {

        ServerResponse serverResponse = new ServerResponse(ResponseStatus.OK);
        int shownElements = 0;
        long counter = 0L;

        if (this.collectionManager.isEmpty()) {
            serverResponse.appendMessage("Collection is empty");
            return serverResponse;
        }

        serverResponse.appendMessage("Collection contains " + this.collectionManager.getSize() + " elements ");

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
                                    () -> new TreeSet<>(sortedMusicBands.comparator())
                                )
                        )
        );

//        for (MusicBand musicBand : sortedMusicBands) {
//            if (counter >= shownElements) break;
//            serverResponse.appendMessage(
//                    counter + " | ========\n" + musicBand.getDescription()
//            );
//            counter += 1;
//        }

        return serverResponse;
    }
}
