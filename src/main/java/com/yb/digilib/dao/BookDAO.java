package com.yb.digilib.dao;

import com.yb.digilib.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private static final String URL = "jdbc:sqlite:digilib.db";

    public void createBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (isbn, title, author, publisher, genre, year, quantityAvailable) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getPublisher());
            pstmt.setString(5, book.getGenre());
            pstmt.setObject(6, book.getYear(), Types.INTEGER); // Handle null values
            pstmt.setObject(7, book.getQuantityAvailable(), Types.INTEGER); // Handle null values
            pstmt.executeUpdate();
        }
    }

    public Book getBook(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE id = ?";
        Book book = null;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getString("genre"),
                        rs.getObject("year", Integer.class),
                        rs.getObject("quantityAvailable", Integer.class)
                );
                // Set other fields if necessary
            }
        }
        return book;
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getString("genre"),
                        rs.getObject("year", Integer.class),
                        rs.getObject("quantityAvailable", Integer.class)
                );
                books.add(book);
            }
        }
        return books;
    }

    public void updateBook(Book book) throws SQLException {
        String sql = "UPDATE books SET isbn = ?, title = ?, author = ?, publisher = ?, genre = ?, year = ?, quantityAvailable = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getPublisher());
            pstmt.setString(5, book.getGenre());
            pstmt.setObject(6, book.getYear(), Types.INTEGER); // Handle null values
            pstmt.setObject(7, book.getQuantityAvailable(), Types.INTEGER); // Handle null values
            pstmt.setInt(8, book.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteBook(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
