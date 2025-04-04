package com.ssnagin.lab5java.sem2.lab5.commands;

import com.ssnagin.lab5java.sem2.lab5.Core;
import com.ssnagin.lab5java.sem2.lab5.collection.CollectionManager;
import com.ssnagin.lab5java.sem2.lab5.commands.commands.*;
import com.ssnagin.lab5java.sem2.lab5.files.FileManager;
import com.ssnagin.lab5java.sem2.lab5.scripts.exceptions.ScriptRecursionException;
import lombok.SneakyThrows;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CommandManagerTest {
    @Test
    public void registerTest() {
        CommandManager commandManager = CommandManager.getInstance();

        Scanner scanner = new Scanner(System.in);

        commandManager.register(new CommandExit("exit", "exit this useless piece of masterpiece"));
        commandManager.register(new CommandHelp("help", "display help on available commands", commandManager));
        commandManager.register(new CommandExecuteScript("execute_script", "some description here", commandManager, CollectionManager.getInstance()));
        commandManager.register(new CommandAdd("add", "add an object to collection", CollectionManager.getInstance(), scanner));
        commandManager.register(new CommandShow("show", "show collection's elements", CollectionManager.getInstance()));
        commandManager.register(new CommandClear("clear", "clear collection elements", CollectionManager.getInstance()));
        commandManager.register(new CommandUpdate("update", "update <id> | update values of selected collection by id", CollectionManager.getInstance(), scanner, commandManager));
        commandManager.register(new CommandRemoveById("remove_by_id", "remove_by_id <id> | removes an element with selected id", CollectionManager.getInstance()));
        commandManager.register(new CommandAddIfMin("add_if_min", "adds an element into collection if it is the lowest element in it", CollectionManager.getInstance(), commandManager, scanner));
        commandManager.register(new CommandHistory("history", "shows last 9 executed commands", commandManager));
        commandManager.register(new CommandPrintDescending("print_descending", "show collection's elements in reversed order", CollectionManager.getInstance()));
        commandManager.register(new CommandCountByNumberOfParticipants("count_by_number_of_participants", "count_by_number_of_participants <numberOfParticipants>| shows the amount of fields with the same amount of participants", CollectionManager.getInstance()));
        commandManager.register(new CommandRemoveLower("remove_lower", "removes elements that are lower than given", CollectionManager.getInstance(), scanner));
        commandManager.register(new CommandGroupCountingByCreationDate("group_counting_by_creation_date", "groups collection elements by creation date", CollectionManager.getInstance()));
        commandManager.register(new CommandSave("save", "save <filename> | saves collection to selected file. Creates if does not exist.", CollectionManager.getInstance(), FileManager.getInstance()));
        commandManager.register(new CommandRandom("random", "random <amount> | adds to collection <amount> random elements", CollectionManager.getInstance()));
    }

    @SneakyThrows
    @Test
    public void scannerStackTest() {

        Core core = Core.getInstance();

        File file = new File("test" + Integer.toString(12345));
        try {
            core.pushFileScanner(file);
        } catch (IOException ignored) {}

//        try {
//            File test = new File("12345");
//            core.pushFileScanner(test);
//            core.pushFileScanner(test);
//        } catch (ScriptRecursionException e) {
//            System.out.println(e + " was handled!");
//            core.clearActiveScripts();
//        }
    }
}
