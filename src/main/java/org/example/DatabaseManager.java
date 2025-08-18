package org.example;

import java.sql.*;

public class DatabaseManager {
    static String url = "jdbc:sqlite:users.db";

    /**
     * method to create Users table.
     * Creates columns to store first name, last name, email and hashed password.
     * catches SQL exception and prints message.
     */
    public static void createUsersDatabase() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "first_name TEXT NOT NULL, " +
                "last_name TEXT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "password TEXT NOT NULL)";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            System.out.println("Table created or already exists.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * method to insert user data into database.
     * parameters are user's first name, last name, email, and password.
     * password is hashed before storing it into database.
     * password parameter is wiped after use.
     * method catches SQL exception and prints message
     */
    public static void insertUserToUsers(String name, String lastName, String email, String password) {
        String insertSQL = "INSERT INTO users(first_name,last_name,email,password) VALUES(?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, AuthManager.hashPassword(password));
            pstmt.executeUpdate();

            System.out.println("Data inserted.");
            java.util.Arrays.fill(password.toCharArray(), '\0');
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
