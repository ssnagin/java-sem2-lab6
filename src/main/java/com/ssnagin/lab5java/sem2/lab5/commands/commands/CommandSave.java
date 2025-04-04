/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.lab5java.sem2.lab5.commands.commands;

import com.ssnagin.lab5java.sem2.lab5.ApplicationStatus;
import com.ssnagin.lab5java.sem2.lab5.collection.CollectionManager;
import com.ssnagin.lab5java.sem2.lab5.collection.model.MusicBand;
import com.ssnagin.lab5java.sem2.lab5.commands.Command;
import com.ssnagin.lab5java.sem2.lab5.console.Console;
import com.ssnagin.lab5java.sem2.lab5.console.ParsedString;
import com.ssnagin.lab5java.sem2.lab5.files.FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

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
