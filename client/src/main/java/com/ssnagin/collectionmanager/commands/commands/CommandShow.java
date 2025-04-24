/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.commands.UserCommand;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;

/**
 * Shows brief description about available commands
 *
 * @author developer
 */
public class CommandShow extends UserCommand {

    private CollectionManager collectionManager;

    public CommandShow(String name, String description, CollectionManager collectionManager) {
        super(name, description);

        this.collectionManager = collectionManager;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {

        if (this.collectionManager.isEmpty()) {
            Console.log("Collection is empty!");
            return ApplicationStatus.RUNNING;
        }

        long counter = 0L;

        for (MusicBand musicBand : this.collectionManager.getCollection()) {
            counter += 1;
            Console.println(Long.toString(counter) + " | ========\n" + musicBand.getDescription());
        }

        return ApplicationStatus.RUNNING;
    }
}
