package com.ssnagin.collectionmanager.gui.dialog;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.util.Optional;

public class FileChooserDialog {

    public static Optional<File> showFileChooser(Window ownerWindow, String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);

        // Настройки фильтра (опционально)
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Script files (*.txt, *.script)", "*.txt", "*.script");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог
        File selectedFile = fileChooser.showOpenDialog(ownerWindow);
        return Optional.ofNullable(selectedFile);
    }

    public static Optional<File> showFileChooser(Window ownerWindow) {
        return showFileChooser(ownerWindow, "Select script file");
    }
}