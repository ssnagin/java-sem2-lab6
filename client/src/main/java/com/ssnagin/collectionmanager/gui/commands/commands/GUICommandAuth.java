package com.ssnagin.collectionmanager.gui.commands.commands;

import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.gui.alert.AlertManager;
import com.ssnagin.collectionmanager.gui.commands.GUICommand;
import com.ssnagin.collectionmanager.gui.window.WindowManager;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class GUICommandAuth extends GUICommand {

    StringBuilder preparedCommandList;
    private final AtomicBoolean areCommandsInitialized = new AtomicBoolean();

    WindowManager windowManager;

    public GUICommandAuth(String name, WindowManager windowManager) {
        super(name);

        this.windowManager = windowManager;
    }

    public void executeCommand(MouseEvent event) {
        windowManager.get("auth").show();
    }
}
