/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.commands.ServerCollectionCommand;
import com.ssnagin.collectionmanager.database.DatabaseManager;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import com.ssnagin.collectionmanager.networking.wrappers.SessionClientRequest;

import java.sql.SQLException;

/**
 * @author developer
 */
public class CommandUpdate extends ServerCollectionCommand {

    public CommandUpdate(String name,
                         CollectionManager collectionManager) {
        super(name, collectionManager);
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {

        ServerResponse serverResponse = super.executeCommand(clientRequest);
        if (serverResponse.getResponseStatus() != ResponseStatus.OK) return serverResponse;

        try {
            serverResponse = switch (clientRequest.getStage()) {
                case 1 -> stage1(clientRequest);
                case 100 -> stage100((SessionClientRequest) clientRequest);
                default -> new ServerResponse(
                        ResponseStatus.ERROR,
                        "Unknown command stage",
                        null
                );
            };
            return serverResponse;
        } catch (Exception e) {
            logger.error(e.toString());
            return new ServerResponse(ResponseStatus.ERROR);
        }
    }

    // Receiving id;
    private ServerResponse stage1(ClientRequest clientRequest) {
        ServerResponse serverResponse = new ServerResponse(ResponseStatus.OK);
        serverResponse.setStage(100);

        if (!(clientRequest.getData() instanceof Long)) {
            serverResponse.setResponseStatus(ResponseStatus.CORRUPTED);
            serverResponse.appendMessage("Wrong long format");
            return serverResponse;
        }

        Long id = (Long) clientRequest.getData();

        try {
            Long result = this.databaseManager.executeQuerySingle(
                    "SELECT id FROM cm_user_collection WHERE collection_id = ? AND user_id = ? LIMIT 1",
                        res -> res.getLong("id"),
                    id,
                    sessionManager.getUserId(((SessionClientRequest) clientRequest).getSessionKey())
                    ).orElseThrow(() -> new SQLException(""));

            if (result == null) throw new SQLException("");
        } catch (SQLException e) {
            return serverResponse.error("You don't have a permission to update this object!");
        }

        MusicBand musicBand = null;
        try {
            musicBand = this.collectionManager.getElementById(id);
        } catch (SQLException e) {
            return serverResponse.error(e.getMessage());
        }

        if (musicBand == null) {
            serverResponse.appendMessage("Collection does not exist");
            serverResponse.setResponseStatus(ResponseStatus.ERROR);
            return serverResponse;
        }

        serverResponse.appendMessage("Collection with givren id exists");

        return serverResponse;
    }

    //  id;
    private ServerResponse stage100(SessionClientRequest clientRequest) {
        ServerResponse serverResponse = new ServerResponse(ResponseStatus.OK);

        MusicBand musicBand = (MusicBand) clientRequest.getData();
        Long musicBandId = musicBand.getId();

        try {
            this.collectionManager.removeElementById(musicBandId);
        } catch (SQLException e) {
            serverResponse.error(e.getMessage());
        }


        try {
            this.collectionManager.addElement(
                    musicBand,
                    sessionManager.getUserId(clientRequest.getSessionKey())
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return serverResponse.ok("Successfully updated!");
    }
}