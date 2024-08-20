package com.yb.digilib.controller;

import com.yb.digilib.model.AppModel;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main controller responsible for managing the main layout of the application.
 * It dynamically switches views in the center of the BorderPane based on user interaction.
 */
public class MainController implements Initializable {

    public BorderPane mainPane;  // Root container for dynamically switching views

    /**
     * Initializes the controller and sets up listeners to handle view changes based on the selected menu item.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if not applicable.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Listen for changes in the selected menu item and update the view accordingly
        AppModel.getInstance().getViewFactory().getSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "dashboardOverview" -> mainPane.setCenter(AppModel.getInstance().getViewFactory().getOverviewView());
                case "booksManagement" -> mainPane.setCenter(AppModel.getInstance().getViewFactory().getBooksManagementView());
                case "usersManagement" -> mainPane.setCenter(AppModel.getInstance().getViewFactory().getUsersManagementView());
                case "loansManagement" -> mainPane.setCenter(AppModel.getInstance().getViewFactory().getLoansManagementView());
                default -> mainPane.setCenter(AppModel.getInstance().getViewFactory().getOverviewView());
            }
        });

        // Set the default view to the overview when the application starts
        mainPane.setCenter(AppModel.getInstance().getViewFactory().getOverviewView());
    }
}
