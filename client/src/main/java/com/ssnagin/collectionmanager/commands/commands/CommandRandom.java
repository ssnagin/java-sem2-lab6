package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.commands.UserNetworkCommand;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import com.ssnagin.collectionmanager.networking.wrappers.SessionClientRequest;

import java.io.IOException;

public class CommandRandom extends UserNetworkCommand {


    public CommandRandom(String name, String description, Networking networking) {
        super(name, description, networking);
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {

        ApplicationStatus applicationStatus = super.executeCommand(parsedString);
        if (applicationStatus != ApplicationStatus.RUNNING) return applicationStatus;

        try {
            long id;
            // Try to parse Integer
            id = Long.parseLong(parsedString.getArguments().get(0));

            ServerResponse response = this.networking.sendClientRequest(
                    new SessionClientRequest(new ClientRequest(parsedString, id), sessionKeyManager.getSessionKey())
            );

            Console.separatePrint(response.getResponseStatus(), "SERVER");
            Console.separatePrint(response.getMessage(), "SERVER");

        } catch (NumberFormatException ex) {
            Console.log("Wrong number format");
            return ApplicationStatus.RUNNING;
        } catch (IndexOutOfBoundsException ex) {
            return this.showUsage(parsedString);
        } catch (IOException | ClassNotFoundException e) {
            Console.error(e.toString());
        }

        return ApplicationStatus.RUNNING;
    }
}
