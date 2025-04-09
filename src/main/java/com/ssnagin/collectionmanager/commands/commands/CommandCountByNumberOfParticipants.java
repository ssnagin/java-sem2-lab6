/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.ApplicationStatus;
import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.commands.Command;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.console.ParsedString;
import com.ssnagin.collectionmanager.reflection.Reflections;

import java.util.NoSuchElementException;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 * 
 * @author developer
 */
public class CommandCountByNumberOfParticipants extends Command {

    private CollectionManager collectionManager;

    public CommandCountByNumberOfParticipants(String name, String description, CollectionManager collectionManager) {
        super(name, description);

        this.collectionManager = collectionManager;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {

        long counter = 0;
        long numberOfParticipants;

        try {
            numberOfParticipants = (Long) Reflections.parsePrimitiveInput(
                    Long.class,
                    parsedString.getArguments().get(0)
            );
        } catch (NumberFormatException ex) {
            Console.log("Неверный формат числа");
            return ApplicationStatus.RUNNING;
        } catch (IndexOutOfBoundsException | NoSuchElementException ex) {
            return this.showUsage(parsedString);
        }

        for (MusicBand musicBand : this.collectionManager.getCollection()) {
            if (numberOfParticipants == musicBand.getNumberOfParticipants()) counter += 1;
        }

        Console.separatePrint(counter, "AMOUNT");

        return ApplicationStatus.RUNNING;
    }
}
