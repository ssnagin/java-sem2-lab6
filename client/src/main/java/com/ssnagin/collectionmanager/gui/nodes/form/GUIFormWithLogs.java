package com.ssnagin.collectionmanager.gui.nodes.form;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Getter;

public class GUIFormWithLogs extends GUIForm {

    @Getter
    protected TextArea logArea;

    public GUIFormWithLogs(String name, Button submitButton, TextArea logArea, Node... fields) {
        super(name, submitButton, fields);

        this.logArea = logArea;
    }
}
