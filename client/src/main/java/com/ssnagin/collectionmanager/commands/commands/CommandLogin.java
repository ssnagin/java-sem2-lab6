package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.commands.UserNetworkCommand;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.reflection.Reflections;
import com.ssnagin.collectionmanager.scripts.ScriptManager;
import com.ssnagin.collectionmanager.user.objects.InternalUser;
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


            Console.log(user.toString());
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            Console.error(e);
        }

        return ApplicationStatus.RUNNING;
    }
}
