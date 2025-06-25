/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.commands.ServerCollectionCommand;
import com.ssnagin.collectionmanager.database.DatabaseManager;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import com.ssnagin.collectionmanager.networking.wrappers.SessionClientRequest;

import java.sql.SQLException;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 *
 * @author developer
 */
public class CommandRemoveById extends ServerCollectionCommand {

    public CommandRemoveById(String name, CollectionManager collectionManager) {
        super(name, collectionManager);
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {

        ServerResponse serverResponse = super.executeCommand(clientRequest);
        if (serverResponse.getResponseStatus() != ResponseStatus.OK) return serverResponse;

        if (!(clientRequest.getData() instanceof Long)) {
            return serverResponse.corruption("wrong long type format");
        }

        Long id = (Long) clientRequest.getData();

        try {
            Long result = this.databaseManager.executeQuerySingle(
                    "SELECT id FROM cm_user_collection WHERE collection_id = ? AND user_id = ? LIMIT 1",
                    res -> res.getLong("id"),
                    id,
                    sessionManager.getUserId(((SessionClientRequest) clientRequest).getSessionKey())
            ).orElseThrow(() -> new SQLException("Internal error"));

            if (result == null) throw new SQLException("Element with given id does not exist");
        } catch (SQLException e) {
            return serverResponse.error("You don't have a permission to delete this object!" + e.getMessage());
        }


        try {
            collectionManager.removeElementById(id);
        } catch (SQLException e) {
            serverResponse.error(e.getMessage());
        }

        return serverResponse.ok("Successfully removed");
    }
}
