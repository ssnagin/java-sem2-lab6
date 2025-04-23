package com.ssnagin.collectionmanager.networking.serlializer;

import com.ssnagin.collectionmanager.networking.ServerResponse;

import java.io.*;

public class DataStream {

    public static byte[] serialize(Serializable obj) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        }
    }

    public static ServerResponse deserialize(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (ServerResponse) objectInputStream.readObject();
        } catch (IOException e) {
            throw new IOException(e);
        } catch (ClassNotFoundException e) {
            throw new CharConversionException(e.getMessage());
        }
    }
}
