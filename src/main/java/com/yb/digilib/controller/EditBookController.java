package com.yb.digilib.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.yb.digilib.model.Book;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

public class EditBookController {

    @FXML
    private DialogPane dialogPane;

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private ComboBox<String> genreComboBox;

    @FXML
    private TextField yearField;

    @FXML
    private TextField quantityField;

    @FXML
    private Label titleErrorLabel;

    @FXML
    private Label authorErrorLabel;

    @FXML
    private Label genreErrorLabel;

    @FXML
    private Label yearErrorLabel;

    @FXML
    private Label quantityErrorLabel;

    @FXML
    private ButtonType saveButtonType;

    private Book book;

    /**
     * Initializes the controller by setting up the genre ComboBox and configuring the save button.
     */
    @FXML
    public void initialize() {
        clearErrorLabels();

        // Initialize the genre ComboBox with a predefined list of genres
        List<String> genres = Arrays.asList(
                "Fiction", "Non-fiction", "Science-fiction", "Fantasy", "Thriller",
                "Romance", "Mystère", "Historique", "Biographie", "Autobiographie",
                "Horreur", "Littérature contemporaine", "Classique", "Poésie", "Essai"
        );
        genreComboBox.getItems().addAll(genres);

        // Retrieve the "Save" button and add an event filter for validation
        Button saveButton = (Button) dialogPane.lookupButton(saveButtonType);
        saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            if (!validateInputs()) {
                // Prevent dialog from closing if inputs are invalid
                event.consume();
            }
        });
    }

    /**
     * Sets the book data in the form fields.
     *
     * @param book The book object to edit.
     */
    public void setBook(Book book) {
        this.book = book;
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        genreComboBox.setValue(book.getGenre()); // Set selected item for ComboBox

        // Show empty string if year or quantity is null
        yearField.setText(book.getYear() != null ? String.valueOf(book.getYear()) : "");
        quantityField.setText(book.getQuantityAvailable() != null ? String.valueOf(book.getQuantityAvailable()) : "");
    }

    /**
     * Retrieves the book data from the form fields and validates the inputs.
     *
     * @return The updated book object, or null if inputs are invalid.
     */
    public Book getBook() {
        book.setTitle(titleField.getText().trim());
        book.setAuthor(authorField.getText().trim());
        book.setGenre(genreComboBox.getValue()); // Get selected item from ComboBox

        try {
            String yearText = yearField.getText().trim();
            book.setYear(yearText.isEmpty() ? null : Integer.parseInt(yearText));
        } catch (NumberFormatException e) {
            yearErrorLabel.setText("L'année doit être un nombre valide.");
            return null; // Do not return the Book object if the year is invalid
        }

        try {
            String quantityText = quantityField.getText().trim();
            book.setQuantityAvailable(quantityText.isEmpty() ? null : Integer.parseInt(quantityText));
        } catch (NumberFormatException e) {
            quantityErrorLabel.setText("La quantité doit être un nombre valide.");
            return null; // Do not return the Book object if the quantity is invalid
        }

        return book;
    }

    /**
     * Validates the input fields and displays error messages if any field is invalid.
     *
     * @return true if all inputs are valid; false otherwise.
     */
    public boolean validateInputs() {
        boolean valid = true;
        clearErrorLabels();

        // Check that title field is not empty
        if (titleField.getText().trim().isEmpty()) {
            titleErrorLabel.setText("Le titre est obligatoire.");
            valid = false;
        }

        // Check that author field is not empty
        if (authorField.getText().trim().isEmpty()) {
            authorErrorLabel.setText("L'auteur est obligatoire.");
            valid = false;
        }

        // Check that genre is selected
        if (genreComboBox.getValue() == null) {
            genreErrorLabel.setText("Le genre est obligatoire.");
            valid = false;
        }

        // Check that year is a valid number within a reasonable range
        try {
            String yearText = yearField.getText().trim();
            if (!yearText.isEmpty()) {
                int year = Integer.parseInt(yearText);
                int currentYear = Year.now().getValue();
                if (year < 1000 || year > currentYear) {
                    yearErrorLabel.setText("L'année doit être comprise entre 1000 et " + currentYear + ".");
                    valid = false;
                }
            }
        } catch (NumberFormatException e) {
            yearErrorLabel.setText("L'année doit être un nombre valide.");
            valid = false;
        }

        // Check that quantity is a valid number and strictly positive
        try {
            String quantityText = quantityField.getText().trim();
            if (!quantityText.isEmpty()) {
                int quantity = Integer.parseInt(quantityText);
                if (quantity <= 0) {
                    quantityErrorLabel.setText("La quantité doit être un nombre strictement positif.");
                    valid = false;
                }
            }
        } catch (NumberFormatException e) {
            quantityErrorLabel.setText("La quantité doit être un nombre valide.");
            valid = false;
        }

        return valid;
    }

    /**
     * Clears all error labels.
     */
    private void clearErrorLabels() {
        titleErrorLabel.setText("");
        authorErrorLabel.setText("");
        genreErrorLabel.setText("");
        yearErrorLabel.setText("");
        quantityErrorLabel.setText("");
    }
}
