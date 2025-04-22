package com.ssnagin.collectionmanager.networking;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
public class ServerResponse implements Serializable {

    private Integer id;
    @Getter
    public StringBuilder message;
    @Getter
    @Setter
    public ResponseStatus responseStatus;
    @Getter
    @Setter
    public Serializable data;

    public void setMessage(String message) {
        this.message.append(message);
    }

    public ServerResponse(ResponseStatus type, String message, Serializable data) {
        setMessage(message);
        setResponseStatus(type);
        this.responseStatus = type;
        this.setData(data);
    }

    public ServerResponse() {
        this(null, null, null);
    }
}
