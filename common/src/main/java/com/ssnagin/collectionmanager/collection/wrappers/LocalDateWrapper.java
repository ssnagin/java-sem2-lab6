/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.collection.wrappers;

import com.ssnagin.collectionmanager.collection.generators.RandomLocalDateGenerator;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Wrapper for localDate
 *
 * @author developer
 */

@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
public class LocalDateWrapper extends MusicBand {
    protected LocalDate creationDate;

    public LocalDateWrapper(MusicBand base) {
        this(base, LocalDate.now());
    }

    public LocalDateWrapper(MusicBand base, LocalDate localDate) {
        super(
                base.getId(),
                base.getName(),
                base.getCoordinates(),
                base.getNumberOfParticipants(),
                base.getSinglesCount(),
                base.getGenre(),
                base.getBestAlbum()
        );
        setCreationDate(localDate);
    }

    @Override
    public LocalDateWrapper random() {
        super.random();
        this.setCreationDate(new RandomLocalDateGenerator<>().random());
        return this;
    }


    public int compareTo(LocalDateWrapper otherLocalDateWrapper) {

        if (otherLocalDateWrapper == null) return 1;

        int result = super.compareTo(otherLocalDateWrapper);

        if (result == 0) result = this.creationDate.compareTo(otherLocalDateWrapper.creationDate);

        return result;
    }
}
