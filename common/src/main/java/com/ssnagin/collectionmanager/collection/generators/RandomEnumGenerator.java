package com.ssnagin.collectionmanager.collection.generators;

import com.ssnagin.collectionmanager.collection.interfaces.Randomize;

public class RandomEnumGenerator<T extends Enum<T>> implements Randomize<T> {
    private final T[] values;

    public RandomEnumGenerator(Class<T> e) {
        values = e.getEnumConstants();
    }

    public T random() {
        return values[random.nextInt(values.length)];
    }
}
