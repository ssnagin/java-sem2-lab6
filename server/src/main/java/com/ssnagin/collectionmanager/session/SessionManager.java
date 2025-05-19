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

        SessionInfo sessionInfo = new SessionInfo(userId);

        // А если мы создаем вторую сессию, т.к. одинаковые userId, надо это учесть и просто обновить время

        SessionKey sessionKey = SessionKeyGenerator.generateSessionKey();
        activeSessions.put(sessionKey, new SessionInfo(userId));
        return sessionKey;
    }

    public SessionStatus checkAuth(SessionKey sessionKey) {
        if (sessionKey == null) {
            return SessionStatus.UNAUTHORIZED;
        }

        SessionInfo sessionInfo = activeSessions.get(sessionKey);
        if (sessionInfo == null) {
            return SessionStatus.UNAUTHORIZED;
        }

        if (sessionInfo.isExpired()) {
            activeSessions.remove(sessionKey);
            return SessionStatus.EXPIRED;
        }


        sessionInfo.updateActivity();

        // DANGEROUS BUT I WILL TRY...
        this.cleanupExpiredSessions();

        return SessionStatus.LOGGED_IN;
    }

    public Long getUserId(SessionKey sessionKey) {
        SessionInfo sessionInfo = activeSessions.get(sessionKey);
        return sessionInfo != null ? sessionInfo.userId : null;
    }

    public void invalidateSession(SessionKey sessionKey) {
        activeSessions.remove(sessionKey);
    }

    public void cleanupExpiredSessions() {
        activeSessions.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
}
