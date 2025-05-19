package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.commands.UserNetworkCommand;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import com.ssnagin.collectionmanager.reflection.Reflections;

import java.io.IOException;

public class CommandRemoveById extends UserNetworkCommand {

    public CommandRemoveById(String name, String description, Networking networking) {
        super(name, description, networking);
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {

        ApplicationStatus applicationStatus = super.executeCommand(parsedString);
        if (applicationStatus != ApplicationStatus.RUNNING) return applicationStatus;

        Long id;

        try {
            id = (Long) Reflections.parsePrimitiveInput(
                    Long.class,
                    parsedString.getArguments().get(0)
            );
        } catch (NumberFormatException ex) {
            Console.log("Неверный формат числа");
            return ApplicationStatus.RUNNING;
        } catch (IndexOutOfBoundsException e) {
            return showUsage(parsedString);
        }

        try {
            ServerResponse response = this.networking.sendClientRequest(
                    new ClientRequest(parsedString, id)
            );
            Console.separatePrint(response.getMessage(), "SERVER");
        } catch (IndexOutOfBoundsException | IOException | ClassNotFoundException e) {
            Console.error(e.toString());
        }

        return ApplicationStatus.RUNNING;
    }
}
