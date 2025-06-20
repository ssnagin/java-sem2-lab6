package com.ssnagin.collectionmanager.gui.controllers;

import com.ssnagin.collectionmanager.Core;
import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.gui.window.WindowManager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class GUIController {

    private Core core = Core.getInstance();

    protected final CommandManager localCommandManager = new CommandManager();
    protected final WindowManager windowManager = WindowManager.getInstance();

    private boolean isInitialized = false;

    protected void initialize() {
        if (isInitialized) return;
        isInitialized = true;
    }
}
