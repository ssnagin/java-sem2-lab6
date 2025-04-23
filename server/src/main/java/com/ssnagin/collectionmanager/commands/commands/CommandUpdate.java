/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.commands.ServerCommand;
import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;

/**
 * @author developer
 */
public class CommandUpdate extends ServerCommand {

    private CollectionManager collectionManager;
    private CommandManager commandManager;

    public CommandUpdate(String name,
                         String description,
                         CollectionManager collectionManager,
                         CommandManager commandManager) {
        super(name, description);

        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {
        return new ServerResponse();
    }
}