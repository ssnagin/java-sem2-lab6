package com.ssnagin.collectionmanager.gui.commands;

import com.ssnagin.collectionmanager.commands.Command;
import com.ssnagin.collectionmanager.console.ClientConsole;
import com.ssnagin.collectionmanager.events.EventManager;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public abstract class GUICommand extends Command {

    protected String name;
    protected String description;

    protected TextArea outputText = new TextArea();

    protected EventManager eventManager = EventManager.getInstance();

    public GUICommand(String name, String description) {
        super(name);
        setDescription(description);
    }

    public GUICommand(String name) {
        this(name, "");
    }

    public void executeCommand(MouseEvent event) {
        ClientConsole.println("Usage still not yet implemented :/ ");
    }

    public void out(Object... objects) {
        if (outputText != null) {
            outputText.setText(outputText.getText() + Arrays.toString(objects));
        }
    }
}
