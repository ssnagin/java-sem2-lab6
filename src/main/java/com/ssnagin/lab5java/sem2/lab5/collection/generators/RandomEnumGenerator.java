package com.ssnagin.lab5java.sem2.lab5.collection.generators;

import com.ssnagin.lab5java.sem2.lab5.collection.interfaces.Randomize;

import java.util.Random;

public class RandomEnumGenerator<T extends Enum<T>> implements Randomize<T> {
    private final T[] values;

    public RandomEnumGenerator(Class<T> e) {
        values = e.getEnumConstants();
    }

    public T random() {
        return values[random.nextInt(values.length)];
    }
}
