package com.ssnagin.collectionmanager.session;

import lombok.*;

@NoArgsConstructor
public class SessionKeyManager {

    @Getter
    @Setter
    private SessionKey sessionKey = null;

    @Getter
    private static SessionKeyManager instance = new SessionKeyManager();
}
