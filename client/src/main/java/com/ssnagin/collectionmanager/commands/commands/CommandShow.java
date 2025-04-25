/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.commands.UserCommand;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;
import com.sun.source.tree.Tree;

import java.io.IOException;
import java.util.TreeSet;

/**
 * Shows brief description about available commands
 *
 * @author developer
 */
public class CommandShow extends UserNetworkCommand {

    public CommandShow(String name, String description, Networking networking) {
        super(name, description, networking);
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {
        super.executeCommand(parsedString);

        ServerResponse response;
        Long counter = 1L;

        try {
            response = this.networking.sendClientRequest(
                    new ClientRequest(parsedString, null)
            );

            Console.separatePrint(response.getMessage(), "SERVER");

            if (response.getData() == null) return ApplicationStatus.RUNNING;

            for (MusicBand musicBand : (TreeSet<MusicBand>) response.getData()) {
                Console.separatePrint(musicBand.getDescription(), Long.toString(counter));
                counter++;
            }

        } catch (IOException | ClassNotFoundException e) {
            Console.error(e.toString());
        }

        return ApplicationStatus.RUNNING;
    }
}
