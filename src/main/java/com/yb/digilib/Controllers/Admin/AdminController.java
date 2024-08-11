package com.yb.digilib.Controllers.Admin;

import com.yb.digilib.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    public BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "overview" -> admin_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
                case "booksManagement" -> admin_parent.setCenter(Model.getInstance().getViewFactory().getBooksManagementView());
                default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
            }
        });
    }
}
