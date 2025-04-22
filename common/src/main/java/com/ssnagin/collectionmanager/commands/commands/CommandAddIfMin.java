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
import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.reflection.Reflections;
import com.ssnagin.collectionmanager.scripts.ScriptManager;
import com.ssnagin.collectionmanager.validation.TempValidator;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Scanner;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 * 
 * @author developer
 */
public class CommandAddIfMin extends Command {

    private CommandManager commandManager;
    private CollectionManager collectionManager;
    private Scanner scanner;
    private ScriptManager scriptManager;

    public CommandAddIfMin(String name, String description, CollectionManager collectionManager, CommandManager commandManager, ScriptManager scriptManager) {
        super(name, description);

        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
        this.scriptManager = scriptManager;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {

        this.scanner = this.scriptManager.getCurrentScanner();

        if (!parsedString.getArguments().isEmpty()) {
            if ("h".equals(parsedString.getArguments().get(0)))
                return this.showUsage(parsedString);
        }

        if (this.collectionManager.isEmpty()) return this.commandManager.get("add").executeCommand(parsedString);

        Console.separatePrint("Please, fill in the form with your values:", this.getName().toUpperCase());

        try {

            MusicBand musicBand = Reflections.parseModel(MusicBand.class, scanner);

            if (musicBand == null) return ApplicationStatus.RUNNING;

            var result = new LocalDateWrapper(
                    musicBand
            );

            // Final validation here;
            List<String> errors = TempValidator.validateMusicBand(musicBand);

            if (!errors.isEmpty()) {
                for (String error : errors) {
                    Console.error(error);
                }
                return ApplicationStatus.RUNNING;
            }

            // Adding into CollectionManager with Creation Date if it is the lowest element:

            if (result.compareTo(this.collectionManager.getLowestElement()) != -1) {
                Console.separatePrint("The element was not added", "NOTE");
                return ApplicationStatus.RUNNING;
            }

            this.collectionManager.addElement(result);
            Console.separatePrint("Successfully added!", "SUCCESS");

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException ex) {
            Console.error(ex.toString());
        }

        return ApplicationStatus.RUNNING;
    }
}
