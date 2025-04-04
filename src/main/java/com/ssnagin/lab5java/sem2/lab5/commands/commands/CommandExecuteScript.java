/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.lab5java.sem2.lab5.commands.commands;

import com.google.gson.GsonBuilder;
import com.ssnagin.lab5java.sem2.lab5.ApplicationStatus;
import com.ssnagin.lab5java.sem2.lab5.Core;
import com.ssnagin.lab5java.sem2.lab5.collection.CollectionManager;
import com.ssnagin.lab5java.sem2.lab5.collection.model.MusicBand;
import com.ssnagin.lab5java.sem2.lab5.commands.Command;
import com.ssnagin.lab5java.sem2.lab5.commands.CommandManager;
import com.ssnagin.lab5java.sem2.lab5.console.Console;
import com.ssnagin.lab5java.sem2.lab5.console.InputParser;
import com.ssnagin.lab5java.sem2.lab5.console.ParseMode;
import com.ssnagin.lab5java.sem2.lab5.console.ParsedString;
import com.ssnagin.lab5java.sem2.lab5.files.FileManager;
import com.ssnagin.lab5java.sem2.lab5.files.adapters.LocalDateAdapter;
import com.ssnagin.lab5java.sem2.lab5.scripts.exceptions.ScriptRecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.TreeSet;

/**
 * Shows brief description about available commands
 * 
 * @author developer
 */
public class CommandExecuteScript extends Command {
    
    private final CommandManager commandManager;
    private final CollectionManager collectionManager;
    
    public CommandExecuteScript(String name, String description, CommandManager commandManager, CollectionManager collectionManager) {
        super(name, description);
        this.commandManager = commandManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {
        // FOR THE FUTURE:
        // This command requires uri string, so we will have to edit ParsedString to ParseMode.URI !!!
        
        parsedString = InputParser.parse(parsedString.getRowArguments(), ParseMode.COMMAND_ONLY);

        File file = new File(parsedString.getCommand());

        try {
            Core.getInstance().pushFileScanner(file);

            while (Core.getInstance().getCurrentScanner().hasNextLine()) {
                String line = Core.getInstance().getCurrentScanner().nextLine().trim();
                if (line.isEmpty()) continue;

                ParsedString scriptCommand = InputParser.parse(line, ParseMode.COMMAND_ONLY);
                Command command = commandManager.get(scriptCommand.getCommand());

                ApplicationStatus status = command.executeCommand(scriptCommand);

                if (status != ApplicationStatus.RUNNING) return status;
            }
        } catch (IOException e) {
            Console.error("Script file not found: " + e.getMessage());

        } catch (ScriptRecursionException e) {
            Console.error("Recursion detected! " + e.getMessage());
            Core.getInstance().clearActiveScripts();
            Core.getInstance().removeScanners();
            return ApplicationStatus.RUNNING;
        } catch (Exception e) {
            Console.error("Un");
        }
        //finally {
        try {
            Core.getInstance().popScanner(file);
        } catch (IOException e) {
            Console.error("Error while accessing to File, stop all executables...");
        }
        //}

        return ApplicationStatus.RUNNING;
    }
}
