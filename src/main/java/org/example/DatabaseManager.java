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
        String checkSQL = "SELECT 1 FROM users WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(url)) {

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSQL)) {
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    System.out.println("User with email already exists");
                    return;
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {


                insertStmt.setString(1, name);
                insertStmt.setString(2, lastName);
                insertStmt.setString(3, email);
                insertStmt.setString(4, AuthManager.hashPassword(password));
                insertStmt.executeUpdate();

                System.out.println("Data inserted.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            java.util.Arrays.fill(password.toCharArray(), '\0');
        }
    }

    /**
     * method the verifies the user.
     * takes password and email as parameters.
     * retrieves stored hash from row that matches stored email.
     * returns user first name upon verification.
     * prints "Incorrect password" if user email found but password does not match.
     * SQL exception is caught and message is printed.
     */
    public static String verifyUserPassword(String password, String email) {
        String selectSql = "SELECT first_name,password FROM users WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(selectSql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String storedHash = rs.getString("password");

                if (AuthManager.verifyPassword(password, storedHash)) {
                    java.util.Arrays.fill(password.toCharArray(), '\0');
                    return firstName;
                } else {
                    return "Incorrect Password";
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "User not found";
    }

}
