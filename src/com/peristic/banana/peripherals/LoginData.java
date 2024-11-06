package com.peristic.banana.peripherals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginData {

    // Database URL, username, and password
    private final String DB_URL = "jdbc:mysql://localhost:3306/uob";
    private final String DB_USERNAME = "root"; // Replace with your DB username
    private final String DB_PASSWORD = ""; // Replace with your DB password

    // Method to check if the username and password are correct by querying the database
    public boolean checkPassword(String username, String passwd) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // 1. Connect to the database
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // 2. Create SQL query
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

            // 3. Prepare the statement
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username); // Set username in the query
            stmt.setString(2, passwd);   // Set password in the query (plain text for now)

            // 4. Execute the query
            rs = stmt.executeQuery();

            // 5. Check if user exists
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Method to sign up a new user
    public boolean signUp(String name, String username, String passwd) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // 1. Connect to the database
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // 2. Create SQL query to insert new user
            String sql = "INSERT INTO users (name, username, password, high_score) VALUES (?, ?, ?, 0)";

            // 3. Prepare the statement
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name); // Set name in the query
            stmt.setString(2, username); // Set username in the query
            stmt.setString(3, passwd);   // Set password in the query (plain text for now)

            // 4. Execute the update (insert the user into the database)
            int rowsInserted = stmt.executeUpdate();

            // 5. Check if insertion was successful
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Method to retrieve the high score of a user
    public int getHighScore(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int highScore = 0;

        try {
            // 1. Connect to the database
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // 2. Create SQL query to get high score
            String sql = "SELECT high_score FROM users WHERE username = ?";

            // 3. Prepare the statement
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            // 4. Execute the query
            rs = stmt.executeQuery();

            // 5. Retrieve the high score
            if (rs.next()) {
                highScore = rs.getInt("high_score");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return highScore;
    }

    // Method to update the high score of a user
    public boolean updateHighScore(String username, int newHighScore) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // 1. Connect to the database
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // 2. Create SQL query to update high score
            String sql = "UPDATE users SET high_score = ? WHERE username = ?";

            // 3. Prepare the statement
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, newHighScore);
            stmt.setString(2, username);

            // 4. Execute the update
            int rowsUpdated = stmt.executeUpdate();

            // 5. Check if update was successful
            return rowsUpdated > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
