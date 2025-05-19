package com.ssnagin.collectionmanager.session.generators;

import java.security.SecureRandom;

public class SessionKeyGenerator {
    public static char[] generateSessionKey(Integer length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);

        char[] result = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = (char) (bytes[i] & 0xFF);
        }
        return result;
    }

    public static char[] generateSessionKey() {
        return generateSessionKey(1024);
    }
}
