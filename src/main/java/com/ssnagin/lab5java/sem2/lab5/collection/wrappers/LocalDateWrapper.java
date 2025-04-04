/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.lab5java.sem2.lab5.collection.wrappers;

import com.ssnagin.lab5java.sem2.lab5.collection.generators.RandomLocalDateGenerator;
import com.ssnagin.lab5java.sem2.lab5.collection.model.MusicBand;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Wrapper for localDate
 * @author developer
 */

@EqualsAndHashCode(callSuper=true)
@ToString
@Getter
@Setter
public class LocalDateWrapper extends MusicBand  {
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

        return new LocalDateWrapper(
            new MusicBand().random(),
            new RandomLocalDateGenerator<>().random()
        );
    }


    public int compareTo(LocalDateWrapper otherLocalDateWrapper) {

        if (otherLocalDateWrapper == null) return 1;

        int result = super.compareTo(otherLocalDateWrapper);

        if (result == 0) result = this.creationDate.compareTo(otherLocalDateWrapper.creationDate);

        return result;
    }
}
