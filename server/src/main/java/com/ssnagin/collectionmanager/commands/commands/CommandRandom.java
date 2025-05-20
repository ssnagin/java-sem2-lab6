/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.collection.wrappers.LocalDateWrapper;
import com.ssnagin.collectionmanager.commands.ServerDatabaseCommand;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;

import java.sql.SQLException;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 *
 * @author developer
 */
public class CommandRandom extends ServerDatabaseCommand {

    private static final int MAX_RANDOM_AMOUNT = 50;

    public CommandRandom(String name, CollectionManager collectionManager) {
        super(name, collectionManager);
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {

        ServerResponse serverResponse = super.executeCommand(clientRequest);
        if (serverResponse.getResponseStatus() != ResponseStatus.OK) return serverResponse;

        long id;

        try {
            id = (long) clientRequest.getData();
        } catch (Exception e) {
            serverResponse.setResponseStatus(ResponseStatus.ERROR);
            serverResponse.appendMessage("Enter valid number");
            return serverResponse;
        }

        if (id > MAX_RANDOM_AMOUNT) {
            serverResponse.setResponseStatus(ResponseStatus.ERROR);
            serverResponse.appendMessage("Max amount error");
            return serverResponse;
        }

        for (long i = 0; i < id; i++) {
            try {
                this.collectionManager.addElement(
                        new LocalDateWrapper(new MusicBand()).random()
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        serverResponse.appendMessage(String.format("%d items were added", id));
        return serverResponse;
    }
}
