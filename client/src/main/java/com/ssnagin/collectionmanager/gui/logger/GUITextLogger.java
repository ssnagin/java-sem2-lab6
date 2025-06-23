package com.ssnagin.collectionmanager.gui.logger;

import com.ssnagin.collectionmanager.events.EventManager;
import com.ssnagin.collectionmanager.events.EventType;
import javafx.scene.control.TextArea;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GUITextLogger {

    protected EventManager eventManager;

    protected TextArea textArea;

    public GUITextLogger(EventManager eventManager, TextArea textArea) {

        // Sign to events:

        this.textArea = textArea;
        this.eventManager = eventManager;

        eventManager.subscribe(EventType.TEXT_THROWN.toString(),
                this::handleThrownText);
    }

    private void handleThrownText(Object message) {
        textArea.appendText(message.toString() + "\n");
    }
}
