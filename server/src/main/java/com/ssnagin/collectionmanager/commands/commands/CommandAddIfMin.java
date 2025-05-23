/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.collection.wrappers.LocalDateWrapper;
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
public class CommandAddIfMin extends ServerCollectionCommand {

    public CommandAddIfMin(String name, CollectionManager collectionManager) {
        super(name, collectionManager);
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {

        ServerResponse response = super.executeCommand(clientRequest);
        if (response.getResponseStatus() != ResponseStatus.OK) return response;

        LocalDateWrapper musicBand = (LocalDateWrapper) clientRequest.getData();
        //List<String> errors = TempValidator.validateMusicBand(musicBand);

        // Adding into CollectionManager with Creation Date if it is the lowest element:
        try {
            if (musicBand.compareTo(this.collectionManager.getLowestElement()) != -1) {
                response.appendMessage("The element was not added");
                return response;
            }
        } catch (SQLException e) {
            response.error(e.getMessage());
        }

        try {
            this.collectionManager.addElement(
                    musicBand,
                    sessionManager.getUserId(((SessionClientRequest) clientRequest).getSessionKey())
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return response;
    }
}
