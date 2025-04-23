package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.commands.ServerCommand;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 *
 * @author developer
 */
public class CommandDefault extends ServerCommand {

    private String temporaryCreatedHeadMessage = "I apologize, but the given command DoEs NoT eXiSt!\n"
            + "(or it was given incorecctly)\n\n"
            + "Please, make another try :) or type help to see available commands";

    public CommandDefault(String name, String description) {
        super(name, description);
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {
        return new ServerResponse(ResponseStatus.OK, temporaryCreatedHeadMessage, null);
    }
}
