package com.ssnagin.collectionmanager.session;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {

    @Getter
    private final static SessionManager instance = new SessionManager();

//    private static final Map<String, Map<String, Object>> sessions = new HashMap<>();
//
//    public static String createSession() {
//        String sessionId = generateSessionId();
//        sessions.put(sessionId, new HashMap<>());
//        return sessionId;
//    }
//
//    private static String generateSessionId() {
//        return null;
//    }
//
//    public static Map<String, Object> getSession(String sessionId) {
//        return sessions.get(sessionId);
//    }
//
//    public static void setSessionAttribute(String sessionId, String key, Object value) {
//        Map<String, Object> session = sessions.get(sessionId);
//        if (session != null) {
//            session.put(key, value);
//        }
//    }
//
//    public static Object getSessionAttribute(String sessionId, String key) {
//        Map<String, Object> session = sessions.get(sessionId);
//        return (session != null) ? session.get(key) : null;
//    }
}
