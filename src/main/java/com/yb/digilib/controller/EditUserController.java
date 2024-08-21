package com.yb.digilib.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.yb.digilib.model.User;

/**
 * Controller for editing user details in a dialog.
 * Manages input validation and updates the user information.
 */
public class EditUserController {

    @FXML
    private DialogPane dialogPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField addressField;

    @FXML
    private Label nameErrorLabel;

    @FXML
    private Label emailErrorLabel;

    @FXML
    private Label phoneErrorLabel;

    @FXML
    private Label addressErrorLabel;

    @FXML
    private ButtonType saveButtonType;

    private User user;

    /**
     * Initializes the controller by setting up event filters for the "Save" button
     * and clearing any existing error labels.
     */
    @FXML
    public void initialize() {
        clearErrorLabels();

        // Retrieve the "Save" button and add an event filter
        Button saveButton = (Button) dialogPane.lookupButton(saveButtonType);
        saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            if (!validateInputs()) {
                // Prevents closing the dialog if inputs are not valid
                event.consume();
            }
        });
    }

    /**
     * Initializes the form fields with the provided user details.
     *
     * @param user The user object to edit.
     */
    public void setUser(User user) {
        this.user = user;
        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        phoneField.setText(user.getPhone());
        addressField.setText(user.getAddress());
    }

    /**
     * Retrieves the current user object with updated details from the form.
     *
     * @return The updated user object.
     */
    public User getUser() {
        user.setName(nameField.getText().trim());
        user.setEmail(emailField.getText().trim());
        user.setPhone(phoneField.getText().trim());
        user.setAddress(addressField.getText().trim());
        return user;
    }

    /**
     * Validates the input fields and returns true if all inputs are valid.
     *
     * @return true if all inputs are valid; false otherwise.
     */
    public boolean validateInputs() {
        boolean valid = true;
        clearErrorLabels();

        // Validate name (no digits allowed)
        if (nameField.getText().trim().isEmpty()) {
            nameErrorLabel.setText("Le nom est requis.");
            valid = false;
        } else if (!nameField.getText().trim().matches("[a-zA-Z\\s]+")) {
            nameErrorLabel.setText("Le nom ne doit pas contenir de chiffres ou de caractères spéciaux.");
            valid = false;
        }

        // Validate email using regex
        if (emailField.getText().trim().isEmpty()) {
            emailErrorLabel.setText("L'email est requis.");
            valid = false;
        } else if (!emailField.getText().trim().matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            emailErrorLabel.setText("L'email doit être valide.");
            valid = false;
        }

        // Validate phone number using regex for French numbers
        if (phoneField.getText().trim().isEmpty()) {
            phoneErrorLabel.setText("Le numéro de téléphone est requis.");
            valid = false;
        } else if (!phoneField.getText().trim().matches("^(\\+33|0)[1-9](\\d{2}){4}$")) {
            phoneErrorLabel.setText("Le numéro de téléphone doit être valide (format français).");
            valid = false;
        }

        // Validate address (can be adapted as needed)
        if (addressField.getText().trim().isEmpty()) {
            addressErrorLabel.setText("L'adresse est requise.");
            valid = false;
        }

        return valid;
    }

    /**
     * Clears all error labels on the form.
     */
    private void clearErrorLabels() {
        nameErrorLabel.setText("");
        emailErrorLabel.setText("");
        phoneErrorLabel.setText("");
        addressErrorLabel.setText("");
    }
}
