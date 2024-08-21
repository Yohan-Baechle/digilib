package com.yb.digilib.controller;

import com.yb.digilib.model.Book;
import com.yb.digilib.model.Loan;
import com.yb.digilib.model.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class EditLoanController {

    @FXML
    private ComboBox<User> userComboBox;

    @FXML
    private ComboBox<Book> bookComboBox;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Label userErrorLabel;

    @FXML
    private Label bookErrorLabel;

    @FXML
    private Label startDateErrorLabel;

    @FXML
    private Label endDateErrorLabel;

    private Loan loan;

    /**
     * Initializes the form fields with the provided loan details.
     *
     * @param loan The loan object to edit.
     */
    public void setLoan(Loan loan) {
        this.loan = loan;

        if (loan != null) {
            userComboBox.setValue(loan.getUser());
            bookComboBox.setValue(loan.getBook());
            startDatePicker.setValue(loan.getStartDate());
            endDatePicker.setValue(loan.getEndDate());
        }
    }

    /**
     * Sets the user data to populate the userComboBox.
     *
     * @param users The list of users to display.
     */
    public void setUserData(ObservableList<User> users) {
        userComboBox.setItems(users);
    }

    /**
     * Sets the book data to populate the bookComboBox.
     *
     * @param books The list of books to display.
     */
    public void setBookData(ObservableList<Book> books) {
        bookComboBox.setItems(books);
    }

    /**
     * Retrieves the current loan object with updated details from the form.
     *
     * @return The updated loan object, or null if the inputs are invalid.
     */
    public Loan getLoan() {
        return loan;
    }

    /**
     * Validates the input fields and updates the loan object if all inputs are valid.
     *
     * @return true if all inputs are valid; false otherwise.
     */
    public boolean validateInputs() {
        boolean isValid = true;

        // Reset error messages
        userErrorLabel.setText("");
        bookErrorLabel.setText("");
        startDateErrorLabel.setText("");
        endDateErrorLabel.setText("");

        // Validate user selection
        if (userComboBox.getValue() == null) {
            userErrorLabel.setText("Veuillez sélectionner un utilisateur.");
            isValid = false;
        }

        // Validate book selection
        if (bookComboBox.getValue() == null) {
            bookErrorLabel.setText("Veuillez sélectionner un livre.");
            isValid = false;
        }

        // Validate start date selection
        if (startDatePicker.getValue() == null) {
            startDateErrorLabel.setText("Veuillez sélectionner une date d'emprunt.");
            isValid = false;
        }

        // Validate end date selection and its relation to start date
        if (endDatePicker.getValue() == null) {
            endDateErrorLabel.setText("Veuillez sélectionner une date de retour.");
            isValid = false;
        } else if (startDatePicker.getValue() != null && endDatePicker.getValue().isBefore(startDatePicker.getValue())) {
            endDateErrorLabel.setText("La date de retour ne peut pas être antérieure à la date d'emprunt.");
            isValid = false;
        }

        // Update the loan object with valid inputs
        if (isValid) {
            if (loan == null) {
                loan = new Loan();
            }
            loan.setUser(userComboBox.getValue());
            loan.setBook(bookComboBox.getValue());
            loan.setStartDate(startDatePicker.getValue());
            loan.setEndDate(endDatePicker.getValue());
        }

        return isValid;
    }
}
