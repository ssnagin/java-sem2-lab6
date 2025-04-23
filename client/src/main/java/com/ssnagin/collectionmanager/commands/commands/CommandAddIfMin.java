/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.collection.wrappers.LocalDateWrapper;
import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.commands.UserCommand;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;
import com.ssnagin.collectionmanager.reflection.Reflections;
import com.ssnagin.collectionmanager.scripts.ScriptManager;
import com.ssnagin.collectionmanager.validation.TempValidator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Scanner;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 *
 * @author developer
 */
public class CommandAddIfMin extends UserCommand {

    private Networking networking;
    private CommandManager commandManager;
    private ScriptManager scriptManager;

    public CommandAddIfMin(String name, String description, CommandManager commandManager, ScriptManager scriptManager, Networking networking) {
        super(name, description);

        this.commandManager = commandManager;
        this.scriptManager = scriptManager;
        this.networking = networking;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {

       Scanner scanner = this.scriptManager.getCurrentScanner();

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
//            List<String> errors = TempValidator.validateMusicBand(musicBand);
//
//            if (!errors.isEmpty()) {
//                for (String error : errors) {
//                    Console.error(error);
//                }
//                return ApplicationStatus.RUNNING;
//            }

            ServerResponse response = this.networking.sendClientRequest(new ClientRequest(parsedString, result));

            Console.separatePrint(response.getResponseStatus(), "SERVER");
            Console.separatePrint(response.getMessage(), "SERVER");


        } catch (ClassNotFoundException | IOException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException ex) {
            Console.error(ex.toString());
        }

        return ApplicationStatus.RUNNING;
    }


}
