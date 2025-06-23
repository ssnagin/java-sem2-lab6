package com.ssnagin.collectionmanager.gui.controllers.controllers;

import com.ssnagin.collectionmanager.events.EventListener;
import com.ssnagin.collectionmanager.events.EventType;
import com.ssnagin.collectionmanager.gui.commands.GUICommand;
import com.ssnagin.collectionmanager.gui.commands.commands.GUICommandAuth;
import com.ssnagin.collectionmanager.gui.commands.commands.GUICommandHelp;
import com.ssnagin.collectionmanager.gui.commands.commands.GUICommandHistory;
import com.ssnagin.collectionmanager.gui.controllers.GUIController;
import com.ssnagin.collectionmanager.gui.logger.GUITextLogger;
import com.ssnagin.collectionmanager.user.objects.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.Getter;

public class MainGUIController extends GUIController {

    @FXML
    public ImageView helpCommandButton;

    @FXML
    public ImageView historyCommandButton;

    @FXML
    public ImageView loginCommandButton;

    @FXML
    public TextArea leftTextArea;

    @Getter
    private GUITextLogger textLogger;

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

        localCommandManager.register(new GUICommandAuth("gui_auth", networking, windowManager));
    }

    @Override
    protected void initEventListeners() {
        eventManager.subscribe(EventType.USER_LOGGED_IN.toString(),
                this::handleUserLoggedIn);

        textLogger = new GUITextLogger(eventManager, leftTextArea);
    }

    private void handleUserLoggedIn(User user) {
        leftTextArea.setText("Ахаха азаза я залогинился и это event");
    }
}
