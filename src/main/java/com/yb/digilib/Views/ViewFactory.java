package com.yb.digilib.Views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Factory class responsible for managing the creation and retrieval of different views in the application.
 */
public class ViewFactory {
    private final StringProperty selectedMenuItem;
    private AnchorPane overviewView;
    private AnchorPane booksManagementView;

    /**
     * Initializes the ViewFactory with a selected menu item property.
     */
    public ViewFactory() {
        this.selectedMenuItem = new SimpleStringProperty("");
    }

    /**
     * Gets the property used to track the currently selected menu item.
     *
     * @return the selected menu item property.
     */
    public StringProperty getSelectedMenuItem() {
        return selectedMenuItem;
    }

    /**
     * Lazily loads and returns the overview view.
     * If the view has already been loaded, returns the cached view.
     *
     * @return the overview view.
     */
    public AnchorPane getOverviewView() {
        if (overviewView == null) {
            try {
                overviewView = new FXMLLoader(getClass().getResource("/fxml/layout/dashboard/dashboardOverview.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace(); // Consider logging this exception properly
            }
        }
        return overviewView;
    }

    /**
     * Lazily loads and returns the books management view.
     * If the view has already been loaded, returns the cached view.
     *
     * @return the books management view.
     */
    public AnchorPane getBooksManagementView() {
        if (booksManagementView == null) {
            try {
                booksManagementView = new FXMLLoader(getClass().getResource("/fxml/layout/dashboard/booksManagement.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace(); // Consider logging this exception properly
            }
        }
        return booksManagementView;
    }

    /**
     * Opens the login window with a specific size.
     */
    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/layout/login/loginForm.fxml"));
        createStage(loader, false, 600, 400);
    }

    /**
     * Opens the main application window with a specific size.
     */
    public void showMainWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        createStage(loader, true, 1200, 800);
    }

    /**
     * Creates and configures a new stage (window) with the specified parameters.
     *
     * @param loader    the FXMLLoader instance used to load the FXML file.
     * @param resizable whether the stage should be resizable.
     * @param width     the width of the stage.
     * @param height    the height of the stage.
     */
    private void createStage(FXMLLoader loader, boolean resizable, double width, double height) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace(); // Consider logging this exception properly
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Digilib");

        stage.setResizable(resizable);
        stage.sizeToScene(); // Adjusts the stage size to fit its content

        stage.setWidth(width);
        stage.setHeight(height);

        stage.show();
    }

    /**
     * Closes the provided stage (window).
     *
     * @param stage the stage to be closed.
     */
    public void closeStage(Stage stage) {
        stage.close();
    }
}
