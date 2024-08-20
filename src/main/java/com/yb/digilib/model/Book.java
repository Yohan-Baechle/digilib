package com.yb.digilib.model;

import javafx.beans.property.*;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Book {
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    private final IntegerProperty id; // Auto-incremented ID
    private final StringProperty isbn; // Auto-generated unique ISBN
    private final StringProperty title;
    private final StringProperty author;
    private final StringProperty publisher;
    private final StringProperty genre;
    private final IntegerProperty year; // Nullable value
    private final IntegerProperty quantityAvailable; // Nullable value

    // Constructor with all parameters
    public Book(String title, String author, String publisher, String genre, Integer year, Integer quantityAvailable) {
        this.id = new SimpleIntegerProperty(idCounter.incrementAndGet());
        this.isbn = new SimpleStringProperty(generateIsbn());
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.publisher = new SimpleStringProperty(publisher);
        this.genre = new SimpleStringProperty(genre);
        this.year = new SimpleIntegerProperty(year != null ? year : -1); // Use -1 to indicate undefined
        this.quantityAvailable = new SimpleIntegerProperty(quantityAvailable != null ? quantityAvailable : -1); // Use -1 to indicate undefined
    }

    // Constructor without parameters
    public Book() {
        this("", "", "", "", null, null); // Use null to indicate undefined values
    }

    // Auto-generate a unique ISBN
    private String generateIsbn() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 13);
    }

    // Getters & Setters for properties
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getIsbn() {
        return isbn.get();
    }

    public StringProperty isbnProperty() {
        return isbn;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public StringProperty authorProperty() {
        return author;
    }

    public String getPublisher() {
        return publisher.get();
    }

    public void setPublisher(String publisher) {
        this.publisher.set(publisher);
    }

    public StringProperty publisherProperty() {
        return publisher;
    }

    public String getGenre() {
        return genre.get();
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public Integer getYear() {
        return year.get() >= 0 ? year.get() : null; // Return null if year is undefined
    }

    public void setYear(Integer year) {
        this.year.set(year != null ? year : -1); // Use -1 to indicate undefined
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable.get() >= 0 ? quantityAvailable.get() : null; // Return null if quantity is undefined
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable.set(quantityAvailable != null ? quantityAvailable : -1); // Use -1 to indicate undefined
    }

    public IntegerProperty quantityAvailableProperty() {
        return quantityAvailable;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id.get() +
                ", isbn='" + isbn.get() + '\'' +
                ", title='" + title.get() + '\'' +
                ", author='" + author.get() + '\'' +
                ", publisher='" + publisher.get() + '\'' +
                ", genre='" + genre.get() + '\'' +
                ", year=" + (year.get() >= 0 ? year.get() : "") +
                ", quantityAvailable=" + (quantityAvailable.get() >= 0 ? quantityAvailable.get() : "") +
                '}';
    }
}
