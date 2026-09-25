package com.example.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
        "jdbc:postgresql://localhost:5432/project0";

    private static final String USER = 
        "project0";

    private static final String PASSWORD = 
        "1234";

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
            URL,
            USER,
            PASSWORD

        );

    }

}