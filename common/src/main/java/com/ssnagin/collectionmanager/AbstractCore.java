package com.ssnagin.collectionmanager;

import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.scripts.ScriptManager;
import lombok.ToString;
import sun.misc.Signal;
import sun.misc.SignalHandler;

@ToString
public abstract class AbstractCore {

    protected ScriptManager scriptManager;
    protected CommandManager commandManager;

    protected Networking networking;

    public AbstractCore() {
        commandManager = CommandManager.getInstance();
        scriptManager = ScriptManager.getInstance();
    }

    public void start(String[] args) {
        Signal.handle(new Signal("INT"), new SignalHandler() {  // Ctrl+C
            @Override
            public void handle(Signal sig) {
                onExit();
            }
        });
    }

    public abstract void onExit();
}
