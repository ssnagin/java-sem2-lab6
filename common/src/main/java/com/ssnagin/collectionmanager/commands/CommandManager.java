/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands;

import com.ssnagin.collectionmanager.commands.commands.CommandDefault;
import com.ssnagin.collectionmanager.commands.interfaces.Manageable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

/**
 * @author developer
 */
@EqualsAndHashCode
@ToString
public class CommandManager implements Manageable<Command> {

    @Getter
    private static final CommandManager instance = new CommandManager();

    @Getter
    private Map<String, Command> commands = new HashMap<>();

    private Deque<Command> commandHistory = new ArrayDeque<>();
    private final int MAX_HISTORY_SIZE = 9;

    @Override
    public void register(Command command) {
        commands.put(command.getName(), command);
    }

    @Override
    public Command get(String commandName) {
        Command command = commands.get(commandName);

        if (command != null) {
            addToHistory(command);
            return command;
        }

        return new CommandDefault("", "");
    }

    public List<Command> getCommandsList() {
        return new ArrayList<>(this.commands.values());
    }

    private void setCommands(Map<String, Command> commands) {
        this.commands = commands;
    }

    /**
     * Добавляет команду в историю выполнения
     */
    public void addToHistory(Command command) {
        // Удаляем самую старую команду, если достигли максимума
        if (commandHistory.size() >= MAX_HISTORY_SIZE) {
            commandHistory.removeLast();
        }
        commandHistory.push(command);
    }

    public List<Command> getCommandHistory() {
        return new ArrayList<>(commandHistory);
    }

    public Command getLastCommand() {
        return commandHistory.peek();
    }

    public void clearHistory() {
        commandHistory.clear();
    }
}