/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.commands.ServerCollectionCommand;
import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;

/**
 * @author developer
 */
public class CommandGroupCountingByCreationDate extends ServerCollectionCommand {

    public CommandGroupCountingByCreationDate(String name, String description, CollectionManager collectionManager) {
        super(name, description, collectionManager);
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {
        return new ServerResponse();
    }
}
