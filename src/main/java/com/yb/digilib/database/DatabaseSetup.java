package com.yb.digilib.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseSetup {
    private static final String URL = "jdbc:sqlite:digilib.db";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String createTableSQL = "CREATE TABLE IF NOT EXISTS books (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "isbn TEXT NOT NULL," +
                        "title TEXT NOT NULL," +
                        "author TEXT NOT NULL," +
                        "publisher TEXT," +
                        "genre TEXT," +
                        "year INTEGER," +
                        "quantityAvailable INTEGER" +
                        ");";
                stmt.execute(createTableSQL);
                System.out.println("Table 'books' created successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
