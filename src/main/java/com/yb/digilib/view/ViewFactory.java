package com.yb.digilib.view;

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
    private AnchorPane usersManagementView;
    private AnchorPane booksManagementView;
    private AnchorPane loansManagementView;

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
     * Lazily loads and returns the members management view.
     * If the view has already been loaded, returns the cached view.
     *
     * @return the members management view.
     */
    public AnchorPane getUsersManagementView() {
        if (usersManagementView == null) {
            try {
                usersManagementView = new FXMLLoader(getClass().getResource("/fxml/layout/users_management.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return usersManagementView;
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
                booksManagementView = new FXMLLoader(getClass().getResource("/fxml/layout/books_management.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return booksManagementView;
    }

    /**
     * Lazily loads and returns the loans management view.
     * If the view has already been loaded, returns the cached view.
     *
     * @return the loans management view.
     */
    public AnchorPane getLoansManagementView() {
        if (loansManagementView == null) {
            try {
                loansManagementView = new FXMLLoader(getClass().getResource("/fxml/layout/loans_management.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return loansManagementView;
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
        stage.setTitle("DIGILIB");
        stage.setResizable(resizable);
        stage.sizeToScene();
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
