package com.yb.digilib.model;

import javafx.beans.property.*;
import java.util.concurrent.atomic.AtomicInteger;

public class User {
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    private final IntegerProperty id; // Auto-incremented ID
    private final StringProperty name;
    private final StringProperty email;
    private final StringProperty phone;
    private final StringProperty address;

    // Constructor with all parameters
    public User(String name, String email, String phone, String address) {
        this.id = new SimpleIntegerProperty(idCounter.incrementAndGet());
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.address = new SimpleStringProperty(address);
    }

    // Constructor without parameters
    public User() {
        this("", "", "", "");
    }

    // Getters & Setters for properties
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id.get() +
                ", name='" + name.get() + '\'' +
                ", email='" + email.get() + '\'' +
                ", phone='" + phone.get() + '\'' +
                ", address='" + address.get() + '\'' +
                '}';
    }
}
