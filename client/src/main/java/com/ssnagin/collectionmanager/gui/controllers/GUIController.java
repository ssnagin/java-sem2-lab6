package com.ssnagin.collectionmanager.gui.controllers;

import com.ssnagin.collectionmanager.Core;
import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.gui.commands.GUICommandManager;
import com.ssnagin.collectionmanager.gui.window.WindowManager;
import com.ssnagin.collectionmanager.networking.Networking;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class GUIController {

    protected Core core = Core.getInstance();

    protected final CommandManager localCommandManager = GUICommandManager.getInstance();
    protected final WindowManager windowManager = WindowManager.getInstance();
    protected final Networking networking = core.getNetworking();

    private boolean isInitialized = false;

    protected void initialize() {
        if (isInitialized) return;
        isInitialized = true;
    }
}
