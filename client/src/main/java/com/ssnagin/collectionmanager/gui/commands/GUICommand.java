package com.ssnagin.collectionmanager.gui.commands;

import com.ssnagin.collectionmanager.commands.Command;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class GUICommand extends Command {

    String name;
    String description;

    public GUICommand(String name, String description) {
        super(name);
        setDescription(description);
    }

    public GUICommand(String name) {
        this(name, "");
    }

    public abstract void executeCommand(MouseEvent event);
}
