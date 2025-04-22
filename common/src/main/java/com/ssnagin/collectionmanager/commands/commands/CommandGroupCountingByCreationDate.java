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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 
 * @author developer
 */
public class CommandGroupCountingByCreationDate extends Command {

    private CollectionManager collectionManager;

    public CommandGroupCountingByCreationDate(String name, String description, CollectionManager
                                               collectionManager) {
        super(name, description);

        this.collectionManager = collectionManager;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {

        if (collectionManager.isEmpty()) {
            Console.log("The collection is empty!");
            return ApplicationStatus.RUNNING;
        }

        Map<LocalDate, Integer> groups = new HashMap<>();

        for (MusicBand musicBand : collectionManager.getCollection()) {
            if (!(musicBand instanceof LocalDateWrapper localDateWrapper)) continue;

            LocalDate localDate = localDateWrapper.getCreationDate();
            groups.put(localDate, groups.getOrDefault(localDate, 0) + 1);
        }

        if (groups.isEmpty()) {
            Console.separatePrint("No groups were found", "WHOOPS");
            return ApplicationStatus.RUNNING;
        }

        Console.log("Groups by LocalDate:");

        for (Map.Entry<LocalDate, Integer> entry : groups.entrySet()) {
            Console.separatePrint(entry.getKey() + " : " + entry.getValue(), "      ");
        }

        return ApplicationStatus.RUNNING;
    }
}
