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
import java.util.ArrayList;
import java.util.Collections;

/**
 * Shows brief description about available commands
 * 
 * @author developer
 */
public class CommandHelp extends Command {
    
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
