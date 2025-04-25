package com.ssnagin.collectionmanager.collection.comparators;

import com.ssnagin.collectionmanager.collection.model.MusicBand;

import java.util.Comparator;

public class CoordinatesComparator implements Comparator<MusicBand> {

    @Override
    public int compare(MusicBand musicBand, MusicBand otherMusicBand) {

        if (musicBand.equals(otherMusicBand)) return 0;
        if (musicBand == null) return -1;
        if (otherMusicBand == null) return 1;

        return musicBand.getCoordinates()
                .compareTo(otherMusicBand.getCoordinates());

    }
}
