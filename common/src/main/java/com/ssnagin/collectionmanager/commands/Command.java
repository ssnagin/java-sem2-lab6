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

    /**
     * Parent constructor of a command
     *
     * @param name
     */
    public Command(String name) {
        this.setName(name);
    }

    public int compareTo(Command otherCommand) {
        return this.getName().compareTo(otherCommand.getName());
    }
}
