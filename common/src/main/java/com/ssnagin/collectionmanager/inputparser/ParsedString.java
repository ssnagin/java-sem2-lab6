/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.inputparser;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author developer
 */
@ToString
@EqualsAndHashCode
public class ParsedString implements Serializable {

    @Getter
    @Setter
    private String pureString;

    @Getter
    private String command;

    @Getter
    private List<String> arguments;

    public ParsedString(String pureString, String command, List<String> arguments) {
        this(pureString);

        this.setCommand(command);
        this.addArguments(arguments);
    }

    public ParsedString(String pureString, String command, String... arguments) {
        this(pureString, command, new ArrayList<>(List.of(arguments)));
    }

    public ParsedString(String pureString, String command) {
        this(pureString, command, new ArrayList<>());
    }

    public ParsedString() {
        this("");
    }

    public ParsedString(String pureString) {
        this.setPureString(pureString);

        this.command = "";
        this.arguments = new ArrayList<>();
    }

    public void setCommand(String command) {
        this.command = command.toLowerCase();
    }

    public String getRowArguments() {
        StringBuilder stringBuilder = new StringBuilder();

        for (String element : this.getArguments()) {
            stringBuilder.append(element);
        }

        return stringBuilder.toString();
    }

    public void addArgument(String argument) {
        if (this.getArguments().isEmpty())
            argument = argument.stripLeading();

        this.arguments.add(argument);
    }

    public void addArguments(List<String> arguments) {
        for (String argument : arguments) {
            this.addArgument(argument);
        }
    }

    public void addFromStream(String someText) {
        if (someText == null) return;

        if (!(this.getCommand().equals(""))) {
            this.addArgument(someText);
            return;
        }

        this.setCommand(someText);
    }

    public boolean isEmpty() {
        return this.getPureString().isEmpty() || this.getCommand() == null || this.getCommand().isEmpty();
    }
}
