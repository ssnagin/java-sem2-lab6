package com.ssnagin.collectionmanager.user.objects;

import com.ssnagin.collectionmanager.description.annotations.Description;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id = null;

    @Description(name="username", description = "Enter username")
    protected String username = null;
    private Boolean isBanned = null;
    private LocalDateTime created = LocalDateTime.now();

    private char[] password = null;
}
