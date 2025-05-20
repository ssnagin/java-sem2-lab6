/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.commands.ServerDatabaseCommand;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;

/**
 *
 *
 * @author developer
 */
public class CommandCountByNumberOfParticipants extends ServerDatabaseCommand {

    public CommandCountByNumberOfParticipants(String name, CollectionManager collectionManager) {
        super(name, collectionManager);
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {

        ServerResponse serverResponse = super.executeCommand(clientRequest);
        if (serverResponse.getResponseStatus() != ResponseStatus.OK) return serverResponse;

        Long counter = 0L;

        if (!(clientRequest.getData() instanceof Long)) {
            serverResponse.setResponseStatus(ResponseStatus.CORRUPTED);
            serverResponse.appendMessage("Wrong number format");
            return serverResponse;
        }

        Long numberOfParticipants = (Long) clientRequest.getData();

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
