package com.ssnagin.collectionmanager.gui.commands.commands;

import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.gui.commands.GUICommand;
import com.ssnagin.collectionmanager.gui.managers.AlertManager;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Collections;

public class GUICommandHelp extends GUICommand {

    StringBuilder preparedCommandList;

    CommandManager commandManager;

    public GUICommandHelp(String name, CommandManager commandManager) {
        super(name);

        this.commandManager = commandManager;
        preparedCommandList = new StringBuilder();

        initGUI();
    }

    public void initGUI() {
        ArrayList<String> sortedKeys = new ArrayList<>(this.commandManager.getCommands().keySet());
        Collections.sort(sortedKeys);

        GUICommand selectedCommand;

        for (String command : sortedKeys) {
            selectedCommand = (GUICommand) this.commandManager.get(command);
            preparedCommandList.append(selectedCommand.getName() + " | " + selectedCommand.getDescription() + "\n");
        }
    }

    public void executeCommand(MouseEvent event) {
        AlertManager.showInfoAlert("Help", "Возможные команды", this.preparedCommandList.toString());
    }
}
