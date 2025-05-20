/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.collection;

import com.ssnagin.collectionmanager.collection.comparators.CoordinatesComparator;
import com.ssnagin.collectionmanager.collection.model.Coordinates;
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

    @Getter
    @Setter
    private DatabaseManager databaseManager;

//    @Setter
//    private TreeSet<MusicBand> collection;

    public CollectionManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Long addElement(MusicBand element, Long userId) throws SQLException {

        Long coordinatesId = this.databaseManager.executeQuerySingle(
                "INSERT INTO cm_collection_coordinates (x, y) VALUES (?, ?) RETURNING id",
                rs -> rs.getLong("id"),
                element.getCoordinates().getX(),
                element.getCoordinates().getY()
        ).orElseThrow(() -> new SQLException("Failed to insert coordinates"));

        Long bestAlbumId = null;
        if (element.getBestAlbum() != null) {
            bestAlbumId = this.databaseManager.executeQuerySingle(
                    "INSERT INTO cm_collection_album (name, tracks) VALUES (?, ?) RETURNING id",
                    rs -> rs.getLong("id"),
                    element.getBestAlbum().getName(),
                    element.getBestAlbum().getTracks()
            ).orElseThrow(() -> new SQLException("Failed to insert album"));
        }

        Long musicBandId = this.databaseManager.executeQuerySingle(
                "INSERT INTO cm_collection (name, number_of_participants, singles_count, " +
                        "coordinates_id, genre, best_album_id) VALUES (?, ?, ?, ?, ?, ?) RETURNING id",
                rs -> rs.getLong("id"),
                element.getName(),
                element.getNumberOfParticipants(),
                element.getSinglesCount(),
                coordinatesId,
                element.getGenre().name(),
                bestAlbumId
        ).orElseThrow(() -> new SQLException("Failed to insert music band"));

        this.databaseManager.executeQuerySingle(
                "INSERT INTO cm_user_collection (user_id, collection_id)" +
                        "VALUES (?, ?) RETURNING id",
                res -> res.getLong("id"),
                userId,
                musicBandId
        ).orElseThrow(() -> new SQLException("Failed to attach connection " + userId.toString() + " - " + musicBandId.toString()));

        return musicBandId;
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
