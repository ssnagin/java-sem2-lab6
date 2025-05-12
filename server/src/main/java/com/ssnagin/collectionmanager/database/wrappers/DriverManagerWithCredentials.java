package com.ssnagin.collectionmanager.database.wrappers;

import com.ssnagin.collectionmanager.database.DatabaseCredentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManagerWithCredentials {

    public static Connection getConnection(DatabaseCredentials credentials) throws SQLException {
        return DriverManager.getConnection(
          String.format("jdbc:postgresql://%s:%d/%s", credentials.getHost(), credentials.getPort(), credentials.getDatabaseName()),
                credentials.getUsername(), credentials.getPassword()
        );
    }
}
