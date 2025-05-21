/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.collection;

import com.ssnagin.collectionmanager.collection.comparators.CoordinatesComparator;
import com.ssnagin.collectionmanager.collection.model.Album;
import com.ssnagin.collectionmanager.collection.model.Coordinates;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.collection.model.MusicGenre;
import com.ssnagin.collectionmanager.collection.wrappers.LocalDateWrapper;
import com.ssnagin.collectionmanager.database.DatabaseManager;
import com.sun.source.tree.Tree;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.sql.ResultSet;
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

        Long bestAlbumId = this.databaseManager.executeQuerySingle(
                "INSERT INTO cm_collection_album (name, tracks) VALUES (?, ?) RETURNING id",
                rs -> rs.getLong("id"),
                element.getBestAlbum().getName(),
                element.getBestAlbum().getTracks()
        ).orElseThrow(() -> new SQLException("Failed to insert album"));

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

    public void removeElement(MusicBand musicBand) throws SQLException {
        if (musicBand == null || musicBand.getId() == null) {
            return;
        }
        removeElementById(musicBand.getId());
    }


    public void removeAllElements() throws SQLException {
        this.databaseManager.update("TRUNCATE TABLE cm_user_collection CASCADE");
        this.databaseManager.update("TRUNCATE TABLE cm_collection CASCADE");
        this.databaseManager.update("TRUNCATE TABLE cm_collection_coordinates CASCADE");
        this.databaseManager.update("TRUNCATE TABLE cm_collection_album CASCADE");
    }

    public MusicBand getElementById(Long id) throws SQLException {

        if (id == null) {
            return null;
        }

        return this.databaseManager.executeQuerySingle(
                "SELECT c.id, c.name, c.number_of_participants, c.singles_count, c.genre, c.created, " +
                        "cc.x AS coord_x, cc.y AS coord_y, " +
                        "ca.name AS album_name, ca.tracks AS album_tracks " +
                        "FROM cm_collection c " +
                        "JOIN cm_collection_coordinates cc ON c.coordinates_id = cc.id " +
                        "JOIN cm_collection_album ca ON c.best_album_id = ca.id " +
                        "WHERE c.id = ?",
                        this::mapResultSetToMusicBand,
                    id
                    ).orElse(null);
    }

    public void removeElementById(Long id) throws SQLException, NoSuchElementException {
        if (id == null) {
            throw new NoSuchElementException("Element id cannot be null");
        }

        int affectedRows = this.databaseManager.update(
                "DELETE FROM cm_collection WHERE id = ?",
                id
        );

        if (affectedRows == 0) {
            throw new NoSuchElementException(String.format("Element with id=%d does not exist", id));
        }
    }

    public int getSize() throws SQLException {

        return this.databaseManager.executeQuerySingle(
                "SELECT COUNT(*) FROM cm_collection",
                res -> res.getInt("count")
        ).orElseThrow(() -> new SQLException("Internal error"));
    }

    public MusicBand getNthLowest(Long n) throws SQLException, IndexOutOfBoundsException {
        if (n < 0 || n >= getSize()) {
            throw new IndexOutOfBoundsException("Invalid index: " + n);
        }

        return this.databaseManager.executeQuerySingle(
                "SELECT c.id, c.name, c.number_of_participants, c.singles_count, c.genre, c.created, " +
                        "cc.x AS coord_x, cc.y AS coord_y, " +
                        "ca.name AS album_name, ca.tracks AS album_tracks " +
                        "FROM cm_collection c " +
                        "JOIN cm_collection_coordinates cc ON c.coordinates_id = cc.id " +
                        "JOIN cm_collection_album ca ON c.best_album_id = ca.id " +
                        "ORDER BY c.number_of_participants ASC, c.id ASC " +
                        "LIMIT 1 OFFSET ?",
                this::mapResultSetToMusicBand,
                n
        ).orElseThrow(() -> new SQLException("Failed to get nth lowest element"));
    }

    public boolean isEmpty() throws SQLException {
        return getSize() == 0;
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

    private MusicBand mapResultSetToMusicBand(ResultSet rs) throws SQLException {
        LocalDateWrapper musicBand = new LocalDateWrapper(new MusicBand());
        musicBand.setId(rs.getLong("id"));
        musicBand.setName(rs.getString("name"));
        musicBand.setNumberOfParticipants(rs.getLong("number_of_participants"));
        musicBand.setSinglesCount(rs.getInt("singles_count"));
        musicBand.setGenre(MusicGenre.valueOf(rs.getString("genre")));
        musicBand.setCreationDate(rs.getTimestamp("created").toLocalDateTime().toLocalDate());

        Coordinates coordinates = new Coordinates();
        coordinates.setX(rs.getLong("coord_x"));
        coordinates.setY(rs.getInt("coord_y"));
        musicBand.setCoordinates(coordinates);

        Album album = new Album();
        album.setName(rs.getString("album_name"));
        album.setTracks(rs.getLong("album_tracks"));
        musicBand.setBestAlbum(album);

        return musicBand;
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
