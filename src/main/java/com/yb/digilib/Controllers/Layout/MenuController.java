package com.yb.digilib.Controllers.Layout;

import com.yb.digilib.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        homeBtn.setOnAction(event ->  onDashboard());
        manageBooksBtn.setOnAction(event -> onBooksManagement());
    }

    private void onDashboard() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set("overview");
    }

    private void onBooksManagement() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set("booksManagement");
    }
}