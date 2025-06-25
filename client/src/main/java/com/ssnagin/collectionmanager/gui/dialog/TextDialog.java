package com.ssnagin.collectionmanager.gui.dialog;

import javafx.scene.control.TextInputDialog;
import java.util.Optional;

public class TextDialog {

    public static Optional<String> showInputDialog(String title, String header, String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);

        return dialog.showAndWait();
    }

    // additional method with default values
    public static Optional<String> showInputDialog(String content) {
        return showInputDialog("Input", "Please enter value:", content);
    }
}