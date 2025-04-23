package com.ssnagin.collectionmanager.networking.data;

import com.ssnagin.collectionmanager.networking.ResponseStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
public class ServerResponse extends TransferData {

    public StringBuilder message = new StringBuilder();
    public ResponseStatus responseStatus;
    public Serializable data;

    public ServerResponse(ResponseStatus type, String message, Serializable data) {
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
}
