package com.ssnagin.collectionmanager.database;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatabaseCredentials {
    String username;
    String password;

    String host;
    Integer port;
    String databaseName;
}
