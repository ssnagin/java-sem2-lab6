package com.ssnagin.collectionmanager.collection.comparators;

import com.ssnagin.collectionmanager.collection.model.Coordinates;

import java.util.Comparator;

public class CoordinatesComparator implements Comparator<Coordinates> {

    @Override
    public int compare(Coordinates coordinates, Coordinates otherCoordinates) {
        return coordinates.compareTo(otherCoordinates);
    }
}
