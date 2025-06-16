package com.ssnagin.collectionmanager.gui.commands.commands;

import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.gui.commands.GUICommand;
import com.ssnagin.collectionmanager.gui.managers.AlertManager;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Collections;

public class GUICommandHistory extends GUICommand {

    StringBuilder preparedCommandList;

    CommandManager commandManager;

    public GUICommandHistory(String name, CommandManager commandManager) {
        super(name);

        this.commandManager = commandManager;
        preparedCommandList = new StringBuilder();

        initGUI();
    }

    public void initGUI() {

    }

    public void executeCommand(MouseEvent event) {
        AlertManager.showInfoAlert("History", "Пусто", "");
    }
}
