package com.ssnagin.collectionmanager;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.ssnagin.collectionmanager.applicationstatus.ApplicationStatus;
import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.commands.UserCommand;
import com.ssnagin.collectionmanager.commands.commands.*;
import com.ssnagin.collectionmanager.config.Config;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.inputparser.InputParser;
import com.ssnagin.collectionmanager.inputparser.ParseMode;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.scripts.ScriptManager;
import lombok.Getter;
import lombok.ToString;

import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author developer
 */
@ToString
public class Core extends AbstractCore {

    @Getter
    private static Core instance = new Core();

    @Getter
    protected ApplicationStatus applicationStatus;

    public static final String LOGO = String.format(" ▗▄▄▖ ▄▄▄  █ █ ▗▞▀▚▖▗▞▀▘   ■  ▄  ▄▄▄  ▄▄▄▄  ▗▖  ▗▖▗▞▀▜▌▄▄▄▄  ▗▞▀▜▌     ▗▞▀▚▖ ▄▄▄ \n" +
            "▐▌   █   █ █ █ ▐▛▀▀▘▝▚▄▖▗▄▟▙▄▖▄ █   █ █   █ ▐▛▚▞▜▌▝▚▄▟▌█   █ ▝▚▄▟▌     ▐▛▀▀▘█    \n" +
            "▐▌   ▀▄▄▄▀ █ █ ▝▚▄▄▖      ▐▌  █ ▀▄▄▄▀ █   █ ▐▌  ▐▌     █   █           ▝▚▄▄▖█    \n" +
            "▝▚▄▄▖      █ █            ▐▌  █             ▐▌  ▐▌                 ▗▄▖           \n" +
            "                          ▐▌                                      ▐▌ ▐▌          \n" +
            "                                                                   ▝▀▜▌          \n" +
            "  ver. %s | github.com/ssnagin/java-sem2-lab5.git              ▐▙▄▞▘        \n\n", Config.Core.VERSION);

    public Core() {
        super();

        // Singletone pattern
        this.collectionManager = CollectionManager.getInstance();
        this.commandManager = CommandManager.getInstance();
        this.scriptManager = ScriptManager.getInstance();

        try {
            this.networking = new Networking("localhost", 22814);
        } catch (UnknownHostException | SocketException e) {
            throw new RuntimeException(e);
        }

        registerCommands();

        this.setApplicationStatus(ApplicationStatus.RUNNING);
    }

    private void registerCommands() {
        this.commandManager.register(new CommandExit("exit", "exit this useless piece of masterpiece"));
        this.commandManager.register(new CommandHelp("help", "display help on available commands", commandManager));
        this.commandManager.register(new CommandExecuteScript("execute_script", "some description here", commandManager, collectionManager, scriptManager));
        this.commandManager.register(new CommandAdd("add", "add an object to collection", networking, scriptManager));
        this.commandManager.register(new CommandShow("show", "show collection's elements", networking));
        this.commandManager.register(new CommandClear("clear", "clear collection elements", networking));
        this.commandManager.register(new CommandUpdate("update", "update <id> | update values of selected collection by id", networking, scriptManager));
        this.commandManager.register(new CommandRemoveById("remove_by_id", "remove_by_id <id> | removes an element with selected id", networking));
        this.commandManager.register(new CommandAddIfMin("add_if_min", "adds an element into collection if it is the lowest element in it", scriptManager, networking));
        this.commandManager.register(new CommandHistory("history", "shows last 9 executed commands", commandManager));
        // this.commandManager.register(new CommandPrintDescending("print_descending", "show collection's elements in reversed order", collectionManager));
        this.commandManager.register(new CommandCountByNumberOfParticipants("count_by_number_of_participants", "count_by_number_of_participants <numberOfParticipants>| shows the amount of fields with the same amount of participants", networking));
        // this.commandManager.register(new CommandRemoveLower("remove_lower", "removes elements that are lower than given", collectionManager, scriptManager));
        // this.commandManager.register(new CommandGroupCountingByCreationDate("group_counting_by_creation_date", "groups collection elements by creation date", collectionManager));
        this.commandManager.register(new CommandRandom("random", "random <amount> | adds to collection <amount> random elements", networking));
    }

    @Override
    public void start(String[] args) {
        super.start(args);

        // Step-by-step description of the algorithm.

        // 0. First, print logo

        this.printLogo();

        // 2. Wait for the user input.
        // After it, parse given arguments with ArgumentParser

        ParsedString parsedString;

        while (true) {

            Console.print(Console.getShellArrow());

            // I need to replace this code for the future custom input (executeCommand from script) integration.

            String inputLine = this.scriptManager.getCurrentScanner().hasNextLine()
                    ? this.scriptManager.getCurrentScanner().nextLine()
                    : "";

            parsedString = InputParser.parse(inputLine, ParseMode.COMMAND_ONLY);

            // 2.1 If the string is null, skip the code:

            if (parsedString.isEmpty()) {
                // ... some code
                continue;
            }

            // 3 Executing commands according to user's input
            this.runCommand(parsedString);
        }
    }

    public void printLogo() {
        Console.print(Core.LOGO);
    }

    protected void runCommand(ParsedString parsedString) {
        UserCommand command = (UserCommand) this.commandManager.get(parsedString.getCommand());
        this.setApplicationStatus(command.executeCommand(parsedString));
    }

    private void setApplicationStatus(ApplicationStatus applicationStatus) {

        this.applicationStatus = applicationStatus;

        if (applicationStatus != ApplicationStatus.RUNNING) {
            this.onExit();
        }
    }

    // === EVENTS ==== //

    @Override
    public void onExit() {
        // Some code here, for example saving json.

        Console.separatePrint("Bye, have a great time!", this.getApplicationStatus().toString());
        System.exit(this.getApplicationStatus().getCode());
    }

}