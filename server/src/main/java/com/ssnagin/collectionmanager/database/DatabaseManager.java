package com.ssnagin.collectionmanager.database;

import com.ssnagin.collectionmanager.database.interfaces.ResultSetMapper;
import com.ssnagin.collectionmanager.database.wrappers.DriverManagerWithCredentials;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseManager {

    private static volatile DatabaseManager instance;

    private Connection connection = null;
    private DatabaseCredentials credentials;

    // TEMP solution, I need to remake argparser in order to set f

    public DatabaseManager() throws SQLException {
        init(
            new DatabaseCredentials("postgres", "", "127.0.0.1", 5432, "collection")
        );
    }

    public DatabaseManager(DatabaseCredentials credentials) throws SQLException {
        init(credentials);
    }

    public static DatabaseManager getInstance() throws SQLException {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    public void init(DatabaseCredentials credentials) throws SQLException {
        this.credentials = credentials;
        establishConnection();
    }

    public void establishConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        connection = DriverManagerWithCredentials.getConnection(this.credentials);
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            establishConnection();
        }
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // NOW HERE CRUD OPERATIONS ARE PRESENTED:

    // SET PARAMETERS FOR SQL STATEMENT:

    private void setParameters(PreparedStatement statement, Object... params) throws SQLException {
        for (Integer i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
    }

    public int update(String sql, Object... params) throws SQLException {
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            setParameters(statement, params);
            return statement.executeUpdate();
        }
    }

    public <T> List<T> executeQuery(String sql, ResultSetMapper<T> mapper, Object... params)
            throws SQLException {
        List<T> results = new ArrayList<>();
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            setParameters(stmt, params);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapper.map(rs));
                }
            }
        }
        return results;
    }

    public <T> Optional<T> executeQuerySingle(String sql, ResultSetMapper<T> mapper, Object... params)
            throws SQLException {
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            setParameters(stmt, params);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapper.map(rs));
                }
                return Optional.empty();
            }
        }
    }

    /*

DatabaseManager db = DatabaseManager.getInstance();
db.executeUpdate("CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, name VARCHAR(100), email VARCHAR(100))");

int affectedRows = db.executeUpdate(
    "INSERT INTO users (name, email) VALUES (?, ?)",
    "John Doe", "john@example.com"
);

Optional<User> user = db.executeQuerySingle(
    "SELECT * FROM users WHERE id = ?",
    rs -> new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")),
    1
);

List<User> users = db.executeQuery(
    "SELECT * FROM users WHERE name LIKE ?",
    rs -> new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")),
    "%John%"
);

List<User> users = db.executeQuery(
    "SELECT * FROM users WHERE name LIKE ?",
    rs -> new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")),
    "%John%"
);

db.executeTransaction(conn -> {
    try (PreparedStatement stmt = conn.prepareStatement(
        "INSERT INTO users (name, email) VALUES (?, ?)")) {
        stmt.setString(1, "Alice");
        stmt.setString(2, "alice@example.com");
        stmt.executeUpdate();

        stmt.setString(1, "Bob");
        stmt.setString(2, "bob@example.com");
        stmt.executeUpdate();
    }
});

     */
}
