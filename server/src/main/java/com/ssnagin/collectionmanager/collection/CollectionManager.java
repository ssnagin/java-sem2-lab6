/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.collection;

import com.ssnagin.collectionmanager.collection.comparators.CoordinatesComparator;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.database.DatabaseManager;
import com.sun.source.tree.Tree;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;

/**
 * @author developer
 */
public class CollectionManager implements Serializable {

    // Using singleton

    @Getter
    private static CollectionManager instance;

    private static final TreeSet<MusicBand> collection = new TreeSet<>();

    static {
        try {
            instance = new CollectionManager(
                    DatabaseManager.getInstance()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private DatabaseManager databaseManager;

//    @Setter
//    private TreeSet<MusicBand> collection;

    public CollectionManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Integer addElement(MusicBand element) throws SQLException {
        return this.databaseManager.update(
                "INSERT INTO cm_collection (name, number_of_participants, singles_count) VALUES (?. ?, )",
                element.getId(), element.getName()

                // АДАПТИРОВАТЬ
        );
    }

    public MusicBand getLowestElement() {
        if (this.collection.isEmpty()) {
            return null;
        }
        return this.collection.first();
    }

    public void removeElement(MusicBand musicBand) {
        this.collection.remove(musicBand);
    }

    public void removeAllElements() {
        this.collection.clear();
    }

    public MusicBand getElementById(Long id) {

        return this.collection.stream()
                .filter(row -> row.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void removeElementById(Long id) throws NoSuchElementException {
        if (!collection.removeIf(musicBand -> musicBand.getId().equals(id)))
            throw new NoSuchElementException(String.format("Element with id=%d does not exist", id));
    }

    public int getSize() {
        return this.collection.size();
    }

    public MusicBand getNthLowest(Long n) {
        if (n < 0 || n >= collection.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + n);
        }
        Iterator<MusicBand> iterator = collection.iterator();
        for (int i = 0; i < n; i++) {
            iterator.next();
        }
        return iterator.next();
    }

    public boolean isEmpty() {
        return this.collection.isEmpty();
    }

    public int removeLower(MusicBand element) {
        if (element == null || collection.isEmpty()) {
            return 0;
        }

        // Получаем подмножество элементов, которые меньше заданного
        List<MusicBand> lowerElements = collection.stream()
                .filter(musicBand -> musicBand.compareTo(element) < 0)
                .toList();

        // Запоминаем количество элементов для удаления
        int count = lowerElements.size();

        // Удаляем все элементы из основной коллекции
        collection.removeAll(lowerElements);
        return count;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        if (this.collection.isEmpty()) {
            result.append("My stomach is as empty as this Collection...");
            return result.toString();
        }

        result.append("CollectionManager={\n");

        for (MusicBand musicBand : this.collection) {
            result.append(musicBand.toString()).append("\n");
        }

        result.append("}\n");

        return result.toString();
    }

    public TreeSet<MusicBand> getCollection() {
        return this.collection; // TEMPORARY
    }
}
