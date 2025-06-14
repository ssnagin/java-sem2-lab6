package com.ssnagin.collectionmanager.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class ClientGUI extends Application {

    @Getter
    @Setter
    private static Stage primaryStage;

    protected Scene scene;
    protected BorderPane root;

    @Override
    public void init() {

    }

    @Override
    public void start(Stage stage) {

        root = new BorderPane();
        root.getStyleClass().add("root");

        scene = new Scene(root);

        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/com/ssnagin/collectionmanager/css/style.css")).toExternalForm()
        );

        stage.setMinHeight(720);
        stage.setMinWidth(1200);

        stage.setTitle("CollectionManager ver. lab 8");

        stage.setScene(scene);
        stage.show();
    }

    public static void launchGUI() { // Сделать реализацию открытия gui по команде; cli не убирать
        new Thread(() -> Application.launch(ClientGUI.class)).start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
