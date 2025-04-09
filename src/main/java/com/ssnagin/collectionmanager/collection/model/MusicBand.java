/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.collection.model;

import com.ssnagin.collectionmanager.collection.generators.RandomEnumGenerator;
import com.ssnagin.collectionmanager.description.annotations.Description;
import com.ssnagin.collectionmanager.validation.annotations.NotEmpty;
import com.ssnagin.collectionmanager.validation.annotations.NotNull;
import com.ssnagin.collectionmanager.validation.annotations.PositiveNumber;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Class for describing Music Bands
 * @author DEVELOPER
 */
@EqualsAndHashCode(callSuper=true)
@Getter
@Setter
public class MusicBand extends Entity<MusicBand> {
    
    @NotNull 
    @PositiveNumber
    @Setter(AccessLevel.NONE)
    protected Long id; // Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    
    @NotNull
    @NotEmpty
    @Description(
            name="имя", 
            description="Название музыкальной группы"
    )
    protected String name; // Поле не может быть null, Строка не может быть пустой
    
    @NotNull
    @Description(
        name="координаты", 
        description="Какие-то координаты музыкальной группы"
    )
    protected Coordinates coordinates; // Поле не может быть null
    
    @NotNull
    @PositiveNumber
    @Description(
        name="количество участников", 
        description="количество участников данной группы"
    )
    protected Long numberOfParticipants; // Поле не может быть null, Значение поля должно быть больше 0
    
    @PositiveNumber
    @Description(
        name="количество синглов", 
        description="сколько синглов выпустила данная группа"
    )
    protected Integer singlesCount; // Поле может быть null, Значение поля должно быть больше 0
    
    @Description(
        name="жанр музыки", 
        description="жанр музыки"
    )
    protected MusicGenre genre = null; // Поле может быть null
    
    @NotNull
    @Description(
        name="лучший альбом", 
        description="какой-то лучший альбом"
    )
    protected Album bestAlbum; // Поле не может быть null

    /**
     * Constructor with specified id
     * 
     * @param id
     * @param name
     * @param coordinates
     * @param numberOfParticipants
     * @param singlesCount
     * @param genre
     * @param bestAlbum 
     */
    public MusicBand(long id, String name, Coordinates coordinates, Long numberOfParticipants, Integer singlesCount, MusicGenre genre, Album bestAlbum) {
        this.setId(id);
        this.setName(name);
        this.setCoordinates(coordinates);
        this.setNumberOfParticipants(numberOfParticipants);
        this.setSinglesCount(singlesCount);
        this.setGenre(genre);
        this.setBestAlbum(bestAlbum);
    }
    
    /**
     * Constructor without specified id, it will be safely generated
     * 
     * @param name
     * @param coordinates
     * @param numberOfParticipants
     * @param singlesCount
     * @param genre
     * @param bestAlbum 
     */
    public MusicBand(String name, Coordinates coordinates, Long numberOfParticipants, Integer singlesCount, MusicGenre genre, Album bestAlbum) {
        
        this(
                MusicBand.generateId(),
                name,
                coordinates,
                numberOfParticipants,
                singlesCount,
                genre,
                bestAlbum
        );
    }
    
    /**
     * Constructor for prepared statements
     */
    public MusicBand() {
        this(
                null,
                null,
                null,
                null,
                null,
                null
                );
    }

    private static long generateId() {
        long result = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        if (result == 0) result = MusicBand.generateId();
        return result;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MusicBand={" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", numberOfParticipants=" + numberOfParticipants +
                ", singlesCount=" + singlesCount +
                ", genre=" + genre +
                ", bestAlbum=" + bestAlbum +
                '}';
    }
    
    @Override
    public int compareTo(MusicBand otherMusicBand) {

        if (otherMusicBand == null) return 1;

        int result = this.getName().compareTo(otherMusicBand.getName());
        
        if (result == 0) result = this.getSinglesCount().compareTo(otherMusicBand.getSinglesCount());
        
        if (result == 0) result = this.getCoordinates().compareTo(otherMusicBand.getCoordinates());

        if (result == 0) result = this.getBestAlbum().compareTo(otherMusicBand.getBestAlbum());

        if (result == 0) result = this.getGenre().compareTo(otherMusicBand.getGenre());

        if (result == 0) result = this.getId().compareTo(otherMusicBand.getId());
        
        return result;
    }

    public MusicBand random() {
        this.setBestAlbum(new Album().random());
        this.setCoordinates(new Coordinates().random());
        this.setGenre((MusicGenre) new RandomEnumGenerator(MusicGenre.class).random());
        this.setName(RandomStringUtils.random(RANDOM_STRING_LENGTH, true, true));
        this.setSinglesCount(random.nextInt(0, Integer.MAX_VALUE));
        this.setNumberOfParticipants(random.nextLong(0, Long.MAX_VALUE));

        return this;
    }
}