/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.commands.ServerCollectionCommand;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.files.FileManager;
import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * Throws when other commands does not exist. The only one unregistered command!
 *
 * @author developer
 */
public class CommandSave extends ServerCollectionCommand {

    @Getter
    @Setter
    private FileManager fileManager;

    @Getter
    @Setter
    private String collectionPath;

    public CommandSave(String name, CollectionManager collectionManager, FileManager fileManager, String collectionPath) {
        super(name, collectionManager);

        setAccessible(false);

        setFileManager(fileManager);
        setCollectionPath(collectionPath);
    }

    @Override
    public ServerResponse executeCommand(ClientRequest clientRequest) {
        try {
            fileManager.write(this.collectionManager.getCollection(), collectionPath);
        } catch (Exception ex) {
            Console.error(ex);
        }

        return new ServerResponse();
    }
}
