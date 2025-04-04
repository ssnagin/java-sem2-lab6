/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.lab5java.sem2.lab5.commands.commands;

import com.ssnagin.lab5java.sem2.lab5.Core;
import com.ssnagin.lab5java.sem2.lab5.reflection.Reflections;
import com.ssnagin.lab5java.sem2.lab5.ApplicationStatus;
import com.ssnagin.lab5java.sem2.lab5.collection.CollectionManager;
import com.ssnagin.lab5java.sem2.lab5.collection.model.MusicBand;
import com.ssnagin.lab5java.sem2.lab5.collection.wrappers.LocalDateWrapper;
import com.ssnagin.lab5java.sem2.lab5.commands.Command;
import com.ssnagin.lab5java.sem2.lab5.console.Console;
import com.ssnagin.lab5java.sem2.lab5.console.ParsedString;
import com.ssnagin.lab5java.sem2.lab5.description.DescriptionParser;
import com.ssnagin.lab5java.sem2.lab5.validation.TempValidator;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Shows brief description about available commands
 * 
 * @author developer
 */
public class CommandAdd extends Command {
    
    private CollectionManager collectionManager;
    private Scanner scanner;
    
    public CommandAdd(String name, String description, CollectionManager collectionManager, Scanner scanner) {
        super(name, description);

        this.collectionManager = collectionManager;
        this.scanner = scanner;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {
        this.scanner = Core.getInstance().getCurrentScanner();

        /*
            Пример создания коллекции

            MusicBand musicBand = new MusicBand(
                1,
                "test",
                new Coordinates((long) 28, 1),
                LocalDate.now(),
                1L,
                1,
                MusicGenre.MATH_ROCK,
                new Album("Test", (long) 123)
            );
        */

        if (!parsedString.getArguments().isEmpty()) {
            if ("h".equals(parsedString.getArguments().get(0)))
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
            
            // Adding into CollectionManager with Creation Date:
            this.collectionManager.addElement(result);
            
            Console.separatePrint("Successfully added!", "SUCCESS");
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Console.error(ex.toString());
        }
        
        return ApplicationStatus.RUNNING;
    }
    
    @Override
    public ApplicationStatus showUsage(ParsedString parsedString) {
        Console.println("Usage: add\nСписок того, что надо ввести:");
        Console.println(DescriptionParser.getRecursedDescription(MusicBand.class, new HashMap<>()));
        
        return ApplicationStatus.RUNNING;
    }
}
