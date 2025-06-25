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
import com.ssnagin.collectionmanager.commands.interfaces.Manageable;
import com.ssnagin.collectionmanager.database.DatabaseManager;
import com.sun.source.tree.Tree;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * @author developer
 */
public class CollectionManager implements Serializable {

    // Using singleton

    @Getter
    private static CollectionManager instance;

    // TEMPORARY
    @Getter
    private final ConcurrentSkipListSet<MusicBand> collection = new ConcurrentSkipListSet<>();

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

    public void loadFromDatabase() throws SQLException {
        List<MusicBand> bands = databaseManager.executeQuery(
                "SELECT c.id, c.name, c.number_of_participants, c.singles_count, c.genre, c.created, " +
                        "cc.x AS coord_x, cc.y AS coord_y, " +
                        "ca.name AS album_name, ca.tracks AS album_tracks " +
                        "FROM cm_collection c " +
                        "JOIN cm_collection_coordinates cc ON c.coordinates_id = cc.id " +
                        "JOIN cm_collection_album ca ON c.best_album_id = ca.id",
                this::mapResultSetToMusicBand
        );

        this.collection.addAll(bands);
    }

    public void syncWithDatabase() throws SQLException {
        this.collection.clear();
        loadFromDatabase();
    }

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



        MusicBand newBand = getElementById(musicBandId);
        if (newBand != null) {
            this.collection.add(newBand);
        }

        return musicBandId;
    }

    public MusicBand getLowestElement() throws SQLException {
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


    public Long removeAllElements() throws SQLException {

        Long removedElements = (long) collection.size();

        this.databaseManager.update("DELETE FROM cm_collection");

        this.databaseManager.update("TRUNCATE TABLE cm_user_collection CASCADE");
        this.databaseManager.update("TRUNCATE TABLE cm_collection CASCADE");
        this.databaseManager.update("TRUNCATE TABLE cm_collection_coordinates CASCADE");
        this.databaseManager.update("TRUNCATE TABLE cm_collection_album CASCADE");

        this.collection.clear();

        return removedElements;
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

        MusicBand toRemove = getElementById(id);//  похоже, что сначала надо сделать это

        // Удаляем связи пользователей с коллекцией
        this.databaseManager.update(
                "DELETE FROM cm_user_collection WHERE collection_id = ?",
                id
        );

        Map<String, Long> relatedIds = this.databaseManager.executeQuerySingle(
                "SELECT coordinates_id, best_album_id FROM cm_collection WHERE id = ?",
                rs -> {
                    Map<String, Long> ids = new HashMap<>();
                    ids.put("coordinates_id", rs.getLong("coordinates_id"));
                    if (!rs.wasNull()) {
                        ids.put("best_album_id", rs.getLong("best_album_id"));
                    }
                    return ids;
                },
                id
        ).orElseThrow(() -> new NoSuchElementException(String.format("Element with id=%d does not exist", id)));

        int affectedRows = this.databaseManager.update(
                "DELETE FROM cm_collection WHERE id = ?",
                id
        );

        this.databaseManager.update(
                "DELETE FROM cm_collection_coordinates WHERE id = ?",
                relatedIds.get("coordinates_id")
        );

        if (relatedIds.containsKey("best_album_id")) {
            this.databaseManager.update(
                    "DELETE FROM cm_collection_album WHERE id = ?",
                    relatedIds.get("best_album_id")
            );
        }

//        MusicBand toRemove = getElementById(id);
//        if (toRemove != null) {
//            this.collection.remove(toRemove); // Че здесь написано, что я курил, когда это писал...
//        }

        if (toRemove != null) {
            this.collection.remove(toRemove); // а потом это
        }

    }

    public int getSize() throws SQLException {

        return this.databaseManager.executeQuerySingle(
                "SELECT COUNT(*) FROM cm_collection",
                res -> res.getInt("count")
        ).orElseThrow(() -> new SQLException("Internal error"));
    }

    public MusicBand getNthLowest(Long n) throws SQLException, IndexOutOfBoundsException {
        if (n < 0 || n >= collection.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + n);
        }

        Iterator<MusicBand> iterator = collection.iterator();
        for (int i = 0; i < n; i++) {
            iterator.next();
        }
        return iterator.next();
    }

    public boolean isEmpty() throws SQLException {
        return getSize() == 0;
    }

    public int removeLower(MusicBand element) throws SQLException {
        if (element == null || collection.isEmpty()) {
            return 0;
        }

        List<MusicBand> toRemove = collection.stream()
                .filter(mb -> mb.compareTo(element) < 0)
                .collect(Collectors.toList());

        for (MusicBand mb : toRemove) {
            removeElementById(mb.getId());
        }

        return toRemove.size();
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

}

