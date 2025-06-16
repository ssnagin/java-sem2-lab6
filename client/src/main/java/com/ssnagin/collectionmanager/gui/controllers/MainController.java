package com.ssnagin.collectionmanager.gui.controllers;

import com.ssnagin.collectionmanager.Core;
import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.gui.commands.GUICommand;
import com.ssnagin.collectionmanager.gui.commands.commands.GUICommandHelp;
import com.ssnagin.collectionmanager.gui.commands.commands.GUICommandHistory;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.Setter;

public class MainController {

    @Getter
    @Setter
    private Core core = Core.getInstance();

    private final CommandManager localCommandManager = new CommandManager();
    private boolean isInitialized = false;

    @FXML
    private ImageView helpCommandButton;

    @FXML
    private ImageView historyCommandButton;

    @FXML
    private void initialize() {
        if (isInitialized) return;
        isInitialized = true;

        initGUICommands();

        helpCommandButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommand) localCommandManager.get("gui_help")).executeCommand(event);
        });

        historyCommandButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommand) localCommandManager.get("gui_history")).executeCommand(event);
        });
    }

    private void initGUICommands() {
        localCommandManager.register(new GUICommandHelp("gui_help", localCommandManager));
        localCommandManager.register(new GUICommandHistory("gui_history", localCommandManager));
    }
}
