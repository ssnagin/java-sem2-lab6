package com.ssnagin.collectionmanager.gui.alert;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;

public class InfoAlert {

    public static void showErrorAlert(String title, String header, String content) {
        showAlert(Alert.AlertType.ERROR, title, header, content);
    }

    public static void showWarningAlert(String title, String header, String content) {
        showAlert(Alert.AlertType.WARNING, title, header, content);
    }

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

    public static Long showAndWait(String title, String header, String content) {

        Dialog<Long> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        TextField numberField = new TextField();
        numberField.setPromptText("Введите число");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label(content), 0, 0);
        grid.add(numberField, 0, 1);

        dialog.getDialogPane().setContent(grid);

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        // stage.getIcons().add(new Image("/path/to/icon.png"));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                try {
                    return Long.parseLong(numberField.getText());
                } catch (NumberFormatException e) {
                    InfoAlert.showInfoAlert("Ошибка ввода", "Некорректное число",
                            "Пожалуйста, введите целое число");
                    return null; // Вернет null при ошибке, диалог останется открытым
                }
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);

        /*

        Long result = NumberInputAlert.showAndWait(
            "Введите число",
            "Пожалуйста, введите целое число",
            "Число для ввода:"
        );

        if (result != null) {
            System.out.println("Введено число: " + result);
            // ...
        } else {
            System.out.println("Ввод отменен или некорректен");
        }

         */
    }

    public static boolean showConfirmationAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        // Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        // stage.getIcons().add(new Image("/path/to/icon.png"));

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;

        /*

        boolean confirmed = AlertManager.showConfirmationAlert(
            "Подтверждение удаления",
            "Вы уверены что хотите удалить этот элемент?",
            "Это действие нельзя будет отменить."
        );

         */
    }
}
