/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.commands.Command;
import com.ssnagin.collectionmanager.commands.UserCommand;
import com.ssnagin.collectionmanager.inputparser.ParsedString;

/**
 * Shows brief description about available commands
 * 
 * @author developer
 */
public class CommandExit extends UserCommand {
    
    public CommandExit(String name, String description) {
        super(name, description);
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {
        return ApplicationStatus.EXIT;
    }
}
