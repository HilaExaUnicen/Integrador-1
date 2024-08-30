package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection db;

    private DatabaseConnection() {
        db = DatabaseConnection.getConnection();
    }

    private static void initialize(){
        if(db == null){
            String uri = "jdbc:derby:MyDerbyDB;create=true";
            try {
                db = DriverManager.getConnection(uri);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void closeConnection(){
        try {
            if(!db.isClosed()){
                db.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(){
        if(db == null){
            initialize();
        }

        return db;
    }
}
