package com.ssnagin.collectionmanager.gui.controllers.controllers;

import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.events.EventType;
import com.ssnagin.collectionmanager.gui.commands.GUICommand;
import com.ssnagin.collectionmanager.gui.commands.commands.GUICommandAuth;
import com.ssnagin.collectionmanager.gui.commands.commands.GUICommandHelp;
import com.ssnagin.collectionmanager.gui.commands.commands.GUICommandHistory;
import com.ssnagin.collectionmanager.gui.commands.commands.GUICommandShow;
import com.ssnagin.collectionmanager.gui.controllers.GUIController;
import com.ssnagin.collectionmanager.gui.nodes.logger.GUITextLogger;
import com.ssnagin.collectionmanager.gui.nodes.table.main.GUITableMain;
import com.ssnagin.collectionmanager.user.objects.User;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.Getter;

public class MainGUIController extends GUIController {

    @FXML
    public ImageView helpCommandButton;

    @FXML
    public ImageView historyCommandButton;

    @FXML
    public ImageView loginCommandButton;

    @FXML
    public TextArea leftTextArea;

    // TABLE COLUMNS

    @FXML
    public TableView<MusicBand> mainTableView;
    @FXML
    public TableColumn<MusicBand, Long> idColumn;
//    @FXML
//    public TableColumn<MusicBand, String> ownedUsernameColumn;
    @FXML
    public TableColumn<MusicBand, String> nameColumn;
    @FXML
    public TableColumn<MusicBand, Long> coordXColumn;
    @FXML
    public TableColumn<MusicBand, Integer> coordYColumn;
    @FXML
    public TableColumn<MusicBand, Long> participantsColumn;
    @FXML
    public TableColumn<MusicBand, Integer> singlesColumn;
//    @FXML
//    public TableColumn<MusicBand, Long> bestAlbumIdColumn;
    @FXML
    public TableColumn<MusicBand, String> bestAlbumNameColumn;
    @FXML
    public TableColumn<MusicBand, Long> bestAlbumTracksColumn;
    @FXML
    public TableColumn<MusicBand, String> genreColumn;

    private GUITableMain guiTableMain;

    @Getter
    private GUITextLogger textLogger;

    @FXML
    protected void initialize() {
        super.initialize();

        initTable();
        initGUICommands();


        helpCommandButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommand) localCommandManager.get("gui_help")).executeCommand(event);
        });

        historyCommandButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommand) localCommandManager.get("gui_history")).executeCommand(event);
        });

        loginCommandButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommand) localCommandManager.get("gui_auth")).executeCommand(event);
        });
    }

    private void initGUICommands() {
        localCommandManager.register(new GUICommandHelp("gui_help", localCommandManager));
        localCommandManager.register(new GUICommandHistory("gui_history", localCommandManager));

        localCommandManager.register(new GUICommandAuth("gui_auth", networking, windowManager));

        localCommandManager.register(new GUICommandShow("gui_show", networking));
    }

    private void initTable() {
        guiTableMain = new GUITableMain(mainTableView);

        guiTableMain.setIdColumn(idColumn);
        guiTableMain.setNameColumn(nameColumn);

        guiTableMain.setCoordXColumn(coordXColumn);
        guiTableMain.setCoordYColumn(coordYColumn);

        guiTableMain.setParticipantsColumn(participantsColumn);
        guiTableMain.setSinglesColumn(singlesColumn);

        guiTableMain.setBestAlbumNameColumn(bestAlbumNameColumn);
        guiTableMain.setBestAlbumTracksColumn(bestAlbumTracksColumn);

        guiTableMain.setGenreColumn(genreColumn);

        guiTableMain.setProperties();
    }

    @Override
    protected void initEventListeners() {
        eventManager.subscribe(EventType.USER_LOGGED_IN.toString(),
                this::handleUserLoggedIn);

        textLogger = new GUITextLogger(eventManager, leftTextArea);
    }

    private void handleUserLoggedIn(User user) {
        ((GUICommandShow) localCommandManager.get("gui_show")).executeCommand(guiTableMain);
    }
}
