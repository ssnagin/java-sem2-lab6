/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.lab5java.sem2.lab5.commands.commands;

import com.ssnagin.lab5java.sem2.lab5.ApplicationStatus;
import com.ssnagin.lab5java.sem2.lab5.collection.CollectionManager;
import com.ssnagin.lab5java.sem2.lab5.commands.Command;
import com.ssnagin.lab5java.sem2.lab5.console.Console;
import com.ssnagin.lab5java.sem2.lab5.console.ParsedString;

import java.util.NoSuchElementException;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 * 
 * @author developer
 */
public class CommandRemoveById extends Command {
    
    private CollectionManager collectionManager;
    
    public CommandRemoveById(String name, String description, CollectionManager collectionManager) {
        super(name, description);
        
        this.collectionManager = collectionManager;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {
        
        if (!parsedString.getArguments().isEmpty()) {
            if ("h".equals(parsedString.getArguments().get(0)))
                return this.showUsage(parsedString);
        }
        
        Long id;
        
        // Try to parse Integer
        // VALIDATOR HERE
        try {
            id = Long.parseLong(parsedString.getArguments().get(0));
        } catch (NumberFormatException ex) {
            Console.log("Wrong number format");
            return ApplicationStatus.RUNNING;
        } catch (IndexOutOfBoundsException ex) {
            return this.showUsage(parsedString);
        }
        
        if (this.collectionManager.getElementById(id) == null) {
            Console.log("Whoops, there is no collection with this element!");
            return ApplicationStatus.RUNNING;
        }

        try {
            this.collectionManager.removeElementById(id);
        } catch (NoSuchElementException e) {
            Console.log("Something went wrong, the element with id="+ id + " was not removed from collection!");
        }
        
        return ApplicationStatus.RUNNING;
    }
}
