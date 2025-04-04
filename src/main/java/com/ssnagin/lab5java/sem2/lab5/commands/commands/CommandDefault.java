/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.lab5java.sem2.lab5.commands.commands;

import com.ssnagin.lab5java.sem2.lab5.ApplicationStatus;
import com.ssnagin.lab5java.sem2.lab5.commands.Command;
import com.ssnagin.lab5java.sem2.lab5.commands.CommandManager;
import com.ssnagin.lab5java.sem2.lab5.console.Console;
import com.ssnagin.lab5java.sem2.lab5.console.ParsedString;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 * 
 * @author developer
 */
public class CommandDefault extends Command {
    
    private String temporaryCreatedHeadMessage = "I apologize, but the given command DoEs NoT eXiSt!\n"
            + "(or it was given incorecctly)\n\n"
            + "Please, make another try :) or type help to see available commands";
    
    public CommandDefault(String name, String description) {
        super(name, description);
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {
        Console.println(temporaryCreatedHeadMessage);
        return ApplicationStatus.RUNNING;
    }
}
