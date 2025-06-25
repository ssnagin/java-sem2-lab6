package com.ssnagin.collectionmanager.gui.controllers.controllers;

import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.commands.UserCommand;
import com.ssnagin.collectionmanager.events.EventType;
import com.ssnagin.collectionmanager.gui.commands.GUICommand;
import com.ssnagin.collectionmanager.gui.commands.commands.*;
import com.ssnagin.collectionmanager.gui.controllers.GUIController;
import com.ssnagin.collectionmanager.gui.nodes.logger.GUITextLogger;
import com.ssnagin.collectionmanager.gui.nodes.loginbar.LoginBar;
import com.ssnagin.collectionmanager.gui.nodes.table.main.GUITableMain;
import com.ssnagin.collectionmanager.locales.LangLocalesAdapter;
import com.ssnagin.collectionmanager.user.objects.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.Locale;

public class MainGUIController extends GUIController {

    @FXML
    public Label welcomeLabel;

    private CommandManager globalCommandManager;

    @FXML
    public ImageView helpCommandButton;

    @FXML
    public HBox animationArea;

    @FXML
    public ImageView historyCommandButton;

    @FXML
    public ImageView loginCommandButton;

    @FXML
    public Button logoutCommandButton;

    @FXML
    public ImageView countByNumberOfParticipantsButton;

    @FXML
    public ImageView addCommandButton;

    @FXML
    public ImageView addIfMinCommandButton;

    @FXML
    public ImageView updateCommandButton;

    @FXML
    public TextArea leftTextArea;

    @FXML
    public ImageView randomCommandButton;

    @FXML
    public ImageView clearCommandButton;

    @FXML
    public ImageView removeByIdCommand;

    @FXML
    public ImageView executeScriptCommandButton;

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

    @FXML
    public Button languageRussianButton;
    @FXML
    public Button languageEnglishButton;
    @FXML
    public Button languageBulgarianButton;
    @FXML
    public Button languageGermanButton;



//    @FXML
//    public TableColumn<Integer, Integer> localIdColumn;

    private GUITableMain guiTableMain;

    @Getter
    private GUITextLogger textLogger;

    // LOGIN BAR

    @FXML
    public VBox logoutBarPane;

    @Getter
    private LoginBar loginBar;

    @FXML
    protected void initialize() {

        if (isInitialized) return;
        isInitialized = true;

        initEventListeners();

        globalCommandManager = CommandManager.getInstance();


        loginBar = new LoginBar(loginCommandButton, logoutBarPane);

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

        logoutCommandButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommand) localCommandManager.get("gui_logout")).executeCommand(event);
        });

        addCommandButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommand) localCommandManager.get("gui_add")).executeCommand(event);
        });

        addIfMinCommandButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommand) localCommandManager.get("gui_add")).executeCommand(event); // Комбинировал тк по факту используется одно и то же окно и одна и та же логика
        });
        updateCommandButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommand) localCommandManager.get("gui_add")).executeCommand(event); // Комбинировал тк по факту используется одно и то же окно и одна и та же логика
        });

        countByNumberOfParticipantsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommand) localCommandManager.get("gui_count_members_by_id")).executeCommand(event);
        });

        randomCommandButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommand) localCommandManager.get("gui_random")).executeCommand(event);
        });

        clearCommandButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommand) localCommandManager.get("gui_clear")).executeCommand(event);
        });

        removeByIdCommand.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommand) localCommandManager.get("gui_remove")).executeCommand(event);
        });

        executeScriptCommandButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ((GUICommand) localCommandManager.get("gui_execute_script")).executeCommand(event);
        });

        animationArea.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            windowManager.get("animation").show();
            ((GUICommandShow) localCommandManager.get("gui_show")).executeCommand(guiTableMain);
        });

        initLanguageButtons();

        // В самом конце -- бросим GUI_CONTENT_LOADED

        eventManager.publish(EventType.GUI_CONTENT_LOADED.toString(), null);
    }

    private void initGUICommands() {
        localCommandManager.register(new GUICommandHelp("gui_help", localCommandManager));
        localCommandManager.register(new GUICommandHistory("gui_history", localCommandManager));

        localCommandManager.register(new GUICommandAuth("gui_auth", networking, windowManager));
        localCommandManager.register(new GUICommandLogout("gui_logout", networking));

        localCommandManager.register(new GUICommandShow("gui_show", networking));

        localCommandManager.register(new GUICommandAdd("gui_add", networking, windowManager));
        localCommandManager.register(new GUICommandRemoveById("gui_remove", networking, leftTextArea));
        localCommandManager.register(new GUICommandRandom("gui_random", networking, leftTextArea));

        localCommandManager.register(new GUICommandCountMembersById("gui_count_members_by_id", networking, leftTextArea));
        localCommandManager.register(new GUICommandClear("gui_clear", networking, leftTextArea));

        localCommandManager.register(new GUICommandExecuteScript("gui_execute_script", (UserCommand) globalCommandManager.get("execute_script")));
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

//        guiTableMain.setLocalIdColumn(localIdColumn);

        guiTableMain.setProperties();
    }

    @Override
    protected void initEventListeners() {
        eventManager.subscribe(EventType.USER_LOGGED_IN.toString(),
                this::handleUserLoggedIn);

        eventManager.subscribe(EventType.USER_LOGGED_OUT.toString(),
                this::handleUserLoggedOut);

        eventManager.subscribe(EventType.COLLECTION_DATA_CHANGED.toString(),
                this::handleTableContentRefresh);

        // EXECUTE_SCRIPT ended

        eventManager.subscribe(EventType.SCRIPTS_HAVE_BEEN_EXECUTED.toString(),
                this::handleScriptsHaveBeenExecuted);

        // GUI CONTENT LOADED

        eventManager.subscribe(EventType.GUI_CONTENT_LOADED.toString(),
                this::handleGUIContentLoaded);

        textLogger = new GUITextLogger(eventManager, leftTextArea);
    }

    private void handleUserLoggedIn(User user) {
        loginBar.showLogout();

        if (user != null) {
            welcomeLabel.textProperty().bind(
                    LangLocalesAdapter.createBinding("user.welcome", user.getUsername())
            );
        } else {
            welcomeLabel.textProperty().bind(
                    LangLocalesAdapter.createBinding("user.welcome.generic")
            );
        }

        ((GUICommandShow) localCommandManager.get("gui_show")).executeCommand(guiTableMain);
    }

    private void handleUserLoggedOut(Object emptyObject) {
        loginBar.showLogin();
    }

    private void handleTableContentRefresh(Object emptyObject) {
        ((GUICommandShow) localCommandManager.get("gui_show")).executeCommand(guiTableMain);
    }

    private void handleGUIContentLoaded(Object emptyObject) {
        if (sessionKeyManager.getSessionKey() != null) this.handleUserLoggedIn(null);
    }

    private void handleScriptsHaveBeenExecuted(Object emptyObject) {
        if (sessionKeyManager.getSessionKey() != null) this.handleUserLoggedIn(null);
    }

    private void initLanguageButtons() {
        languageRussianButton.setOnAction(event -> setLanguage("ru"));
        languageEnglishButton.setOnAction(event -> setLanguage("en"));
        languageBulgarianButton.setOnAction(event -> setLanguage("bg"));
        languageGermanButton.setOnAction(event -> setLanguage("de"));
    }

    private void setLanguage(String language) {
        LangLocalesAdapter.setLocale(new Locale(language));
        eventManager.publish(EventType.LANGUAGE_CHANGED.toString(), language);
    }
}
