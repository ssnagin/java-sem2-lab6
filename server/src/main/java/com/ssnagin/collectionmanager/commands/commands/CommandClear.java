/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.commands.ServerCollectionCommand;
import com.ssnagin.collectionmanager.database.DatabaseManager;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;

import java.sql.SQLException;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 *
 * @author developer
 */
public class CommandClear extends ServerCollectionCommand {

    public CommandClear(String name, CollectionManager collectionManager) {
        super(name, collectionManager);
    }

    // Data -- the number of erased elements

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {

        ServerResponse serverResponse = super.executeCommand(clientRequest);
        if (serverResponse.getResponseStatus() != ResponseStatus.OK) return serverResponse;

        Long removedElements = -1L;

        try {
            this.collectionManager.removeAllElements();
        } catch (SQLException e) {
            return serverResponse.error("Couldn't clear the collection");
        }

        serverResponse.setData(removedElements);
        serverResponse.appendMessage("Collection was erased!");

        return serverResponse;
    }
}
