/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.collection.wrappers.LocalDateWrapper;
import com.ssnagin.collectionmanager.commands.Command;
import com.ssnagin.collectionmanager.commands.ServerCommand;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.ClientRequest;
import com.ssnagin.collectionmanager.networking.ServerResponse;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 
 * @author developer
 */
public class CommandGroupCountingByCreationDate extends ServerCommand {

    private CollectionManager collectionManager;

    public CommandGroupCountingByCreationDate(String name, String description, CollectionManager
                                               collectionManager) {
        super(name, description);

        this.collectionManager = collectionManager;
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {
        return new ServerResponse();
    }
}
