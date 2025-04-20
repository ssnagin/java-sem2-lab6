package com.ssnagin.collectionmanager.collection;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.collection.model.Album;
import com.ssnagin.collectionmanager.collection.model.Coordinates;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.collection.model.MusicGenre;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionManagerTest {

    CollectionManager collectionManager = CollectionManager.getInstance();

    @Test
    public void addElementTest() {

        collectionManager.addElement(
                new MusicBand(
                        "test",
                        new Coordinates(1L,2),
                        2L,
                        3,
                        MusicGenre.PSYCHEDELIC_ROCK,
                        new Album("Test",2L)
                )
        );

        collectionManager.addElement(
                new MusicBand().random()
        );
    }

    @Test
    public void getElementByIdTest() {
        this.collectionManager.getElementById(1);

        MusicBand musicBand = new MusicBand(
                123,
                "test",
                new Coordinates(1L,2),
                2L,
                3,
                MusicGenre.PSYCHEDELIC_ROCK,
                new Album("Tewst",2L)
        );

        collectionManager.addElement(
                musicBand
        );

        var element = this.collectionManager.getElementById(123);

        assertEquals(element, musicBand);
    }
}
