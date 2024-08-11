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
                dashboardView = new FXMLLoader(getClass().getResource("/fxml/layout/dashboard/overview.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    public void showLoginWindows() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/layout/login/login.fxml"));
        // Taille spécifique pour la fenêtre de connexion
        createStage(loader, false, 600, 400);
    }

    public void showAdminWindows() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        // Taille spécifique pour la fenêtre d'administration (tableau de bord)
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

        // Définir la fenêtre redimensionnable ou non
        stage.setResizable(resizable);

        // Appliquer la méthode sizeToScene pour ajuster la taille de la fenêtre
        stage.sizeToScene();

        // Optionnel : Forcer la taille si nécessaire
        stage.setWidth(width);
        stage.setHeight(height);

        // Afficher la fenêtre
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
