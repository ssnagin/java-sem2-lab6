package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.commands.ServerCommand;
import com.ssnagin.collectionmanager.crypto.generators.CryptoSHA1Generator;
import com.ssnagin.collectionmanager.database.DatabaseManager;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import com.ssnagin.collectionmanager.session.SessionKey;
import com.ssnagin.collectionmanager.session.SessionManager;
import com.ssnagin.collectionmanager.user.excetions.NoSuchUserException;
import com.ssnagin.collectionmanager.user.objects.User;

import java.sql.SQLException;
import java.util.List;

public class CommandLogin extends ServerCommand {

    private SessionManager sessionManager;

    private DatabaseManager databaseManager;

    private static String LOGIN_ERROR_TEXT = "Login failed. Please, check the credentials once again";

    public CommandLogin(String name, DatabaseManager databaseManager, SessionManager sessionManager) {
        super(name);

        this.sessionManager = sessionManager;
        this.databaseManager = databaseManager;
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {
        ServerResponse serverResponse = new ServerResponse(ResponseStatus.OK);

        try {

            User user = (User) clientRequest.getData();
            logger.debug(CryptoSHA1Generator.getInstance().getSHA1(user.getPassword()));
            List<User> response = this.databaseManager.executeQuery("SELECT * FROM cm_user WHERE username = ? AND password = ? LIMIT 1",
                    res -> new User(
                            res.getLong("id"),
                            res.getString("username"),
                            res.getInt("is_banned"),
                            res.getTimestamp("created").toLocalDateTime(),
                            res.getString("password").toCharArray()
                    ),
                    user.getUsername(),
                    CryptoSHA1Generator.getInstance().getSHA1(user.getPassword())
            );

            if (response.isEmpty()) return serverResponse.error(LOGIN_ERROR_TEXT);

            user = response.get(0);

            if (user.getIsBanned() == 1) {
                return serverResponse.error("YOU ARE BANNED!!!");
            }

            SessionKey sessionKey = sessionManager.createNewSession(user.getId());

            serverResponse.setData(sessionKey);

            serverResponse.appendMessage("Successfully logged in as " + user.getUsername() + "!\n\nYour session key -- " +
                    String.valueOf(sessionKey.getSessionKey()).substring(0, 50) + "...\n\nexpires after 20 minutes of inactivity.");

            if (response.isEmpty()) throw new NoSuchUserException("");

        } catch (SQLException e) {
            return serverResponse.corruption("Internal server error: " + e.getMessage());
        } catch (NoSuchUserException e) {
            return serverResponse.error(LOGIN_ERROR_TEXT);
        }

        return serverResponse;
    }
}
