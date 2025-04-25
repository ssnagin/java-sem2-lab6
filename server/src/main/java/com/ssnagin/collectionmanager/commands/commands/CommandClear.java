/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.commands.ServerCollectionCommand;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 *
 * @author developer
 */
public class CommandClear extends ServerCollectionCommand {

    public CommandClear(String name, String description, CollectionManager collectionManager) {
        super(name, description, collectionManager);
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {

        ServerResponse serverResponse = new ServerResponse(ResponseStatus.OK);

        this.collectionManager.removeAllElements();

        serverResponse.appendMessage("Collection was erased!");

        return serverResponse;
    }
}
