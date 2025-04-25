/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.commands.ServerCommand;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;

/**
 * @author developer
 */
public class CommandUpdate extends ServerCommand {

    private CollectionManager collectionManager;
    private CommandManager commandManager;

    public CommandUpdate(String name,
                         String description,
                         CollectionManager collectionManager,
                         CommandManager commandManager) {
        super(name, description);

        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {

        try {
            ServerResponse serverResponse = switch (clientRequest.getStage()) {
                case 1 -> stage1(clientRequest);
                case 100 -> stage100(clientRequest);
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

        MusicBand musicBand = this.collectionManager.getElementById(id);

        if (musicBand == null) {
            serverResponse.appendMessage("Collection does not exist");
            serverResponse.setResponseStatus(ResponseStatus.ERROR);
            return serverResponse;
        }

        serverResponse.appendMessage("Collection with givren id exists");

        return serverResponse;
    }

    //  id;
    private ServerResponse stage100(ClientRequest clientRequest) {
        ServerResponse serverResponse = new ServerResponse(ResponseStatus.OK);

        MusicBand musicBand = (MusicBand) clientRequest.getData();
        Long musicBandId = musicBand.getId();

        this.collectionManager.removeElementById(musicBandId);
        this.collectionManager.addElement(musicBand);

        return serverResponse;
    }
}