/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A basic class that defines all commands
 *
 * @author developer
 */
@EqualsAndHashCode
@ToString
public abstract class Command implements Comparable<Command> {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String description;

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

    public int compareTo(Command otherCommand) {

        int result = this.getName().compareTo(otherCommand.getName());

        if (result == 0) this.getDescription().compareTo(otherCommand.getDescription());

        return result;
    }
}
