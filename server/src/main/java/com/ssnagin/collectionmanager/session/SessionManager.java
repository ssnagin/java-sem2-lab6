package com.ssnagin.collectionmanager.session;

import com.ssnagin.collectionmanager.session.generators.SessionKeyGenerator;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    @Getter
    private final static SessionManager instance = new SessionManager();

    private SessionManager() {}

    @Getter
    private final Map<SessionKey, SessionInfo> activeSessions = new ConcurrentHashMap<>();

    public SessionKey createNewSession(Long userId) {

        // А если мы создаем вторую сессию, т.к. одинаковые userId, надо это учесть и просто обновить время
        for (Map.Entry<SessionKey, SessionInfo> entry : activeSessions.entrySet()) {
            if (entry.getValue().getUserId().equals(userId)) {

                entry.getValue().updateActivity();
                return entry.getKey();
            }
        }

        SessionKey sessionKey = SessionKeyGenerator.generateSessionKey();
        activeSessions.put(sessionKey, new SessionInfo(userId));
        return sessionKey;
    }

    public SessionStatus checkAuth(SessionKey sessionKey) {

        if (sessionKey == null) return SessionStatus.UNAUTHORIZED;

        SessionInfo sessionInfo = activeSessions.get(sessionKey);

        if (sessionInfo == null) return SessionStatus.UNAUTHORIZED;

        if (sessionInfo.isExpired()) {
            activeSessions.remove(sessionKey);
            return SessionStatus.EXPIRED;
        }


        sessionInfo.updateActivity();

        // DANGEROUS BUT I WILL TRY...
        this.cleanupExpiredSessions(); // very dangerous thing

        return SessionStatus.LOGGED_IN;
    }

    public Long getUserId(SessionKey sessionKey) {
        SessionInfo sessionInfo = activeSessions.get(sessionKey);
        return sessionInfo != null ? sessionInfo.userId : null;
    }

    public boolean isUserLoggedIn(SessionKey sessionKey) {

        if (sessionKey == null) return false;
        return this.checkAuth(sessionKey) == SessionStatus.LOGGED_IN;
    }

    public void cleanupExpiredSessions() {
        activeSessions.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
}
