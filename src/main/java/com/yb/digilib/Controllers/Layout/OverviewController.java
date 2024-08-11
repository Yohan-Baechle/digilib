package com.yb.digilib.Controllers.Layout;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller responsible for initializing and managing the dashboard view.
 * It handles the population of various charts with data to display on the dashboard.
 */
public class OverviewController implements Initializable {

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

    /**
     * Initializes the dashboard view with sample data for various charts.
     * This method is automatically called after the FXML has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if not applicable.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeBorrowedBooksPieChart();
        initializeMonthlyRegistrationsAreaChart();
        initializeMonthlyRevenueStackedBarChart();
        initializeCategoryBorrowedChart();
    }

    /**
     * Initializes the PieChart with sample data representing the distribution of borrowed books by genre.
     */
    private void initializeBorrowedBooksPieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Science Fiction", 150),
                new PieChart.Data("Romance", 100),
                new PieChart.Data("Adventure", 200),
                new PieChart.Data("History", 120),
                new PieChart.Data("Fantasy", 80)
        );
        borrowedBooksPieChart.setData(pieChartData);
    }

    /**
     * Initializes the AreaChart with sample data representing monthly registrations for the current year.
     */
    private void initializeMonthlyRegistrationsAreaChart() {
        XYChart.Series<String, Number> areaChartSeries = new XYChart.Series<>();
        areaChartSeries.setName("Registrations 2024");
        areaChartSeries.getData().add(new XYChart.Data<>("Jan", 50));
        areaChartSeries.getData().add(new XYChart.Data<>("Feb", 75));
        areaChartSeries.getData().add(new XYChart.Data<>("Mar", 60));
        areaChartSeries.getData().add(new XYChart.Data<>("Apr", 85));
        areaChartSeries.getData().add(new XYChart.Data<>("May", 90));
        areaChartSeries.getData().add(new XYChart.Data<>("Jun", 120));
        monthlyRegistrationsAreaChart.getData().add(areaChartSeries);
    }

    /**
     * Initializes the StackedBarChart with sample data representing monthly revenue split into memberships and paid borrowings.
     */
    private void initializeMonthlyRevenueStackedBarChart() {
        XYChart.Series<String, Number> revenueSeries1 = new XYChart.Series<>();
        revenueSeries1.setName("Memberships");
        revenueSeries1.getData().add(new XYChart.Data<>("Jan", 200));
        revenueSeries1.getData().add(new XYChart.Data<>("Feb", 180));
        revenueSeries1.getData().add(new XYChart.Data<>("Mar", 220));
        revenueSeries1.getData().add(new XYChart.Data<>("Apr", 260));
        revenueSeries1.getData().add(new XYChart.Data<>("May", 240));

        XYChart.Series<String, Number> revenueSeries2 = new XYChart.Series<>();
        revenueSeries2.setName("Paid Borrowings");
        revenueSeries2.getData().add(new XYChart.Data<>("Jan", 150));
        revenueSeries2.getData().add(new XYChart.Data<>("Feb", 130));
        revenueSeries2.getData().add(new XYChart.Data<>("Mar", 170));
        revenueSeries2.getData().add(new XYChart.Data<>("Apr", 190));
        revenueSeries2.getData().add(new XYChart.Data<>("May", 210));

        monthlyRevenueStackedBarChart.getData().addAll(revenueSeries1, revenueSeries2);
    }

    /**
     * Initializes the BarChart with sample data representing the number of borrowings by category.
     */
    private void initializeCategoryBorrowedChart() {
        XYChart.Series<String, Number> barChartSeries = new XYChart.Series<>();
        barChartSeries.setName("Borrowings by Category");
        barChartSeries.getData().add(new XYChart.Data<>("Science Fiction", 80));
        barChartSeries.getData().add(new XYChart.Data<>("Romance", 65));
        barChartSeries.getData().add(new XYChart.Data<>("Adventure", 90));
        barChartSeries.getData().add(new XYChart.Data<>("History", 70));
        barChartSeries.getData().add(new XYChart.Data<>("Fantasy", 50));
        categoryBorrowedChart.getData().add(barChartSeries);
    }
}
