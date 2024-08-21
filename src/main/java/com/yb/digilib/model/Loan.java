package com.yb.digilib.model;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Loan {
    private static final AtomicInteger idCounter = new AtomicInteger(0); // Compteur statique pour générer des IDs uniques

    private final IntegerProperty loanId;
    private final ObjectProperty<User> user;
    private final ObjectProperty<Book> book;
    private final ObjectProperty<LocalDate> startDate;
    private final ObjectProperty<LocalDate> endDate;
    private final StringProperty status;

    public Loan() {
        this.loanId = new SimpleIntegerProperty(idCounter.incrementAndGet());
        this.user = new SimpleObjectProperty<>();
        this.book = new SimpleObjectProperty<>();
        this.startDate = new SimpleObjectProperty<>();
        this.endDate = new SimpleObjectProperty<>();
        this.status = new SimpleStringProperty("En cours");
    }

    public Loan(User user, Book book, LocalDate startDate, LocalDate endDate) {
        this.loanId = new SimpleIntegerProperty(idCounter.incrementAndGet()); // Génère un ID unique
        this.user = new SimpleObjectProperty<>(user);
        this.book = new SimpleObjectProperty<>(book);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.endDate = new SimpleObjectProperty<>(endDate);
        this.status = new SimpleStringProperty("En cours");
    }

    // Getters & Setters pour les propriétés
    public int getLoanId() {
        return loanId.get();
    }

    public IntegerProperty loanIdProperty() {
        return loanId;
    }

    public User getUser() {
        return user.get();
    }

    public void setUser(User user) {
        this.user.set(user);
    }

    public ObjectProperty<User> userProperty() {
        return user;
    }

    public Book getBook() {
        return book.get();
    }

    public void setBook(Book book) {
        this.book.set(book);
    }

    public ObjectProperty<Book> bookProperty() {
        return book;
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate.set(startDate);
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate.get();
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate.set(endDate);
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        return endDate;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }
}
