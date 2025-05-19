package com.ssnagin.collectionmanager.user.objects;

import com.ssnagin.collectionmanager.description.annotations.Description;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
    private Long id = null;

    @Description(name="username", description = "Enter username")
    protected String username = null;
    private Integer isBanned = null;
    private LocalDateTime registered = LocalDateTime.now();

    private char[] password = null;
}
