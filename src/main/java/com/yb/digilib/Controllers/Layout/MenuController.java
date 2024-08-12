package com.yb.digilib.Controllers.Layout;

import com.yb.digilib.Models.AppModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller responsible for managing the navigation menu.
 * It handles user interactions with the menu buttons and updates the main view accordingly.
 */
public class MenuController implements Initializable {
    public Button homeBtn;
    public Button manageBooksBtn;
    public Button manageMembersBtn;
    public Button loansReturnsBtn;
    public Button transactionHistoryBtn;
    public Button reservationsBtn;
    public Button settingsBtn;
    public Button logoutBtn;
    public Button helpSupportBtn;

    /**
     * Initializes the menu controller by setting up event listeners for the menu buttons.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if not applicable.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    /**
     * Adds event listeners to the menu buttons. Each listener updates the selected menu item in the ViewFactory.
     */
    private void addListeners() {
        homeBtn.setOnAction(event -> onDashboard());
        manageBooksBtn.setOnAction(event -> onBooksManagement());
        manageMembersBtn.setOnAction(event -> onMembersManagement());
    }

    /**
     * Sets the selected menu item to "overview", which triggers the display of the dashboard view.
     */
    private void onDashboard() {
        AppModel.getInstance().getViewFactory().getSelectedMenuItem().set("dashboardOverview");
    }

    /**
     * Sets the selected menu item to "booksManagement", which triggers the display of the books management view.
     */
    private void onBooksManagement() {
        AppModel.getInstance().getViewFactory().getSelectedMenuItem().set("booksManagement");
    }

    /**
     * Sets the selected menu item to "membersManagement", which triggers the display of the members management view.
     */
    private void onMembersManagement() {
        AppModel.getInstance().getViewFactory().getSelectedMenuItem().set("membersManagement");
    }
}

