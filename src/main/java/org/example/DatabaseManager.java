package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    public static void createUsersDatabase(){
        String url = "jdbc:sqlite:users.db";

        String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "first_name TEXT NOT NULL, "+
                "last_name TEXT NOT NULL, "+
                "email TEXT NOT NULL, "+
                "password TEXT NOT NULL)";

        try(Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement()){
            stmt.execute(createUsersTable);
            System.out.println("Table created or already exists.");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
