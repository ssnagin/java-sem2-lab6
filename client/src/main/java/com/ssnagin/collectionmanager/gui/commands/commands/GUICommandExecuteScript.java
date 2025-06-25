package com.ssnagin.collectionmanager.gui.commands.commands;

import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.commands.UserCommand;
import com.ssnagin.collectionmanager.console.ClientConsole;
import com.ssnagin.collectionmanager.events.EventType;
import com.ssnagin.collectionmanager.gui.commands.GUICommand;
import com.ssnagin.collectionmanager.gui.dialog.FileChooserDialog;
import com.ssnagin.collectionmanager.inputparser.InputParser;
import com.ssnagin.collectionmanager.inputparser.ParseMode;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class GUICommandExecuteScript extends GUICommand {

    protected UserCommand executeScriptCommand;

    public GUICommandExecuteScript(String name, UserCommand executeScriptCommand) {
        super(name);

        this.executeScriptCommand = executeScriptCommand;
    }

    public void executeCommand(MouseEvent event) {

        Optional<File> selectedFile = FileChooserDialog.showFileChooser(
                ((Node) event.getSource()).getScene().getWindow()
        );

        selectedFile.ifPresent(file -> {

            ClientConsole.println(file.getAbsolutePath());
            try {
                ClientConsole.println(file.getCanonicalPath());
            } catch (IOException e) {
                ClientConsole.error(e.getMessage());
            }


            ParsedString parsedString = InputParser.parse(
                    "execute_script \"" + file.getAbsolutePath() + "\"",
                    ParseMode.DEFAULT
            );
            executeScriptCommand.executeCommand(parsedString);
        });

        eventManager.publish(EventType.COLLECTION_DATA_CHANGED.toString(), null);
    }
}
