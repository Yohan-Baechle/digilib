package com.yb.digilib.Views;

import com.yb.digilib.Controllers.Admin.AdminController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    private AnchorPane dashboardView;

    public ViewFactory() {}

    public AnchorPane getDashboardView() {
        if (dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/fxml/layout/dashboard.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    public void showLoginWindows() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/layout/login.fxml"));
        createStage(loader, false);
    }


    public void showAdminWindows() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader, true);
    }

    private void createStage(FXMLLoader loader, boolean resizable) {
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

        stage.show();
    }



    public void closeStage(Stage stage) {
        stage.close();
    }
}
