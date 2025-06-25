package com.ssnagin.collectionmanager.gui.controllers.controllers;

import com.ssnagin.collectionmanager.collection.model.MusicGenre;
import com.ssnagin.collectionmanager.gui.commands.commands.GUICommandAdd;
import com.ssnagin.collectionmanager.gui.controllers.GUIController;
import com.ssnagin.collectionmanager.gui.nodes.form.GUIForm;
import com.ssnagin.collectionmanager.gui.nodes.form.GUIFormWithLogs;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddGUIController extends GUIController {

    @FXML
    public TextField fieldName; // 0
    @FXML
    public TextField fieldCoordinatesX; // 1
    @FXML
    public TextField fieldCoordinatesY; // 2
    @FXML
    public TextField fieldNumberOfParticipants; // 3
    @FXML
    public TextField fieldSinglesCount; // 4
    @FXML
    public ComboBox<MusicGenre> comboBoxMusicGenre; // 5
    @FXML
    public TextField fieldBestAlbum; // 6
    @FXML
    public TextField fieldTracksAmount; // 7
    @FXML
    public Button buttonAddCommand; // 8
    @FXML
    public Button buttonAddIfMinCommand; // 9

    @FXML
    public Button buttonUpdateCommand;

    @FXML
    public TextArea logArea;

    @FXML
    @Override
    protected void initialize() {

        if (isInitialized) return;
        isInitialized = true;

        initEventListeners();

        // Init fields
        comboBoxMusicGenre.getItems().setAll(MusicGenre.values());
    }

    @FXML
    protected void initEventListeners() {
        buttonAddCommand.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> buttonSubmitHandler(event, "add"));
        buttonAddIfMinCommand.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> buttonSubmitHandler(event, "add_if_min"));
        buttonUpdateCommand.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> buttonSubmitHandler(event, "update"));
    }

    private void buttonSubmitHandler(MouseEvent event, String type) {
        ((GUICommandAdd) localCommandManager.get("gui_add")).executeCommand(
                event,
                new GUIFormWithLogs(type,
                        buttonAddCommand,
                        logArea,
                        fieldName,
                        fieldCoordinatesX,
                        fieldCoordinatesY,
                        fieldNumberOfParticipants,
                        fieldSinglesCount,
                        comboBoxMusicGenre,
                        fieldBestAlbum,
                        fieldTracksAmount
                )
        );
    }
}
