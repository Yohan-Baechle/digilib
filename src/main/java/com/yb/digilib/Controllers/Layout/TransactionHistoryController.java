package com.yb.digilib.Controllers.Layout;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller responsible for initializing and managing the Transaction History view.
 * It handles the population of the table and the charts with transaction data.
 */
public class TransactionHistoryController implements Initializable {

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private TableColumn<Transaction, String> transactionIdColumn;

    @FXML
    private TableColumn<Transaction, String> dateColumn;

    @FXML
    private TableColumn<Transaction, String> clientNameColumn;

    @FXML
    private TableColumn<Transaction, Double> amountColumn;

    @FXML
    private TableColumn<Transaction, String> statusColumn;

    @FXML
    private BarChart<String, Number> transactionAmountBarChart;

    @FXML
    private PieChart transactionStatusPieChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTransactionTable();
        initializeTransactionAmountBarChart();
        initializeTransactionStatusPieChart();
    }

    /**
     * Initializes the transaction table with sample data.
     */
    private void initializeTransactionTable() {
        transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<Transaction> transactions = FXCollections.observableArrayList(
                new Transaction("T001", "2024-01-15", "John Doe", 250.00, "Complétée"),
                new Transaction("T002", "2024-01-17", "Jane Smith", 150.00, "En attente"),
                new Transaction("T003", "2024-01-18", "Alice Johnson", 300.00, "Annulée"),
                new Transaction("T004", "2024-01-20", "Robert Brown", 120.00, "Complétée"),
                new Transaction("T005", "2024-01-22", "Emily White", 450.00, "Complétée")
        );

        transactionTable.setItems(transactions);
    }

    /**
     * Initializes the BarChart with sample data representing the amount of transactions per month.
     */
    private void initializeTransactionAmountBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Montants 2024");
        series.getData().add(new XYChart.Data<>("Jan", 1200));
        series.getData().add(new XYChart.Data<>("Fév", 950));
        series.getData().add(new XYChart.Data<>("Mar", 1450));
        series.getData().add(new XYChart.Data<>("Avr", 700));
        series.getData().add(new XYChart.Data<>("Mai", 1300));
        // Ajoutez plus de données selon les besoins

        transactionAmountBarChart.getData().add(series);
    }

    /**
     * Initializes the PieChart with sample data representing the distribution of transactions by status.
     */
    private void initializeTransactionStatusPieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Complétées", 60),
                new PieChart.Data("En attente", 25),
                new PieChart.Data("Annulées", 15)
        );
        transactionStatusPieChart.setData(pieChartData);
    }

    // Classe interne pour représenter une transaction
    public static class Transaction {
        private String transactionId;
        private String date;
        private String clientName;
        private Double amount;
        private String status;

        public Transaction(String transactionId, String date, String clientName, Double amount, String status) {
            this.transactionId = transactionId;
            this.date = date;
            this.clientName = clientName;
            this.amount = amount;
            this.status = status;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public String getDate() {
            return date;
        }

        public String getClientName() {
            return clientName;
        }

        public Double getAmount() {
            return amount;
        }

        public String getStatus() {
            return status;
        }
    }
}
