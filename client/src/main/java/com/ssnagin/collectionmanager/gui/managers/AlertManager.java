package com.ssnagin.collectionmanager.gui.managers;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AlertManager {

    public static void showInfoAlert(String title, String header, String content) {
        showAlert(Alert.AlertType.INFORMATION, title, header, content);
    }

    private static void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        switch (type) {
            case INFORMATION:
//                stage.getIcons().add(new Image("/resources/com/ssnagin/collectionmanager/img/gif/info.gif"));
                break;
            case WARNING:
                // stage.getIcons().add(new Image("/path/to/warning_icon.png"));
                break;
            case ERROR:
                // stage.getIcons().add(new Image("/path/to/error_icon.png"));
                break;
        }

        alert.showAndWait();
    }




}
