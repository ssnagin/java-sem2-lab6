package com.ssnagin.collectionmanager.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import lombok.Getter;
import lombok.Setter;

public abstract class ServerCollectionCommand extends ServerCommand {

    @Getter
    @Setter
    protected CollectionManager collectionManager;

    public ServerCollectionCommand(String name, String description, CollectionManager collectionManager) {
        super(name, description);
        setCollectionManager(collectionManager);
    }
}
