/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.commands.UserCommand;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 *
 * @author developer
 */
public class CommandDefault extends UserCommand {

    private String temporaryCreatedHeadMessage = "I apologize, but the given command DoEs NoT eXiSt!\n"
            + "(or it was given incorecctly)\n\n"
            + "Please, make another try :) or type help to see available commands";

    public CommandDefault(String name, String description) {
        super(name, description);
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {

        ApplicationStatus applicationStatus = super.executeCommand(parsedString);
        if (applicationStatus != ApplicationStatus.RUNNING) return applicationStatus;

        Console.println(temporaryCreatedHeadMessage);
        return ApplicationStatus.RUNNING;
    }
}