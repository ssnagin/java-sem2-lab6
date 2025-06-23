package com.ssnagin.collectionmanager;

import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.networking.Networking;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sun.misc.Signal;
import sun.misc.SignalHandler;

@ToString
public abstract class AbstractCore {

    @Setter
    @Getter
    String[] args;

    protected CommandManager commandManager;

    @Getter
    protected Networking networking;

    public AbstractCore(String[] args) {
        setArgs(args);

        commandManager = CommandManager.getInstance();
    }

    public AbstractCore() {
        this(new String[0]);
    }

    public void start() {
        Signal.handle(new Signal("INT"), new SignalHandler() {  // Ctrl+C
            @Override
            public void handle(Signal sig) {
                onExit();
            }
        });
    }

    public abstract void onExit();
}
