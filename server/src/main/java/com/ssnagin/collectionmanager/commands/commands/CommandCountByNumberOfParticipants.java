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

import java.sql.SQLException;

/**
 *
 *
 * @author developer
 */
public class CommandCountByNumberOfParticipants extends ServerCollectionCommand {

    public CommandCountByNumberOfParticipants(String name, CollectionManager collectionManager) {
        super(name, collectionManager);
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {

        ServerResponse serverResponse = super.executeCommand(clientRequest);
        if (serverResponse.getResponseStatus() != ResponseStatus.OK) return serverResponse;

        Integer result;

        if (!(clientRequest.getData() instanceof Long)) {
            serverResponse.setResponseStatus(ResponseStatus.CORRUPTED);
            serverResponse.appendMessage("Wrong number format");
            return serverResponse;
        }

        Long numberOfParticipants = (Long) clientRequest.getData();

        try {
            result = this.databaseManager.executeQuerySingle(
                    "SELECT COUNT(*) FROM cm_collection WHERE number_of_participants = ?",
                    res -> res.getInt("count"),
                    numberOfParticipants
            ).orElseThrow(() -> new SQLException("Error while counting"));
        } catch (SQLException e) {
            return serverResponse.error(e.getMessage());
        }

        serverResponse.setData(result);
        serverResponse.appendMessage("Counted " + result + " participants");

        return serverResponse;
    }
}
