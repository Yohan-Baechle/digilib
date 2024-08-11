package com.yb.digilib.Controllers.Layout;

import com.yb.digilib.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label usernameLbl;
    public TextField usernameFld;
    public PasswordField passwordFld;
    public Button loginBtn;
    public Label errorLbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginBtn.setOnAction(event -> onLogin());
    }

    private void onLogin() {
        Stage stage = (Stage) errorLbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showAdminWindows();
    }
}
