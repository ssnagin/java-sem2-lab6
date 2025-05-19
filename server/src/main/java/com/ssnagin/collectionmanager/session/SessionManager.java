package com.ssnagin.collectionmanager.session;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {

    @Getter
    private final static SessionManager instance = new SessionManager();

    private SessionStatus checkAuth(SessionKey sessionKey) {
        return SessionStatus.LOGGED_IN;
        // REMAKE THIS
    }
}
