package com.ssnagin.collectionmanager.commands;

import com.ssnagin.collectionmanager.config.Config;
import com.ssnagin.collectionmanager.networking.Networking;
import com.ssnagin.collectionmanager.networking.data.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.ServerResponse;

import java.io.IOException;

public class UserNetworkCommand extends UserCommand {

    protected Networking networking;

    public UserNetworkCommand(String name, String description, Networking networking) {
        super(name, description);

        this.networking = networking;
    }

    protected ServerResponse sendRequestSync(ClientRequest request)
            throws IOException, ClassNotFoundException, InterruptedException {

        final Object lock = new Object();
        final ServerResponse[] responseHolder = {null};
        final Exception[] exceptionHolder = {null};

        this.networking.sendClientRequest(request, response -> {
            synchronized(lock) {
                responseHolder[0] = response;
                lock.notifyAll();
            }
        });

        synchronized(lock) {
            lock.wait(3000);
            if (responseHolder[0] == null) {
                throw new IOException("Timeout waiting for response");
            }
            return responseHolder[0];
        }
    }
}
