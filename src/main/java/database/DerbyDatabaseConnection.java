package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyDatabaseConnection {
    private static Connection db;

    private DerbyDatabaseConnection() {
        db = DerbyDatabaseConnection.getConnection();
    }

    private static void initialize() {
        try {
            String uri = "jdbc:derby:MyDerbyDB;create=true";
            if (db == null || db.isClosed()) {
                db = DriverManager.getConnection(uri);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        try {
            if (!db.isClosed()) {
                db.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            if (db == null || db.isClosed()) {
                initialize();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return db;
    }
}
