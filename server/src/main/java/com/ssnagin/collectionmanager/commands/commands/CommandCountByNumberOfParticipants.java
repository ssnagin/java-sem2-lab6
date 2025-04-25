/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.commands.ServerCommand;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;

/**
 *
 *
 * @author developer
 */
public class CommandCountByNumberOfParticipants extends ServerCommand {

    private CollectionManager collectionManager;

    public CommandCountByNumberOfParticipants(String name, String description, CollectionManager collectionManager) {
        super(name, description);

        this.collectionManager = collectionManager;


    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {

        ServerResponse serverResponse = new ServerResponse(ResponseStatus.OK);
        Long counter = 0L;

        if (!(clientRequest.getData() instanceof Long)) {
            serverResponse.setResponseStatus(ResponseStatus.CORRUPTED);
            serverResponse.appendMessage("Wrong number format");
            return serverResponse;
        }

        Long numberOfParticipants = (Lo2ng) clientRequest.getData();

        for (MusicBand musicBand : this.collectionManager.getCollection()) {
            if (numberOfParticipants == musicBand.getNumberOfParticipants()) {
                counter += 1;
            }
        }

        serverResponse.setData(counter);
        serverResponse.appendMessage("Counted " + numberOfParticipants + " participants");

        return serverResponse;
    }
}
