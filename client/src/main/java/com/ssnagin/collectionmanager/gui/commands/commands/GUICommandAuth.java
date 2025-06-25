package com.ssnagin.collectionmanager.gui.commands.commands;

import com.ssnagin.collectionmanager.events.EventType;
import com.ssnagin.collectionmanager.gui.nodes.form.GUIForm;
import com.ssnagin.collectionmanager.gui.nodes.form.GUIFormWithLogs;
import com.ssnagin.collectionmanager.gui.commands.GUINetworkCommand;
import com.ssnagin.collectionmanager.gui.window.WindowManager;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import com.ssnagin.collectionmanager.session.SessionKey;
import com.ssnagin.collectionmanager.user.objects.User;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.Text;

import java.io.IOException;

public class GUICommandAuth extends GUINetworkCommand {

    WindowManager windowManager;

    public GUICommandAuth(String name, Networking networking, WindowManager windowManager) {
        super(name, networking);

        this.windowManager = windowManager;
    }

    public void executeCommand(MouseEvent event) {
        windowManager.get("auth").show();
    }

    public void executeCommand(MouseEvent event, GUIForm form) {
        ParsedString emulatedCommand = new ParsedString("login", "login");

        if (form.getName().equals("auth")) authenticateUser(emulatedCommand, form);
        if (form.getName().equals("register")) {
            emulatedCommand.setCommand("register");
            registerUser(emulatedCommand, form);
        }
    }

    /*
    0 - Login Field
    1 - Password Field
     */
    protected void authenticateUser(ParsedString parsedString, GUIForm form) {

        if (form instanceof GUIFormWithLogs) {
            setOutputText(((GUIFormWithLogs) form).getLogArea());
        }

        TextField loginField = (TextField) form.getFields().get(0);
        TextField passwordField = (TextField) form.getFields().get(1);

        User user = new User();
        user.setUsername(loginField.getText());
        user.setPassword(passwordField.getText().toCharArray());
        user.setId(0L);
        user.setIsBanned(0);


        ClientRequest clientRequest = new ClientRequest(
                parsedString,
                user
        );

        try {
            ServerResponse serverResponse = this.networking.sendClientRequest(clientRequest);

            SessionKey sessionKey = (SessionKey) serverResponse.getData();

            sessionKeyManager.setSessionKey(sessionKey);

            out(serverResponse.getMessage());

            eventManager.publish(EventType.USER_LOGGED_IN.toString(), user);
        } catch (IOException | ClassNotFoundException e) {
            out(e.getMessage());
        }
    }

    protected void registerUser(ParsedString parsedString, GUIForm form) {
        if (form instanceof GUIFormWithLogs) {
            setOutputText(((GUIFormWithLogs) form).getLogArea());
        }

        TextField loginField = (TextField) form.getFields().get(0);
        TextField passwordField = (TextField) form.getFields().get(1);

        User user = new User();
        user.setUsername(loginField.getText());
        user.setPassword(passwordField.getText().toCharArray());
        user.setId(0L);
        user.setIsBanned(0);

        try {
            ServerResponse response = this.networking.sendClientRequest(
                    new ClientRequest(parsedString, user)
            );

            out(response.getMessage());

            eventManager.publish(EventType.USER_REGISTERED.toString(), user);

        } catch (IOException | ClassNotFoundException e) {
            out(e.getMessage());
        }
    }
}
