package com.yb.digilib.controller;

import com.yb.digilib.model.Loan;
import com.yb.digilib.model.User;
import com.yb.digilib.model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Controller for managing loans in the application.
 * Handles displaying, adding, editing, and filtering loans.
 */
public class LoansManagementController {

    @FXML
    private Button addLoanBtn;

    @FXML
    private TableView<Loan> loansTable;

    @FXML
    private TableColumn<Loan, Integer> loanIdColumn;

    @FXML
    private TableColumn<Loan, String> userColumn;

    @FXML
    private TableColumn<Loan, String> bookColumn;

    @FXML
    private TableColumn<Loan, LocalDate> startDateColumn;

    @FXML
    private TableColumn<Loan, LocalDate> endDateColumn;

    @FXML
    private TableColumn<Loan, String> statusColumn;

    @FXML
    private TableColumn<Loan, Void> actionColumn;

    @FXML
    private TextField searchField;

    @FXML
    private Label statusLabel;

    private ObservableList<Loan> loanData;

    /**
     * Initializes the controller by setting up table columns, loan data, search field, and actions column.
     */
    @FXML
    public void initialize() {
        initializeColumns();
        initializeLoanData();
        initializeSearchField();
        initializeActionsColumn();
    }

    /**
     * Initializes the columns of the table view.
     */
    private void initializeColumns() {
        // Disable column reordering
        loanIdColumn.setReorderable(false);
        userColumn.setReorderable(false);
        bookColumn.setReorderable(false);
        startDateColumn.setReorderable(false);
        endDateColumn.setReorderable(false);
        statusColumn.setReorderable(false);
        actionColumn.setReorderable(false);

        // Configure columns to use observable properties
        loanIdColumn.setCellValueFactory(cellData -> cellData.getValue().loanIdProperty().asObject());
        userColumn.setCellValueFactory(cellData -> cellData.getValue().userProperty().get().nameProperty());
        bookColumn.setCellValueFactory(cellData -> cellData.getValue().bookProperty().get().titleProperty());
        startDateColumn.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
        endDateColumn.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        // Set column widths as percentages of table width
        loanIdColumn.prefWidthProperty().bind(loansTable.widthProperty().multiply(0.05));
        userColumn.prefWidthProperty().bind(loansTable.widthProperty().multiply(0.20));
        bookColumn.prefWidthProperty().bind(loansTable.widthProperty().multiply(0.25));
        startDateColumn.prefWidthProperty().bind(loansTable.widthProperty().multiply(0.15));
        endDateColumn.prefWidthProperty().bind(loansTable.widthProperty().multiply(0.15));
        statusColumn.prefWidthProperty().bind(loansTable.widthProperty().multiply(0.10));
        actionColumn.prefWidthProperty().bind(loansTable.widthProperty().multiply(0.10));
    }

    /**
     * Initializes the loan data with sample data.
     */
    private void initializeLoanData() {
        loanData = FXCollections.observableArrayList(
                new Loan(new User("John Doe", "john.doe@example.com", "+33123456789", "123 Main St"),
                        new Book("1984", "George Orwell", "Penguin Books", "Dystopian", 1949, 5),
                        LocalDate.now().minusDays(10),
                        LocalDate.now().plusDays(10)),
                new Loan(new User("Jane Smith", "jane.smith@example.com", "+33123456789", "456 Elm St"),
                        new Book("To Kill a Mockingbird", "Harper Lee", "Penguin Books", "Fiction", 1960, 8),
                        LocalDate.now().minusDays(15),
                        LocalDate.now().plusDays(5))
        );

        loansTable.setItems(loanData);
    }

    /**
     * Initializes the search field to filter the table data.
     */
    private void initializeSearchField() {
        // Create a FilteredList based on the loanData ObservableList
        FilteredList<Loan> filteredData = new FilteredList<>(loanData, b -> true);

        // Add a listener to the search field to filter the data
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(loan -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return loan.getUser().getName().toLowerCase().contains(lowerCaseFilter)
                        || loan.getBook().getTitle().toLowerCase().contains(lowerCaseFilter)
                        || loan.getStatus().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // Create a SortedList based on the FilteredList
        SortedList<Loan> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(loansTable.comparatorProperty());
        loansTable.setItems(sortedData);
    }

    /**
     * Initializes the actions column with buttons for each row.
     */
    private void initializeActionsColumn() {
        actionColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Loan, Void> call(final TableColumn<Loan, Void> param) {
                final TableCell<Loan, Void> cell = new TableCell<>() {
                    private final Button returnButton = new Button();
                    private final Button extendButton = new Button();

                    {
                        // Add icons to the buttons
                        returnButton.setGraphic(new FontIcon("mdmz-reply"));
                        extendButton.setGraphic(new FontIcon("mdmz-update"));

                        // Add CSS style classes to buttons
                        returnButton.getStyleClass().add("button-info");
                        extendButton.getStyleClass().add("button-warning");

                        // Configure button actions
                        returnButton.setOnAction(event -> {
                            Loan loan = getTableView().getItems().get(getIndex());
                            returnLoan(loan);
                        });

                        extendButton.setOnAction(event -> {
                            Loan loan = getTableView().getItems().get(getIndex());
                            extendLoan(loan);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(returnButton, extendButton);
                            buttons.setSpacing(10); // Space between buttons
                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        });
    }

    /**
     * Marks the loan as returned and updates the status label.
     *
     * @param loan The loan to return.
     */
    private void returnLoan(Loan loan) {
        loan.setStatus("Retourné");
        updateStatusLabel("Le livre \"" + loan.getBook().getTitle() + "\" a été retourné avec succès.", "alert-success");
        loansTable.refresh();
    }

    /**
     * Extends the end date of the loan and updates the status label.
     *
     * @param loan The loan to extend.
     */
    private void extendLoan(Loan loan) {
        loan.setEndDate(loan.getEndDate().plusDays(7)); // Extend by 7 days
        updateStatusLabel("Le prêt pour \"" + loan.getBook().getTitle() + "\" a été prolongé.", "alert-success");
        loansTable.refresh();
    }

    /**
     * Updates the status label with a message and a CSS class, and clears it after a delay.
     *
     * @param message The message to display.
     * @param cssClass The CSS class to apply.
     */
    private void updateStatusLabel(String message, String cssClass) {
        if (statusLabel != null) {
            statusLabel.setText(message);
            statusLabel.getStyleClass().setAll("alert", cssClass);
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    ae -> {
                        statusLabel.setText("");
                        statusLabel.getStyleClass().clear();
                    }
            ));
            timeline.play();
        }
    }

    /**
     * Handles the action for adding a new loan.
     *
     * @param actionEvent The action event.
     */
    public void handleAddLoanAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/layout/edit_loan.fxml"));
            DialogPane dialogPane = loader.load();

            EditLoanController controller = loader.getController();
            controller.setLoan(new Loan()); // Initialize with a new loan

            Dialog<Loan> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Ajouter un prêt");
            dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            Button saveButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
                if (!controller.validateInputs()) {
                    event.consume();
                }
            });

            dialog.setResultConverter(dialogButton -> dialogButton == ButtonType.OK ? controller.getLoan() : null);

            Optional<Loan> result = dialog.showAndWait();
            result.ifPresent(newLoan -> {
                loanData.add(newLoan);
                updateStatusLabel("Le prêt pour \"" + newLoan.getBook().getTitle() + "\" a été ajouté avec succès.", "alert-success");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog for editing an existing loan.
     *
     * @param loan The loan to edit.
     */
    private void editLoan(Loan loan) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/layout/edit_loan.fxml"));
            DialogPane dialogPane = loader.load();

            EditLoanController controller = loader.getController();
            controller.setLoan(loan); // Pass the current loan for editing

            Dialog<Loan> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Modifier le prêt");
            dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            Button saveButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
                if (!controller.validateInputs()) {
                    event.consume();
                }
            });

            dialog.setResultConverter(dialogButton -> dialogButton == ButtonType.OK ? controller.getLoan() : null);

            Optional<Loan> result = dialog.showAndWait();
            result.ifPresent(updatedLoan -> {
                int index = loansTable.getItems().indexOf(loan);
                if (index >= 0) {
                    loanData.set(index, updatedLoan);
                    updateStatusLabel("Le prêt pour \"" + updatedLoan.getBook().getTitle() + "\" a été mis à jour avec succès.", "alert-success");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
