package com.ssnagin.collectionmanager.gui.commands.commands;

import com.ssnagin.collectionmanager.gui.commands.GUICommand;
import com.ssnagin.collectionmanager.gui.managers.AlertManager;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class GUICommandHelp extends GUICommand {

    private Scene scene;

    StringBuilder preparedCommandList;

    public GUICommandHelp(String name) {
        super(name);

        preparedCommandList = new StringBuilder();
//        scene = clientGUI.getScene();

        initGUI();
    }

    public void initGUI() {
//        ArrayList<String> sortedKeys = new ArrayList<>(this.commandManager.getCommands().keySet());
//        Collections.sort(sortedKeys);
//
//        UserCommand selectedCommand;
//
//        for (String command : sortedKeys) {
//            selectedCommand = (UserCommand) this.commandManager.get(command);
//            preparedCommandList.append(selectedCommand.getName() + " | " + selectedCommand.getDescription() + "\n");
//        }

//        helpCommandButton = (ImageView) scene.getRoot().lookup("#command_help");

//        helpCommandButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//            AlertManager.showInfoAlert("test", "This is a test2", "This is a test3");
//        });
    }

//    @FXML
//    public void onMouseClicked(MouseEvent event) {
//
//    }

    public void executeCommand(MouseEvent event) {
        AlertManager.showInfoAlert("Help", "test 2", this.preparedCommandList.toString());
    }
}
