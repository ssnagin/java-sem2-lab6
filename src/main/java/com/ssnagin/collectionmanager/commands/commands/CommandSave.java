/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.ApplicationStatus;
import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.commands.Command;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.console.ParsedString;
import com.ssnagin.collectionmanager.files.FileManager;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 * 
 * @author developer
 */
public class CommandSave extends Command {

    private CollectionManager collectionManager;
    private FileManager fileManager;

    public CommandSave(String name, String description, CollectionManager collectionManager, FileManager fileManager) {
        super(name, description);

        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {

        Console.println(parsedString);

        String path = parsedString.getArguments().get(0);

        try {
            fileManager.write(this.collectionManager.getCollection(), path);
        } catch (Exception ex) {
            Console.error(ex);
        }

        return ApplicationStatus.RUNNING;
    }
}
