package com.yb.digilib.controller;

import com.yb.digilib.model.AppModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller responsible for managing the navigation menu.
 * It handles user interactions with the menu buttons and updates the main view accordingly.
 */
public class SidebarController implements Initializable {
    public Button manageBooksBtn;
    public Button manageUsersBtn;
    public Button loansReturnsBtn;

    /**
     * Initializes the menu controller by setting up event listeners for the menu buttons.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if not applicable.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();

        updateSelectedMenu(AppModel.getInstance().getViewFactory().getSelectedMenuItem().get());

        // Listen for changes in the selected menu item to update the interface
        AppModel.getInstance().getViewFactory().getSelectedMenuItem().addListener((observable, oldValue, newValue) -> updateSelectedMenu(newValue));
    }

    /**
     * Adds event listeners to the menu buttons. Each listener updates the selected menu item in the ViewFactory.
     */
    private void addListeners() {
        manageUsersBtn.setOnAction(event -> onUsersManagement());
        manageBooksBtn.setOnAction(event -> onBooksManagement());
        loansReturnsBtn.setOnAction(event -> onLoansManagement());
    }

    /**
     * Sets the selected menu item to "usersManagement", which triggers the display of the users management view.
     */
    private void onUsersManagement() {
        AppModel.getInstance().getViewFactory().getSelectedMenuItem().set("usersManagement");
    }

    /**
     * Sets the selected menu item to "booksManagement", which triggers the display of the books management view.
     */
    private void onBooksManagement() {
        AppModel.getInstance().getViewFactory().getSelectedMenuItem().set("booksManagement");
    }

    /**
     * Sets the selected menu item to "loansManagement", which triggers the display of the loans management view.
     */
    private void onLoansManagement() {
        AppModel.getInstance().getViewFactory().getSelectedMenuItem().set("loansManagement");
    }

    /**
     * Updates the menu buttons' styles based on the selected menu item.
     *
     * @param selectedMenu The identifier for the selected menu item.
     */
    private void updateSelectedMenu(String selectedMenu) {
        // Remove 'button-selected' class from all buttons
        manageUsersBtn.getStyleClass().remove("button-selected");
        manageBooksBtn.getStyleClass().remove("button-selected");
        loansReturnsBtn.getStyleClass().remove("button-selected");

        // Add 'button-selected' class to the corresponding button
        switch (selectedMenu) {
            case "usersManagement":
                manageUsersBtn.getStyleClass().add("button-selected");
                break;
            case "booksManagement":
                manageBooksBtn.getStyleClass().add("button-selected");
                break;
            case "loansManagement":
                loansReturnsBtn.getStyleClass().add("button-selected");
                break;
            default:
                break;
        }
    }
}
