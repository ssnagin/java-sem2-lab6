package com.ssnagin.collectionmanager.core;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.scripts.ScriptManager;
import lombok.ToString;

@ToString
public abstract class Core {

    protected CollectionManager collectionManager;
    protected ScriptManager scriptManager;
    protected CommandManager commandManager;


    protected Networking networking;

    public Core() {
        collectionManager = CollectionManager.getInstance();
        commandManager = CommandManager.getInstance();
        scriptManager = ScriptManager.getInstance();
    }

    public abstract void start(String[] args);
}
