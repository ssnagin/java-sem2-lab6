package com.ssnagin.collectionmanager.gui.controllers.controllers;

import com.ssnagin.collectionmanager.gui.commands.GUICommand;
import com.ssnagin.collectionmanager.gui.commands.commands.GUICommandAuth;
import com.ssnagin.collectionmanager.gui.commands.commands.GUICommandHelp;
import com.ssnagin.collectionmanager.gui.commands.commands.GUICommandHistory;
import com.ssnagin.collectionmanager.gui.controllers.GUIController;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MainGUIController extends GUIController {

    @FXML
    private ImageView helpCommandButton;

    @FXML
    private ImageView historyCommandButton;

    @FXML
    private ImageView loginCommandButton;

    @FXML
    protected void initialize() {
        super.initialize();

        initGUICommands();

        helpCommandButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommand) localCommandManager.get("gui_help")).executeCommand(event);
        });

        historyCommandButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommand) localCommandManager.get("gui_history")).executeCommand(event);
        });

        loginCommandButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommand) localCommandManager.get("gui_auth")).executeCommand(event);
        });
    }

    private void initGUICommands() {
        localCommandManager.register(new GUICommandHelp("gui_help", localCommandManager));
        localCommandManager.register(new GUICommandHistory("gui_history", localCommandManager));

        localCommandManager.register(new GUICommandAuth("gui_auth", windowManager));
    }
}
