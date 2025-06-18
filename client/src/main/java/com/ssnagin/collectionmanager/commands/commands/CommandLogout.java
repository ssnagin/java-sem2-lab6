package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.commands.UserNetworkCommand;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import com.ssnagin.collectionmanager.networking.wrappers.SessionClientRequest;
import com.ssnagin.collectionmanager.reflection.Reflections;
import com.ssnagin.collectionmanager.scripts.ScriptManager;
import com.ssnagin.collectionmanager.user.objects.InternalUser;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class CommandLogout extends UserNetworkCommand {

    private ScriptManager scriptManager;

    private final String TEXT_PLACEHOLDER = "Successfully logged out";

    public CommandLogout(String name, String description, Networking networking, ScriptManager scriptManager) {
        super(name, description, networking);
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {

        ApplicationStatus applicationStatus = super.executeCommand(parsedString);
        if (applicationStatus != ApplicationStatus.RUNNING) return applicationStatus;

        if (sessionKeyManager.getSessionKey() == null) {
            Console.println(TEXT_PLACEHOLDER);
            return ApplicationStatus.RUNNING;
        }
//        try {
//
//            InternalUser user = Reflections.parseModel(InternalUser.class, scanner);
//            user.setIsBanned(0);
//
//            ServerResponse response = this.networking.sendClientRequest(
//                    new ClientRequest(parsedString, user)
//            );
//            Console.separatePrint(response.getMessage(), "SERVER");
//
//        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InstantiationException |
//                 IllegalAccessException | IllegalArgumentException |
//                 InvocationTargetException ex) {
//            Console.error(ex.toString());
//        }

        try {
            ServerResponse response = this.networking.sendClientRequest(
                    new SessionClientRequest(
                            new ClientRequest(parsedString),
                            sessionKeyManager.getSessionKey()
                    )
            );

        } catch (IOException | ClassNotFoundException e) {
            Console.error(e.getMessage());
        }

        return ApplicationStatus.RUNNING;
    }
}
