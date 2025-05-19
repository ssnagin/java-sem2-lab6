package com.ssnagin.collectionmanager.networking.wrappers;

import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.session.SessionKey;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SessionClientRequest extends ClientRequest {

    SessionKey sessionKey;

    public SessionClientRequest(ClientRequest clientRequest) {
        this(clientRequest, null);
    }

    public SessionClientRequest(ClientRequest clientRequest, SessionKey sessionKey) {
        super(
                clientRequest.getParsedString(),
                clientRequest.getData(),
                clientRequest.getStage()
        );

        setSessionKey(sessionKey);
    }

}
