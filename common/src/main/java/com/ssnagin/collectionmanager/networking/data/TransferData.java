package com.ssnagin.collectionmanager.networking.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@ToString
@EqualsAndHashCode
public abstract class TransferData implements Serializable {

    @Getter
    @Setter
    private Long id = generateId();

    private static long generateId() {
        return UUID.randomUUID().getLeastSignificantBits();
    }
}