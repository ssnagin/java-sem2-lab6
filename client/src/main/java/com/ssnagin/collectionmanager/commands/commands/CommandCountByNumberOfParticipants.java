package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.commands.UserNetworkCommand;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import com.ssnagin.collectionmanager.reflection.Reflections;
import com.ssnagin.collectionmanager.console.Console;

import java.io.IOException;
import java.util.NoSuchElementException;

public class CommandCountByNumberOfParticipants extends UserNetworkCommand {

    public CommandCountByNumberOfParticipants(String name, String description, Networking networking) {
        super(name, description, networking);
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {

        ApplicationStatus applicationStatus = super.executeCommand(parsedString);
        if (applicationStatus != ApplicationStatus.RUNNING) return applicationStatus;

        Long numberOfParticipants;

        try {
            numberOfParticipants = (Long) Reflections.parsePrimitiveInput(
                    Long.class,
                    parsedString.getArguments().get(0)
            );

            ServerResponse serverResponse = this.networking.sendClientRequest(
                    new ClientRequest(
                            parsedString,
                            numberOfParticipants
                    )
            );

            Console.separatePrint(serverResponse.getMessage(), "SERVER");

        } catch (NumberFormatException ex) {
            Console.log("Неверный формат числа");
            return ApplicationStatus.RUNNING;
        } catch (IndexOutOfBoundsException | NoSuchElementException | IOException | ClassNotFoundException ex) {
            return this.showUsage(parsedString);
        }



        return ApplicationStatus.RUNNING;

        /*long counter = 0;
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

 */       //return ApplicationStatus.RUNNING;
    }
}