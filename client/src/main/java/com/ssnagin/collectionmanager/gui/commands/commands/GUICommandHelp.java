package com.ssnagin.collectionmanager.gui.commands.commands;

import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.gui.commands.GUICommand;
import com.ssnagin.collectionmanager.gui.alert.AlertManager;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class GUICommandHelp extends GUICommand {

    StringBuilder preparedCommandList;
    private final AtomicBoolean areCommandsInitialized = new AtomicBoolean();

    CommandManager commandManager;

    public GUICommandHelp(String name, CommandManager commandManager) {
        super(name);

        this.commandManager = commandManager;
        preparedCommandList = new StringBuilder();

        initGUI();
    }

    public void initGUI() {
        // Some code here
        areCommandsInitialized.set(false);
    }

    public void initCommands() {
        if (areCommandsInitialized.get()) return;

        ArrayList<String> sortedKeys = new ArrayList<>(this.commandManager.getCommands().keySet());
        Collections.sort(sortedKeys);

        GUICommand selectedCommand;

        for (String command : sortedKeys) {
            selectedCommand = (GUICommand) this.commandManager.get(command);
            preparedCommandList.append(selectedCommand.getName() + " | " + selectedCommand.getDescription() + "\n");
        }

        areCommandsInitialized.set(true);
    }

    public void executeCommand(MouseEvent event) {

        initCommands();

        AlertManager.showInfoAlert("Help", "Возможные команды", this.preparedCommandList.toString());
    }
}
