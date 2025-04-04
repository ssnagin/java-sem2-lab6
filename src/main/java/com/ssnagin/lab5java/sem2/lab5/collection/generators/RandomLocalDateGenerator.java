package com.ssnagin.lab5java.sem2.lab5.collection.generators;

import com.ssnagin.lab5java.sem2.lab5.collection.interfaces.Randomize;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("unchecked")
public class RandomLocalDateGenerator<T extends LocalDate> implements Randomize<T> {

    public T random(LocalDate startDate, LocalDate endDate) {
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();

        return (T) LocalDate.ofEpochDay(
                ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay)
        );
    }

    public T random() {
        return random(
                LocalDate.of(1, 1, 1),
                LocalDate.of(9999, 12, 31)
        );
    }
}
