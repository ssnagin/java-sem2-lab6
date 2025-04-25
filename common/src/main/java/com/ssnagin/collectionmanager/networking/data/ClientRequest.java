package com.ssnagin.collectionmanager.networking.data;

import com.ssnagin.collectionmanager.inputparser.ParsedString;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
public class ClientRequest extends TransferData {

    private ParsedString parsedString;
    private Serializable data;


    public ClientRequest(ParsedString parsedString, Serializable data) {
        setParsedString(parsedString);
        setData(data);
    }

    public ClientRequest() {
        this(null, null);
    }

    public ClientRequest(Serializable data) {
        this(null, data);
    }

    public ClientRequest(ParsedString parsedString) {
        this(parsedString, null);
    }
}
