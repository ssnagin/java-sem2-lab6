/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.commands.ServerCommand;
import com.ssnagin.collectionmanager.networking.ClientRequest;
import com.ssnagin.collectionmanager.networking.ServerResponse;
import com.ssnagin.collectionmanager.scripts.ScriptManager;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 *
 * @author developer
 */
public class CommandRemoveLower extends ServerCommand {

    private CollectionManager collectionManager;
    private ScriptManager scriptManager;


    public CommandRemoveLower(String name, String description, CollectionManager collectionManager, ScriptManager scriptManager) {
        super(name, description);

        this.collectionManager = collectionManager;
        this.scriptManager = scriptManager;
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {
        return new ServerResponse();
    }
}
