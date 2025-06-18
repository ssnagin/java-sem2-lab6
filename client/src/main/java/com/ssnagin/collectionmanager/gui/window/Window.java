package com.ssnagin.collectionmanager.gui.window;

import com.ssnagin.collectionmanager.gui.ClientGUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Window implements Comparable<Window> {

    private String name;

    private WindowParameters windowParameters;

    private Stage stage;
    private Scene scene;

    private static final AtomicBoolean isRunning = new AtomicBoolean(false);

    public Window(String name, WindowParameters windowParameters, Boolean autoLaunch) {
        this.name = name;
        this.windowParameters = windowParameters;

        if (!autoLaunch) launch();
    }

    public Window(String name, WindowParameters windowParameters) {
        this(name, windowParameters, false);
    }

    public void launch() {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(this::initStage);
        } else {
            initStage();
        }
    }

    private void initStage() {
        this.stage = new Stage();
        stage.setTitle(windowParameters.getTitle());
        stage.setMinWidth(windowParameters.getMinWidth());
        stage.setMinHeight(windowParameters.getMinHeight());
    }

    public void initGUI() throws IOException {
        if (stage == null) {
            initStage();
        }

        Pane root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource(windowParameters.getPageUri()))
        );

        scene = new Scene(root);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource(windowParameters.getStylesUri())).toExternalForm()
        );

        stage.setScene(scene);
    }

    public void show() {
        if (Platform.isFxApplicationThread()) {
            showWindow();
        } else {
            Platform.runLater(this::showWindow);
        }
    }

    private void showWindow() {
        try {
            if (stage == null || scene == null) {
                initGUI();
            }
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hide() {
        if (stage != null) {
            if (Platform.isFxApplicationThread()) {
                stage.hide();
            } else {
                Platform.runLater(() -> stage.hide());
            }
        }
    }

    public void close() {
        if (stage != null) {
            if (Platform.isFxApplicationThread()) {
                stage.close();
            } else {
                Platform.runLater(() -> stage.close());
            }
        }
    }

    public boolean isShowing() {
        return stage != null && stage.isShowing();
    }

    @Override
    public int compareTo(Window o) {
        return this.name.compareTo(o.getName());
    }
}
