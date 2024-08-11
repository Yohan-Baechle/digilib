package com.yb.digilib.Controllers.Layout;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Text usernameLbl;

    @FXML
    private PieChart borrowedBooksPieChart;

    @FXML
    private AreaChart<String, Number> monthlyRegistrationsAreaChart;

    @FXML
    private StackedBarChart<String, Number> monthlyRevenueStackedBarChart;

    @FXML
    private BarChart<String, Number> categoryBorrowedChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialisation des données pour le PieChart (Répartition des emprunts)
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Science Fiction", 150),
                new PieChart.Data("Romance", 100),
                new PieChart.Data("Aventure", 200),
                new PieChart.Data("Histoire", 120),
                new PieChart.Data("Fantastique", 80)
        );
        borrowedBooksPieChart.setData(pieChartData);

        // Initialisation des données pour l'AreaChart (Inscriptions mensuelles)
        XYChart.Series<String, Number> areaChartSeries = new XYChart.Series<>();
        areaChartSeries.setName("Inscriptions 2024");
        areaChartSeries.getData().add(new XYChart.Data<>("Jan", 50));
        areaChartSeries.getData().add(new XYChart.Data<>("Feb", 75));
        areaChartSeries.getData().add(new XYChart.Data<>("Mar", 60));
        areaChartSeries.getData().add(new XYChart.Data<>("Apr", 85));
        areaChartSeries.getData().add(new XYChart.Data<>("May", 90));
        areaChartSeries.getData().add(new XYChart.Data<>("Jun", 120));
        monthlyRegistrationsAreaChart.getData().add(areaChartSeries);

        // Initialisation des données pour le StackedBarChart (Revenus mensuels)
        XYChart.Series<String, Number> revenueSeries1 = new XYChart.Series<>();
        revenueSeries1.setName("Adhésions");
        revenueSeries1.getData().add(new XYChart.Data<>("Jan", 200));
        revenueSeries1.getData().add(new XYChart.Data<>("Feb", 180));
        revenueSeries1.getData().add(new XYChart.Data<>("Mar", 220));
        revenueSeries1.getData().add(new XYChart.Data<>("Apr", 260));
        revenueSeries1.getData().add(new XYChart.Data<>("May", 240));

        XYChart.Series<String, Number> revenueSeries2 = new XYChart.Series<>();
        revenueSeries2.setName("Emprunts payants");
        revenueSeries2.getData().add(new XYChart.Data<>("Jan", 150));
        revenueSeries2.getData().add(new XYChart.Data<>("Feb", 130));
        revenueSeries2.getData().add(new XYChart.Data<>("Mar", 170));
        revenueSeries2.getData().add(new XYChart.Data<>("Apr", 190));
        revenueSeries2.getData().add(new XYChart.Data<>("May", 210));

        monthlyRevenueStackedBarChart.getData().addAll(revenueSeries1, revenueSeries2);

        // Initialisation des données pour le BarChart (Emprunts par catégorie)
        XYChart.Series<String, Number> barChartSeries = new XYChart.Series<>();
        barChartSeries.setName("Emprunts par catégorie");
        barChartSeries.getData().add(new XYChart.Data<>("Science Fiction", 80));
        barChartSeries.getData().add(new XYChart.Data<>("Romance", 65));
        barChartSeries.getData().add(new XYChart.Data<>("Aventure", 90));
        barChartSeries.getData().add(new XYChart.Data<>("Histoire", 70));
        barChartSeries.getData().add(new XYChart.Data<>("Fantastique", 50));
        categoryBorrowedChart.getData().add(barChartSeries);
    }
}
