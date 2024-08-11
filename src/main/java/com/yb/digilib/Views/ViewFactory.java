package com.yb.digilib.Views;

import com.yb.digilib.Controllers.Admin.AdminController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    private final StringProperty selectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane booksManagementView;

    public ViewFactory() {
        this.selectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getAdminSelectedMenuItem() {
        return selectedMenuItem;
    }

    public AnchorPane getDashboardView() {
        if (dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/fxml/layout/dashboard/overview.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    public AnchorPane getBooksManagementView() {
        if (booksManagementView == null) {
            try {
                booksManagementView = new FXMLLoader(getClass().getResource("/fxml/layout/dashboard/booksManagement.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return booksManagementView;
    }

    public void showLoginWindows() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/layout/login/login.fxml"));
        createStage(loader, false, 600, 400);
    }

    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader, true, 1200, 800);
    }

    private void createStage(FXMLLoader loader, boolean resizable, double width, double height) {
        Scene scene = null;

        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Digilib");

        stage.setResizable(resizable);
        stage.sizeToScene();

        stage.setWidth(width);
        stage.setHeight(height);

        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
