/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.commands.UserCommand;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.gui.ClientGUI;
import com.ssnagin.collectionmanager.inputparser.ParsedString;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 *
 * @author developer
 */
public class CommandShowGUI extends UserCommand {

    private ClientGUI clientGUI;

    private String USAGE = "gui <argument>\n\nshow  | show gui\nhide  | hide gui";

    public CommandShowGUI(String name, String description, ClientGUI clientGUI) {

        super(name, description);
        this.clientGUI = clientGUI;
    }

    @Override
    public ApplicationStatus executeCommand(ParsedString parsedString) {

        ApplicationStatus applicationStatus = super.executeCommand(parsedString);
        if (applicationStatus != ApplicationStatus.RUNNING) return applicationStatus;

        try {
            if (parsedString.getArguments().get(0).equalsIgnoreCase("show")) {

                if (clientGUI.isGUIRunning()) {
                    clientGUI.showGUI();
                    return ApplicationStatus.RUNNING;
                }

                clientGUI.launchGUI();
                return ApplicationStatus.RUNNING;
            }

            if (parsedString.getArguments().get(0).equalsIgnoreCase("hide")) {
                clientGUI.hideGUI();
                return ApplicationStatus.RUNNING;
            }
        } catch (IndexOutOfBoundsException e) {return showUsage(parsedString);}
        catch (Exception e) {
            Console.error(e.getMessage());
            return ApplicationStatus.RUNNING;
        }

        return showUsage(parsedString);
    }

    @Override
    public ApplicationStatus showUsage(ParsedString parsedString) {
        Console.println(USAGE);
        return ApplicationStatus.RUNNING;
    }
}