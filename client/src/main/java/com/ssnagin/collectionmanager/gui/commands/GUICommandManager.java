package com.ssnagin.collectionmanager.gui.commands;

import com.ssnagin.collectionmanager.commands.CommandManager;
import lombok.Getter;

public class GUICommandManager extends CommandManager {

    @Getter
    private static final GUICommandManager instance = new GUICommandManager();
}
