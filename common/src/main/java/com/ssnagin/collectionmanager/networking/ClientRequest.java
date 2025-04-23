package com.ssnagin.collectionmanager.networking;

import com.ssnagin.collectionmanager.inputparser.ParsedString;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
public class ClientRequest implements Serializable {

    @Getter
    @Setter
    public ParsedString parsedString;

    public ClientRequest(ParsedString parsedString) {
        setParsedString(parsedString);
    }

    public ClientRequest() {
        this(null);
    }
}
