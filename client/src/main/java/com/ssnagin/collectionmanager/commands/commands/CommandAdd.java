package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.collection.wrappers.LocalDateWrapper;
import com.ssnagin.collectionmanager.commands.UserCommand;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.description.DescriptionParser;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.ClientRequest;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.ServerResponse;
import com.ssnagin.collectionmanager.reflection.Reflections;
import com.ssnagin.collectionmanager.scripts.ScriptManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Scanner;

public class CommandAdd extends UserCommand {

    private Networking networking;
    private ScriptManager scriptManager;

    public CommandAdd(String name, String description, Networking networking, ScriptManager scriptManager) {
        super(name, description);

        this.networking = networking;

        this.scriptManager = scriptManager;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {
        Scanner scanner = this.scriptManager.getCurrentScanner();

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

            var result = new LocalDateWrapper(
                    musicBand
            );

            ServerResponse response = this.networking.send(new ClientRequest(parsedString, result));

            // Validation here;
//            List<String> errors = TempValidator.validateMusicBand(musicBand);
//
//            if (!errors.isEmpty()) {
//                for (String error : errors) {
//                    Console.error(error);
//                }
//                return ApplicationStatus.RUNNING;
//            }

            // Adding into CollectionManager with Creation Date:
//            this.collectionManager.addElement(result);
            Console.separatePrint(response.getResponseStatus(), "SERVER");
            Console.separatePrint("Successfully added!", "SUCCESS");
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException ex) {
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
