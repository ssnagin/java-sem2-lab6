package com.ssnagin.collectionmanager.gui.commands.commands;

import com.ssnagin.collectionmanager.commands.Command;
import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.gui.commands.GUICommand;
import com.ssnagin.collectionmanager.gui.alert.InfoAlert;
import javafx.scene.input.MouseEvent;

public class GUICommandHistory extends GUICommand {

    protected CommandManager commandManager;

    public GUICommandHistory(String name, CommandManager commandManager) {
        super(name);

        this.commandManager = commandManager;
    }

    public void executeCommand(MouseEvent event) {

        StringBuilder prepared = new StringBuilder();

        if (this.commandManager.getCommandHistory().isEmpty()) {
            InfoAlert.showInfoAlert("History", "Пусто", "");
            return;
        }

        for (Command command : this.commandManager.getCommandHistory()) {
            prepared.append(command.getName()).append("\n");
        }
        InfoAlert.showInfoAlert("History", "Недавние команды", prepared.toString());
    }
}
