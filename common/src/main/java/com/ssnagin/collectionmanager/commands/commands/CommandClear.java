/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.commands.Command;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 * 
 * @author developer
 */
public class CommandClear extends Command {
    
    CollectionManager collectionManager;
    
    
    public CommandClear(String name, String description, CollectionManager collectionManager) {
        super(name, description);

        this.collectionManager = collectionManager;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {
        
        if (collectionManager.isEmpty()) {
            Console.separatePrint("Collection is already empty!", "CLEAR");
            return ApplicationStatus.RUNNING;
        }
        
        collectionManager.removeAllElements();
        Console.separatePrint("Done!", "CLEAR");
        
        return ApplicationStatus.RUNNING;
    }
}
