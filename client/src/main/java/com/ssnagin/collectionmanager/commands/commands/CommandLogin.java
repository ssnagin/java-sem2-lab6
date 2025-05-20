package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.commands.UserNetworkCommand;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import com.ssnagin.collectionmanager.reflection.Reflections;
import com.ssnagin.collectionmanager.scripts.ScriptManager;
import com.ssnagin.collectionmanager.session.SessionKey;
import com.ssnagin.collectionmanager.user.objects.InternalUser;
import com.ssnagin.collectionmanager.user.objects.User;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class CommandLogin extends UserNetworkCommand {

    protected ScriptManager scriptManager;

    public CommandLogin(String name, String description, Networking networking, ScriptManager scriptManager) {
        super(name, description, networking);

        this.scriptManager = scriptManager;
    }

    @Override
    @SneakyThrows
    public ApplicationStatus executeCommand(ParsedString parsedString) {

        ApplicationStatus applicationStatus = super.executeCommand(parsedString);
        if (applicationStatus != ApplicationStatus.RUNNING) return applicationStatus;

        Scanner scanner = this.scriptManager.getCurrentScanner();

//        User user = Reflections.parseModel(User.class, scanner);
//
//        Console.log(user.toString());

        try {

            InternalUser user = Reflections.parseModel(InternalUser.class, scanner);
            user.setIsBanned(0);

            ClientRequest clientRequest = new ClientRequest(
                parsedString, (User) user
            );

            ServerResponse serverResponse = this.networking.sendClientRequest(clientRequest);

            SessionKey sessionKey = (SessionKey) serverResponse.getData();

            sessionKeyManager.setSessionKey(sessionKey);
            
            Console.separatePrint(
                    serverResponse.getMessage(),
                    String.valueOf(serverResponse.getResponseStatus())
            );

        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            Console.error(e);
        }

        return ApplicationStatus.RUNNING;
    }
}
