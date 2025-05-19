package com.ssnagin.collectionmanager.session.generators;

import com.ssnagin.collectionmanager.session.SessionKey;

import java.security.SecureRandom;

public class SessionKeyGenerator {
    public static SessionKey generateSessionKey(Integer length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);

        char[] result = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = (char) (bytes[i] & 0xFF);
        }
        return new SessionKey(result);
    }

    public static SessionKey generateSessionKey() {
        return generateSessionKey(1024);
    }
}
