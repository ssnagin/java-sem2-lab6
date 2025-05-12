package com.ssnagin.collectionmanager.database.interfaces;


import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLTransaction {
    void execute(Connection connection) throws SQLException;
}
