package com.yb.digilib.Controllers.Layout;

import com.yb.digilib.Models.AppModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller responsible for handling the login view.
 * It manages user input for username and password, and handles the login process.
 */
public class LoginController implements Initializable {

    public Label usernameLbl;
    public TextField usernameFld;
    public PasswordField passwordFld;
    public Button loginBtn;
    public Label errorLbl;

    /**
     * Initializes the login view. Sets up the event handler for the login button.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if not applicable.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginBtn.setOnAction(event -> onLogin());
    }

    /**
     * Handles the login process when the login button is clicked.
     * Closes the current login window and opens the main application window.
     */
    private void onLogin() {
        // Close the current stage (login window)
        Stage stage = (Stage) errorLbl.getScene().getWindow();
        AppModel.getInstance().getViewFactory().closeStage(stage);

        // Open the main application window
        AppModel.getInstance().getViewFactory().showMainWindow();
    }
}
