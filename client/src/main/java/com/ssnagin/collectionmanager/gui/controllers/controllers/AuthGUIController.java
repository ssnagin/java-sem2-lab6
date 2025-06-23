package com.ssnagin.collectionmanager.gui.controllers.controllers;

import com.ssnagin.collectionmanager.gui.form.GUIFormWithLogs;
import com.ssnagin.collectionmanager.gui.commands.commands.GUICommandAuth;
import com.ssnagin.collectionmanager.gui.controllers.GUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AuthGUIController extends GUIController {

    @FXML
    public PasswordField authPasswordField;

    @FXML
    public Button authSubmit;

    @FXML
    public TextField registerLoginField;

    @FXML
    public PasswordField registerPasswordField;

    @FXML
    public Button registerSubmit;

    @FXML
    public TextField authLoginField;

    @FXML
    public TextArea registerLog;

    @FXML
    public TextArea authLog;

    @FXML
    protected void initialize() {

        super.initialize();

        authSubmit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommandAuth) localCommandManager.get("gui_auth")).executeCommand(
                    event,
                    new GUIFormWithLogs("auth", authSubmit, authLog, authLoginField, authPasswordField)
            );
        });

        registerSubmit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommandAuth) localCommandManager.get("gui_auth")).executeCommand(
                    event,
                    new GUIFormWithLogs("register", registerSubmit, registerLog, registerLoginField, registerPasswordField)
            );
        });
    }
}
