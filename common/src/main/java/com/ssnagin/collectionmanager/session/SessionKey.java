package com.ssnagin.collectionmanager.session;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class SessionKey implements Serializable {
    private char[] sessionKey;
}
