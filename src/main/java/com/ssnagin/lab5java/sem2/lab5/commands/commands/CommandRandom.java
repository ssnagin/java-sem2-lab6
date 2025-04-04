/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.lab5java.sem2.lab5.commands.commands;

import com.ssnagin.lab5java.sem2.lab5.ApplicationStatus;
import com.ssnagin.lab5java.sem2.lab5.Core;
import com.ssnagin.lab5java.sem2.lab5.collection.CollectionManager;
import com.ssnagin.lab5java.sem2.lab5.collection.model.Album;
import com.ssnagin.lab5java.sem2.lab5.collection.model.Coordinates;
import com.ssnagin.lab5java.sem2.lab5.collection.model.MusicBand;
import com.ssnagin.lab5java.sem2.lab5.collection.wrappers.LocalDateWrapper;
import com.ssnagin.lab5java.sem2.lab5.commands.Command;
import com.ssnagin.lab5java.sem2.lab5.console.Console;
import com.ssnagin.lab5java.sem2.lab5.console.ParsedString;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;
import java.util.RandomAccess;

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
