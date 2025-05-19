package com.ssnagin.collectionmanager.networking.data.server;

import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.TransferData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString(callSuper = true)
@Getter
@Setter
public class ServerResponse extends TransferData {

    public StringBuilder message = new StringBuilder();
    public ResponseStatus responseStatus;
    public Serializable data;

    public ServerResponse(ResponseStatus type, String message, Serializable data) {
        this(type, message, data, 0);
    }

    public ServerResponse(ResponseStatus type, String message, Serializable data, Integer stage) {
        super(stage);

        if (message == null) message = "";

        appendMessage(message);
        setResponseStatus(type);
        this.responseStatus = type;
        this.setData(data);
    }

    public ServerResponse(ResponseStatus responseStatus) {
        this(responseStatus, null, null);
    }

    public void appendMessage(String message) {
        this.message.append(message);
    }

    public ServerResponse() {
        this(null, null, null);
    }

    public ServerResponse error(String message) {
        setResponseStatus(ResponseStatus.ERROR);
        setMessage(new StringBuilder().append(message));

        return this;
    }

    public ServerResponse corruption(String message) {
        setResponseStatus(ResponseStatus.CORRUPTED);
        setMessage(new StringBuilder().append(message));

        return this;
    }

    public ServerResponse ok(String message) {
        setResponseStatus(ResponseStatus.OK);
        setMessage(new StringBuilder().append(message));

        return this;
    }
}
