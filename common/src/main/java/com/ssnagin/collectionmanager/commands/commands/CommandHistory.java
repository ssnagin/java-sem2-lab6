/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.commands.Command;
import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.console.ParsedString;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 *
 * @author developer
 */
public class CommandHistory extends Command {

    private CommandManager commandManager;

    public CommandHistory(String name, String description, CommandManager commandManager) {
        super(name, description);
        this.commandManager = commandManager;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {

        if (this.commandManager.getCommandHistory().isEmpty()) {
            Console.log("There were no commands!");
            return ApplicationStatus.RUNNING;
        }

        for (Command command : this.commandManager.getCommandHistory()) {
            Console.log(command.getName());
        }

        return ApplicationStatus.RUNNING;
    }
}
