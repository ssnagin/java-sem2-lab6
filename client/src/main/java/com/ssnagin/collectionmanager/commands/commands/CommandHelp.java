/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.commands.Command;
import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.commands.UserCommand;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Shows brief description about available commands
 * 
 * @author developer
 */
public class CommandHelp extends UserCommand {
    
    private CommandManager commandManager;
    
    private String temporaryCreatedHeadMessage = "CollectionManager is a nice tool though.\nHere are available commands:\n";
    
    public CommandHelp(String name, String description, CommandManager commandManager) {
        super(name, description);
        
        this.commandManager = commandManager;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {
        Console.println(temporaryCreatedHeadMessage);
       
        ArrayList<String> sortedKeys = new ArrayList<String>(this.commandManager.getCommands().keySet());
        Collections.sort(sortedKeys);
        
        Command selectedCommand;
        
        for (String command : sortedKeys) {
            
            selectedCommand = this.commandManager.get(command);
            
            Console.println(selectedCommand.getName() + "   " + selectedCommand.getDescription());
        }
        
        return ApplicationStatus.RUNNING;
    }
}
