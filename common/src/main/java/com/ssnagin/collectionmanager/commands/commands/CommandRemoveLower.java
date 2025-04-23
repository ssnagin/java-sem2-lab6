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
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.description.DescriptionParser;
import com.ssnagin.collectionmanager.reflection.Reflections;
import com.ssnagin.collectionmanager.scripts.ScriptManager;
import com.ssnagin.collectionmanager.validation.TempValidator;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 * 
 * @author developer
 */
public class CommandRemoveLower extends Command {

    private CollectionManager collectionManager;
    private ScriptManager scriptManager;


    public CommandRemoveLower(String name, String description, CollectionManager collectionManager, ScriptManager scriptManager) {
        super(name, description);

        this.collectionManager = collectionManager;
        this.scriptManager = scriptManager;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {

        Scanner scanner = this.scriptManager.getCurrentScanner();

        if (!parsedString.getArguments().isEmpty()) {
            if (" h".equals(parsedString.getArguments().get(0)))
                return this.showUsage(parsedString);
        }

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

            // remove all elements that are lower than created:
            Console.separatePrint(this.collectionManager.removeLower(musicBand), "REMOVED");
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException ex) {
            Console.error(ex.toString());
        }

        return ApplicationStatus.RUNNING;
    }

    @Override
    public ApplicationStatus showUsage(ParsedString parsedString) {
        Console.println("Usage: remove_lower\nСписок того, что надо ввести:");
        Console.println(DescriptionParser.getRecursedDescription(MusicBand.class, new HashMap<>()));

        return ApplicationStatus.RUNNING;
    }
}
