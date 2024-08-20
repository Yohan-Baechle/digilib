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

    @FXML
    public void initialize() {
        clearErrorLabels();

        // Initialiser la ComboBox avec une liste complète de genres
        List<String> genres = Arrays.asList(
                "Fiction", "Non-fiction", "Science-fiction", "Fantasy", "Thriller",
                "Romance", "Mystère", "Historique", "Biographie", "Autobiographie",
                "Horreur", "Littérature contemporaine", "Classique", "Poésie", "Essai"
        );
        genreComboBox.getItems().addAll(genres);

        // Récupérer le bouton "Sauvegarder"
        Button saveButton = (Button) dialogPane.lookupButton(saveButtonType);
        saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            if (!validateInputs()) {
                // Empêche la fermeture du dialogue
                event.consume();
            }
        });
    }

    public void setBook(Book book) {
        this.book = book;
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        genreComboBox.setValue(book.getGenre()); // Set selected item for ComboBox

        // Show empty string if year is null or undefined
        yearField.setText(book.getYear() != null ? String.valueOf(book.getYear()) : "");
        quantityField.setText(book.getQuantityAvailable() != null ? String.valueOf(book.getQuantityAvailable()) : "");
    }

    public Book getBook() {
        book.setTitle(titleField.getText().trim());
        book.setAuthor(authorField.getText().trim());
        book.setGenre(genreComboBox.getValue()); // Get selected item from ComboBox

        try {
            String yearText = yearField.getText().trim();
            book.setYear(yearText.isEmpty() ? null : Integer.parseInt(yearText));
        } catch (NumberFormatException e) {
            yearErrorLabel.setText("L'année doit être un nombre valide.");
            return null; // Ne pas retourner l'objet Book si l'année n'est pas valide
        }

        try {
            String quantityText = quantityField.getText().trim();
            book.setQuantityAvailable(quantityText.isEmpty() ? null : Integer.parseInt(quantityText));
        } catch (NumberFormatException e) {
            quantityErrorLabel.setText("La quantité doit être un nombre valide.");
            return null; // Ne pas retourner l'objet Book si la quantité n'est pas valide
        }

        return book;
    }

    public boolean validateInputs() {
        boolean valid = true;
        clearErrorLabels();

        // Vérifier que les champs ne sont pas vides
        if (titleField.getText().trim().isEmpty()) {
            titleErrorLabel.setText("Le titre est obligatoire.");
            valid = false;
        }

        if (authorField.getText().trim().isEmpty()) {
            authorErrorLabel.setText("L'auteur est obligatoire.");
            valid = false;
        }

        if (genreComboBox.getValue() == null) {
            genreErrorLabel.setText("Le genre est obligatoire.");
            valid = false;
        }

        // Vérifier que l'année est un nombre valide et dans une plage raisonnable
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

        // Vérifier que la quantité est un nombre valide et positif
        try {
            String quantityText = quantityField.getText().trim();
            if (!quantityText.isEmpty()) {
                int quantity = Integer.parseInt(quantityText);
                if (quantity < 0) {
                    quantityErrorLabel.setText("La quantité doit être un nombre positif.");
                    valid = false;
                }
            }
        } catch (NumberFormatException e) {
            quantityErrorLabel.setText("La quantité doit être un nombre valide.");
            valid = false;
        }

        return valid;
    }

    private void clearErrorLabels() {
        titleErrorLabel.setText("");
        authorErrorLabel.setText("");
        genreErrorLabel.setText("");
        yearErrorLabel.setText("");
        quantityErrorLabel.setText("");
    }
}
