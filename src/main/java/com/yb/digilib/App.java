package com.yb.digilib;

import com.yb.digilib.model.AppModel;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Entry point for the DigiLib JavaFX application.
 * This class is responsible for launching the application and displaying the initial login window.
 */
public class App extends Application {

    /**
     * The main entry point for all JavaFX applications.
     * This method is called after the system is ready for the application to start running.
     *
     * @param stage the primary stage for this application, onto which the application scene can be set.
     */
    @Override
    public void start(Stage stage) {
        // Display the login window when the application starts
        AppModel.getInstance().getViewFactory().showMainWindow();
    }

    /**
     * Main method that launches the JavaFX application.
     *
     * @param args command line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
