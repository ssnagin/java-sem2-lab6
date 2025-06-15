package com.ssnagin.collectionmanager.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.gui.ClientGUI;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class UserCommand extends Command {

    protected String description;

    public UserCommand(String name, String description) {

        super(name);
        setDescription(description);
    }

    /**
     * Executes the given command and returns status code
     *
     * @param parsedString
     * @return
     */
    public ApplicationStatus executeCommand(ParsedString parsedString) {
        try {
            if ("h".equals(parsedString.getArguments().get(0)))
                return this.showUsage(parsedString);
        } catch (Exception ignored) {}

        return ApplicationStatus.RUNNING;
    }

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

    public int compareTo(UserCommand otherCommand) {
        int result = super.compareTo(otherCommand);
        if (result == 0) result = description.compareTo(otherCommand.getDescription());
        return result;
    }
}
