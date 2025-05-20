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

public class CommandRegister extends ServerCommand {

    private DatabaseManager databaseManager;

    private static String REGISTER_ERROR_TEXT = "Registration failed. Please, check the credentials once again. Probably username with such name already exists.";

    public CommandRegister(String name, DatabaseManager databaseManager) {
        super(name);

        this.databaseManager = databaseManager;
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {
        ServerResponse serverResponse = new ServerResponse(ResponseStatus.OK);

        try {
            User user = (User) clientRequest.getData();

            Long userId = this.databaseManager.executeQuerySingle(
                    "INSERT INTO cm_user (username, password, is_banned) VALUES (?, ?, ?)" +
                            "RETURNING id",
                    res -> res.getLong("id"),
                    user.getUsername(),
                    CryptoSHA1Generator.getInstance().getSHA1(user.getPassword()),
                    user.getIsBanned()
            ).orElseThrow(() -> new SQLException());

            if (userId == null) return serverResponse.error(REGISTER_ERROR_TEXT);

            serverResponse.appendMessage("Successfully registered!\n\nNow you can log in with written credentials." );

        } catch (SQLException e) {
            return serverResponse.corruption("Internal server error: " + e.getMessage());
        }

        return serverResponse;
    }
}
