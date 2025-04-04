/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.lab5java.sem2.lab5.commands.commands;

import com.ssnagin.lab5java.sem2.lab5.ApplicationStatus;
import com.ssnagin.lab5java.sem2.lab5.collection.CollectionManager;
import com.ssnagin.lab5java.sem2.lab5.collection.model.Album;
import com.ssnagin.lab5java.sem2.lab5.collection.model.Coordinates;
import com.ssnagin.lab5java.sem2.lab5.collection.model.MusicBand;
import com.ssnagin.lab5java.sem2.lab5.collection.model.MusicGenre;
import com.ssnagin.lab5java.sem2.lab5.collection.wrappers.LocalDateWrapper;
import com.ssnagin.lab5java.sem2.lab5.commands.Command;
import com.ssnagin.lab5java.sem2.lab5.console.Console;
import com.ssnagin.lab5java.sem2.lab5.console.ParsedString;

/**
 * Shows brief description about available commands
 * 
 * @author developer
 */
public class CommandShow extends Command {
    
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

        Long counter = 0L;

        for (MusicBand musicBand : this.collectionManager.getCollection()) {
            counter += 1;
            Console.println(Long.toString(counter) + " | ========\n" + musicBand.getDescription());
        }
        
        return ApplicationStatus.RUNNING;
    }
}
