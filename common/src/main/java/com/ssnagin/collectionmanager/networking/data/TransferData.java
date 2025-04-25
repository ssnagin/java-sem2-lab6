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

    @Getter
    @Setter
    private Integer stage = 0;

    private static long generateId() {
        return UUID.randomUUID().getLeastSignificantBits();
    }

    public TransferData(Integer stage) {
        setStage(stage);
    }
}