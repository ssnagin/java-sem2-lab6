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
import com.ssnagin.collectionmanager.validation.TempValidator;

import java.sql.SQLException;
import java.util.List;

/**
 * Shows brief description about available commands
 *
 * @author developer
 */
public class CommandAdd extends ServerCollectionCommand {

    public CommandAdd(String name, CollectionManager collectionManager) {
        super(name, collectionManager);
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {

        ServerResponse response = super.executeCommand(clientRequest);
        if (response.getResponseStatus() != ResponseStatus.OK) return response;

        MusicBand musicBand = (MusicBand) clientRequest.getData();
        List<String> errors = TempValidator.validateMusicBand(musicBand);

        if (!errors.isEmpty()) {
            response.setResponseStatus(ResponseStatus.ERROR);
            for (String error : errors) {
                System.out.println(error);
                response.appendMessage(error + "\n");
            }
            return response;
        }

        try {
            this.collectionManager.addElement(
                    musicBand,
                    sessionManager.getUserId(((SessionClientRequest) clientRequest).getSessionKey())
            ); // Created row in the table

        } catch (SQLException e) {
            return response.error(e.getMessage());
        }

        response.appendMessage("Successfully added music band");

        return response;
    }
}
