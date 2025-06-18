package com.ssnagin.collectionmanager.gui;

import com.ssnagin.collectionmanager.commands.CommandManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@NoArgsConstructor
@Deprecated
// ПЕРЕДЕЛАТЬ В ЛОГИКУ Window
public class ClientGUI extends Application {

    private static ClientGUI instance = new ClientGUI();

    public static synchronized ClientGUI getInstance() {
        if (instance == null) {
            instance = new ClientGUI();
        }
        return instance;
    }

    @Getter
    @Setter
    private static Stage primaryStage;

    private static final AtomicBoolean isRunning = new AtomicBoolean(false);
    @Getter
    protected Scene scene;

    protected BorderPane root;

    @Override
    public void start(Stage stage) {

        if (isRunning.get()) {
            Platform.runLater(() -> {
                if (primaryStage != null) {
                    primaryStage.show();
                    primaryStage.toFront();
                }
            });
            return;
        }

        setPrimaryStage(stage);
        isRunning.set(true);

        initGUI();
    }

    public void launchGUI() { // Сделать реализацию открытия gui по команде; cli не убирать
        if (!isRunning.get()) {
            new Thread(() -> Application.launch(ClientGUI.class)).start();
        }
    }

    public void showGUI() {
        if (Platform.isFxApplicationThread()) {
            if (primaryStage == null) {
                primaryStage = new Stage();
                initGUI();
            }
            primaryStage.show();
            return;
        }
        Platform.runLater(() -> {
            if (primaryStage == null) {
                launchGUI();
                return;
            }
            primaryStage.show();
        });
     }

    public void hideGUI() {
        if (primaryStage != null) {
            Platform.runLater(() -> primaryStage.hide());
        }
    }

    public static boolean isGUIRunning() {
        return isRunning.get() && instance.primaryStage != null;
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @SneakyThrows
    private void initGUI() {

        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1280);
        primaryStage.setTitle("CollectionManager ver. lab 8");

        root = FXMLLoader.load(getClass().getResource("/com/ssnagin/collectionmanager/fxml/main.fxml"));

        scene = new Scene(root);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/com/ssnagin/collectionmanager/css/style.css")).toExternalForm()
        );

        primaryStage.setScene(scene);
    }
}
