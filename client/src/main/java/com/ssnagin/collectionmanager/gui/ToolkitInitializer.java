package com.ssnagin.collectionmanager.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ToolkitInitializer {
    private static boolean initialized = false;
    private static Stage primaryStageHolder;


    public static void start() {
        if (!initialized) {
            Platform.setImplicitExit(false);
            new Thread(() -> Application.launch(JavaFXApp.class)).start();
            initialized = true;
        }
    }

    public static class JavaFXApp extends Application {
        @Override
        public void start(Stage primaryStage) {
            primaryStageHolder = primaryStage;
            primaryStageHolder.setOnCloseRequest(event -> {
                event.consume(); // Отменяем закрытие
                primaryStageHolder.hide(); // Просто скрываем окно
            });
        }
    }

    public static void showPrimaryStage() {
        Platform.runLater(() -> {
            if (primaryStageHolder != null) {
                primaryStageHolder.show();
                primaryStageHolder.toFront();
            }
        });
    }
}
