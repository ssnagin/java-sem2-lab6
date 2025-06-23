package com.ssnagin.collectionmanager.console;

import com.ssnagin.collectionmanager.events.EventManager;
import com.ssnagin.collectionmanager.events.EventType;
import lombok.Setter;

public class ClientConsole extends Console {

    private static final EventManager eventManager = EventManager.getInstance();

    public ClientConsole instance = new ClientConsole();

    public static void print(Object text) {
        eventManager.publish(EventType.TEXT_THROWN.toString(), text.toString());

        System.out.print(text.toString());
    }

    public static void println(Object text) {
        eventManager.publish(EventType.TEXT_THROWN.toString(), text.toString());

        System.out.println(text.toString());
    }
}
