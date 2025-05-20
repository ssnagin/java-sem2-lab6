/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.commands.ServerDatabaseCommand;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;

/**
 * Shows brief description about available commands
 *
 * @author developer
 */
public class CommandShow extends ServerDatabaseCommand {

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

//        ServerResponse serverResponse = new ServerResponse(ResponseStatus.OK);
//        int shownElements;
//
//        if (this.collectionManager.isEmpty()) {
//            serverResponse.appendMessage("Collection is empty");
//            return serverResponse;
//        }
//
//        serverResponse.appendMessage("Collection contains " + this.collectionManager.getSize() + " elements ");
//
//        shownElements = this.collectionManager.getSize();
//
//        if (this.collectionManager.getSize() > Config.Commands.MAX_SHOWN_COLLECTION_ELEMENTS) {
//            shownElements = Config.Commands.MAX_SHOWN_COLLECTION_ELEMENTS;
//            serverResponse.appendMessage("(shown first " + shownElements + " elements, sorted by coordinates)");
//        }
//
//        NavigableSet<MusicBand> sortedMusicBands = this.collectionManager.getCollection().descendingSet();
//
//        // Limits for the response
//        serverResponse.setData(
//                sortedMusicBands.stream().limit(shownElements)
//                        .collect(
//                                Collectors.toCollection(
//                                        () -> new SerializableTreeSet<>(sortedMusicBands.comparator())
//                                )
//                        )
//        );
//
//        return serverResponse;
    }

    private ServerResponse stage1(ClientRequest clientRequest) {
        ServerResponse serverResponse = new ServerResponse(ResponseStatus.OK, "", null);

        int size = this.collectionManager.getSize();
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

        if (collectionManager.isEmpty()) {
            serverResponse.error("Collection is empty");
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
        } catch (IndexOutOfBoundsException e) {
            serverResponse.appendMessage(null);
            serverResponse.setStage(99);
        }

        return serverResponse;
    }
}
