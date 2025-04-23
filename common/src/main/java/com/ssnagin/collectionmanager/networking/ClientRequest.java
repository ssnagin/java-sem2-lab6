package com.ssnagin.collectionmanager.networking;

import com.ssnagin.collectionmanager.inputparser.ParsedString;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
public class ClientRequest implements Serializable {

    public ParsedString parsedString;
    public Serializable data;


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
