/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.commands.UserCommand;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;

import java.io.IOException;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 *
 * @author developer
 */
public class CommandClear extends UserCommand {

    Networking networking;

    public CommandClear(String name, String description, Networking networking) {
        super(name, description);

        this.networking = networking;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {

        try {
            ServerResponse response = this.networking.sendClientRequest(new ClientRequest(parsedString));

            Console.separatePrint("OK", "SERVER");
        } catch (IOException | ClassNotFoundException e) {
            Console.error(e.toString());
        }

        return ApplicationStatus.RUNNING;
    }
}
