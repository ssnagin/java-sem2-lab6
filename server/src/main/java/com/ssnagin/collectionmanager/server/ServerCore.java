package com.ssnagin.collectionmanager.server;


import com.ssnagin.collectionmanager.Core;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
public class ServerCore extends Core {
    @Getter
    private static ServerCore instance = new ServerCore();

    public ServerCore() {
        super();

    }

    @Override
    public void start(String[] args) {

    }
}
