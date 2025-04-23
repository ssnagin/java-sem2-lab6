package com.ssnagin.collectionmanager.collection.interfaces;

import java.util.Random;

/**
 * Something that can be set with random values once.
 */
public interface Randomize<T> {
    T random();

    int RANDOM_STRING_LENGTH = 8;
    Random random = new Random();
}
