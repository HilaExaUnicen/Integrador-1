package DAOs;

import database.DatabaseConnection;

import java.sql.Connection;

public abstract class Dao {
    private Connection db;

    protected void initDb() {
        db = DatabaseConnection.getConnection();
    }

    protected void closeDb() {
        DatabaseConnection.closeConnection();
    }

    public Connection getDb() {
        return db;
    }
}
