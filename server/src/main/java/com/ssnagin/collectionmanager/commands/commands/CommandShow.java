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

import java.sql.SQLException;

/**
 * Shows brief description about available commands
 *
 * @author developer
 */
public class CommandShow extends ServerCollectionCommand {

    protected static final Integer MAX_PAGE_SIZE = 50;

    public CommandShow(String name, CollectionManager collectionManager) {
        super(name, collectionManager);
    }

    // Stages:
    // 0 and 2-98 -- reserved
    // 1 -- show general info (requires page_number)
    // 99 -- stop (returns back)
    // 100-x -- elements in collection (requires page_number)

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {

        ServerResponse serverResponse = super.executeCommand(clientRequest);
        if (serverResponse.getResponseStatus() != ResponseStatus.OK) return serverResponse;

        if (!(clientRequest.getData() instanceof Long))
            return new ServerResponse(ResponseStatus.CORRUPTED, "Page number must be valid", null);

        if (clientRequest.getStage() >= 100L)
            return stage100x(clientRequest);

        if (clientRequest.getStage() == 1) {
            return stage1(clientRequest);
        }

        return new ServerResponse(ResponseStatus.ERROR, "Unknown stage", null);

    }

    private ServerResponse stage1(ClientRequest clientRequest) {
        ServerResponse serverResponse = new ServerResponse(ResponseStatus.OK, "", null);

        int size;
        try {
            size = this.collectionManager.getSize();
        } catch (SQLException e) {
            return serverResponse.error(e.getMessage());
        }

        Long maxPages = (long) Math.floorDiv(size, MAX_PAGE_SIZE) + 1;
        Long currentPage = (Long) clientRequest.getData();

        if (currentPage > maxPages) return serverResponse.error("Page number is too big");

        serverResponse.appendMessage(
                "Collection contains " +
                size +
                " elements\n=    PAGE " +
                currentPage + " / " +
                maxPages
               );

        if (currentPage < maxPages) {
            serverResponse.appendMessage(
                    "  |\"show "+
                    (currentPage + 1)+
                    "\" to the next page|   ="
            );
        }

        serverResponse.setStage(100);

        return serverResponse;
    }

    private ServerResponse stage100x(ClientRequest clientRequest) {
        ServerResponse serverResponse = new ServerResponse(ResponseStatus.OK);
        serverResponse.setStage(clientRequest.getStage());

        try {
            if (collectionManager.isEmpty()) return serverResponse.ok("Collection is empty");
        } catch (SQLException e) {
            return serverResponse.error("{Error while processing collection size}");
        }

        Long currentPage = (Long) clientRequest.getData();
        Long id = (currentPage - 1) * MAX_PAGE_SIZE + (clientRequest.getStage() - 100);

        try {
            serverResponse.setData(
                    collectionManager.getNthLowest(id)
            );
            serverResponse.setStage(
                    serverResponse.getStage() + 1
            );
        } catch (IndexOutOfBoundsException | SQLException e) {
            serverResponse.appendMessage(null);
            serverResponse.setStage(99);
        }

        return serverResponse;
    }
}
