package com.ssnagin.collectionmanager.networking.serlialization.types;


import com.ssnagin.collectionmanager.collection.model.MusicBand;

import java.io.Serializable;
import java.util.Comparator;
import java.util.TreeSet;

public class SerializableTreeSet<T> extends TreeSet<T> implements Serializable {

    public SerializableTreeSet(Comparator<? super MusicBand> comparator) {
        super((Comparator<? super T>) comparator);
    }

}
