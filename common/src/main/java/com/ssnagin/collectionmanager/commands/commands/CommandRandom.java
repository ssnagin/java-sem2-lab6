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

/**
 * Throws when other commands does not exist. The only one unregistered command!
 * 
 * @author developer
 */
public class CommandRandom extends Command {

    private CollectionManager collectionManager;

    private static final int MAX_RANDOM_AMOUNT = 5000;


    public CommandRandom(String name, String description, CollectionManager collectionManager) {
        super(name, description);

        this.collectionManager = collectionManager;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {

        if (!parsedString.getArguments().isEmpty()) {
            if (" h".equals(parsedString.getArguments().get(0)))
                return this.showUsage(parsedString);
        }

        Long id;

        // Try to parse Integer
        // VALIDATOR HERE
        try {
            id = Long.parseLong(parsedString.getArguments().get(0));

            if (id > MAX_RANDOM_AMOUNT) {
                Console.log("Max amount error");
                return ApplicationStatus.RUNNING;
            }

        } catch (NumberFormatException ex) {
            Console.log("Wrong number format");
            return ApplicationStatus.RUNNING;
        } catch (IndexOutOfBoundsException ex) {
            return this.showUsage(parsedString);
        }

        for (long i = 0; i < id; i++) {
            this.collectionManager.addElement(
                    new LocalDateWrapper(new MusicBand()).random()
            );
        }

        Console.separatePrint("items were added", id.toString());

        return ApplicationStatus.RUNNING;
    }
}
