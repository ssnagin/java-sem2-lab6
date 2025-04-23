package com.ssnagin.collectionmanager.networking.data;

import com.ssnagin.collectionmanager.networking.ResponseStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
public class ServerResponse implements Serializable {

    public String message;
    public ResponseStatus responseStatus;
    public Serializable data;

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
