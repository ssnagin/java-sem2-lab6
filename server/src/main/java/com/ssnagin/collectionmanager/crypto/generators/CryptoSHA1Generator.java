package com.ssnagin.collectionmanager.crypto.generators;

import lombok.Getter;
import lombok.SneakyThrows;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class CryptoSHA1Generator {

    protected static MessageDigest messageDigest;

    static {
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    private static CryptoSHA1Generator instance = new CryptoSHA1Generator();

    public String getSHA1(char[] sequence) {

        // Convert char[] to byte[]
        byte[] bytes = new byte[sequence.length * 2];
        for (int i = 0; i < sequence.length; i++) {
            bytes[i * 2] = (byte) (sequence[i] >> 8);
            bytes[i * 2 + 1] = (byte) sequence[i];
        }

        byte[] hashBytes = messageDigest.digest(bytes);

        // Clear sensitive data
        messageDigest.reset();
        java.util.Arrays.fill(bytes, (byte) 0);

        return HexFormat.of().formatHex(hashBytes);

    }

    public String getSHA1(String string)  {
        return getSHA1(string.toCharArray());
    }
}
