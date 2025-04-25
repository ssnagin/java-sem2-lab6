package com.ssnagin.collectionmanager.networking.serlialization;

import java.io.*;

public class DataStream {

    public static byte[] serialize(Serializable obj) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        }
    }

    public static <T extends Serializable> T deserialize(byte[] data) throws IOException, ClassNotFoundException {

        if (data == null || data.length == 0) {
            throw new IOException("Empty data provided for deserialization");
        }

//        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
//             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
//            return (Class<? extends Serializable>) objectInputStream.readObject();
//        } catch (IOException e) {
//            throw new IOException(e);
//        } catch (ClassNotFoundException e) {
//            throw new CharConversionException(e.getMessage());
//        }

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (T) objectInputStream.readObject();
        }
    }
}
