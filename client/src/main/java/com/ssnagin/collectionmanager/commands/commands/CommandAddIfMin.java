/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.collection.wrappers.LocalDateWrapper;
import com.ssnagin.collectionmanager.commands.UserNetworkCommand;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;
import com.ssnagin.collectionmanager.reflection.Reflections;
import com.ssnagin.collectionmanager.scripts.ScriptManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 *
 * @author developer
 */
public class CommandAddIfMin extends UserNetworkCommand {

    private ScriptManager scriptManager;

    public CommandAddIfMin(String name, String description, ScriptManager scriptManager, Networking networking) {
        super(name, description, networking);

        this.scriptManager = scriptManager;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {

        ApplicationStatus applicationStatus = super.executeCommand(parsedString);
        if (applicationStatus != ApplicationStatus.RUNNING) return applicationStatus;

        Scanner scanner = this.scriptManager.getCurrentScanner();

        Console.separatePrint("Please, fill in the form with your values:", this.getName().toUpperCase());

        try {
            MusicBand musicBand = Reflections.parseModel(MusicBand.class, scanner);

            if (musicBand == null) return ApplicationStatus.RUNNING;

            var result = new LocalDateWrapper(musicBand);


            this.networking.sendClientRequest(new ClientRequest(parsedString, result), response -> {
                Console.separatePrint(response.getResponseStatus(), "SERVER");
                Console.separatePrint(response.getMessage(), "SERVER");
            });

        } catch (IOException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException ex) {
            Console.error(ex.toString());
        }

        return ApplicationStatus.RUNNING;
    }


}
