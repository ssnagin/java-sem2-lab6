/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A basic class that defines all commands
 * @author developer
 */
@EqualsAndHashCode
@ToString
public abstract class Command implements Comparable<Command> {
    
    private String name;
    public String description;
    
    /**
     * Parent constructor of a command
     * 
     * @param name
     * @param description 
     */
    public Command(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }
    
     /**
     * Set the name of a command
     *
     */
    public void setName(String name) {
        this.name = name;
    }
    
     /**
     * Set the description of a command
     *
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
     /**
     * Get the name of a command
     *
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Get the description of a command
     *
     */
    public String getDescription() {
        return this.description;
    }
    
     /**
     * Executes the given command and returns status code
     * 
     * @param parsedString
     * @return
     */
    public abstract ApplicationStatus executeCommand(ParsedString parsedString);
    
     /**
     * basic usage of a command
     * 
     * @param parsedString
     * @return
     */
    public ApplicationStatus showUsage(ParsedString parsedString) {
        
        Console.log("Usage is still not implemented: " + parsedString.toString());
        
        return ApplicationStatus.RUNNING;
    }

    public int compareTo(Command otherCommand) {
        
        int result = this.getName().compareTo(otherCommand.getName());
        
        if (result == 0) this.getDescription().compareTo(otherCommand.getDescription());
        
        return result;
    }
}
