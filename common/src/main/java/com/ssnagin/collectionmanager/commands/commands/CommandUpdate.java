/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.commands.Command;
import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.console.ParsedString;
import com.ssnagin.collectionmanager.description.DescriptionParser;
import com.ssnagin.collectionmanager.reflection.Reflections;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author developer
 */
public class CommandUpdate extends Command {

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
    public ApplicationStatus executeCommand(ParsedString parsedString) {
        
        if (!parsedString.getArguments().isEmpty()) {
            if ("h".equals(parsedString.getArguments().get(0)))
                return this.showUsage(parsedString);
        }

        Long id;

        // Try to parse Integer
        // VALIDATOR HERE

        try {
            id = (Long) Reflections.parsePrimitiveInput(
                    Long.class,
                    parsedString.getArguments().get(0)
            );
        } catch (NumberFormatException ex) {
            Console.log("Неверный формат числа");
            return ApplicationStatus.RUNNING;
        } catch (IndexOutOfBoundsException ex) {
            return this.showUsage(parsedString);
        }
         
        
        MusicBand musicBand = this.collectionManager.getElementById(id);
        
        if (musicBand == null) {
            Console.log("Коллекции по заданому id не существует");
            return ApplicationStatus.RUNNING;
        }
        
        parsedString.setCommand("add");
        
        Command command = this.commandManager.get(parsedString.getCommand());
        ApplicationStatus status = command.executeCommand(parsedString);
        
        if (status != ApplicationStatus.RUNNING) return status;
        
        MusicBand elementToChange = this.collectionManager.getElementById(id);
        
        // Check if the collection was not added:
        if (elementToChange == null) {
            Console.error("Something went wrong while adding element to collection!");
            return ApplicationStatus.RUNNING;
        }
        
        elementToChange.setId(id);
        
        parsedString.setCommand("remove_by_id");
        
        command = this.commandManager.get(parsedString.getCommand());
        return command.executeCommand(parsedString);
    }
    
    @Override
    public ApplicationStatus showUsage(ParsedString parsedString) {
        
        StringBuilder stringBuilder = new StringBuilder();
      
        stringBuilder.append("usage: update <id> <field> <value>\n").append("things that can be updated:\n");
        stringBuilder.append(DescriptionParser.getRecursedDescription(MusicBand.class, new HashMap<>()));
        
        Console.println(stringBuilder);
        
        return ApplicationStatus.RUNNING;
    }
}