package com.ssnagin.collectionmanager.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class ServerCollectionCommand extends ServerCommand {

    protected CollectionManager collectionManager;

    public ServerCollectionCommand(String name, CollectionManager collectionManager) {
        super(name);
        setCollectionManager(collectionManager);
    }
}
