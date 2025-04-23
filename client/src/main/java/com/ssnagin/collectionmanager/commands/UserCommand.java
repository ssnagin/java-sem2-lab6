package com.ssnagin.collectionmanager.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.ParsedString;

public abstract class UserCommand extends Command {

    public UserCommand(String name, String description) {
        super(name, description);
    }

    /**
     * Executes the given command and returns status code
     *
     * @param parsedString
     * @return
     */
    public abstract ApplicationStatus executeCommand(ParsedString parsedString);

    /**
     * basic usage of a command
     *
     * @param parsedString
     * @return
     */
    public ApplicationStatus showUsage(ParsedString parsedString) {

        Console.log("Usage is still not implemented: " + parsedString.toString());

        return ApplicationStatus.RUNNING;
    }
}
