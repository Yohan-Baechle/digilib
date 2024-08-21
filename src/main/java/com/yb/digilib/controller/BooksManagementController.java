package com.yb.digilib.controller;

import com.yb.digilib.model.Book;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2OutlinedAL;

import java.io.IOException;
import java.util.Optional;

public class BooksManagementController {

    @FXML
    private Button addBookBtn;
    @FXML
    private TableView<Book> booksTable;
    @FXML
    private TableColumn<Book, Integer> idColumn;
    @FXML
    private TableColumn<Book, String> isbnColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> genreColumn;
    @FXML
    private TableColumn<Book, Integer> yearColumn;
    @FXML
    private TableColumn<Book, Integer> quantityColumn;
    @FXML
    private TableColumn<Book, Void> actionColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label statusLabel;

    private ObservableList<Book> bookData;

    /**
     * Initializes the controller by setting up the table columns, data, search functionality, and actions column.
     */
    @FXML
    public void initialize() {
        initializeColumns();
        initializeBookData();
        initializeSearchField();
        initializeActionsColumn();
    }

    /**
     * Configures the table columns, including setting fixed widths and binding column properties to book properties.
     */
    private void initializeColumns() {
        // Disable column reordering
        idColumn.setReorderable(false);
        isbnColumn.setReorderable(false);
        titleColumn.setReorderable(false);
        authorColumn.setReorderable(false);
        genreColumn.setReorderable(false);
        yearColumn.setReorderable(false);
        quantityColumn.setReorderable(false);
        actionColumn.setReorderable(false);

        // Bind column properties to book properties
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityAvailableProperty().asObject());

        // Set column widths as a percentage of the table width
        idColumn.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.05));
        isbnColumn.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.20));
        titleColumn.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.25));
        authorColumn.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.15));
        genreColumn.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.10));
        yearColumn.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.10));
        quantityColumn.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.05));
        actionColumn.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.10));
    }

    /**
     * Initializes the book data with sample books and sets it to the table.
     */
    private void initializeBookData() {
        // Sample book data
        bookData = FXCollections.observableArrayList(
                new Book("To Kill a Mockingbird", "Harper Lee", "Penguin Books", "Fiction", 1960, 10),
                new Book("1984", "George Orwell", "Penguin Books", "Dystopian", 1949, 8),
                new Book("The Great Gatsby", "F. Scott Fitzgerald", "Scribner", "Classic", 1925, 5),
                new Book("Moby Dick", "Herman Melville", "Harper & Brothers", "Adventure", 1851, 3),
                new Book("War and Peace", "Leo Tolstoy", "The Russian Messenger", "Historical", 1869, 7)
        );

        booksTable.setItems(bookData);
    }

    /**
     * Sets up the search functionality for filtering the book list based on the search field input.
     */
    private void initializeSearchField() {
        FilteredList<Book> filteredData = new FilteredList<>(bookData, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return book.getTitle().toLowerCase().contains(lowerCaseFilter)
                        || book.getAuthor().toLowerCase().contains(lowerCaseFilter)
                        || book.getGenre().toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(book.getYear()).contains(lowerCaseFilter);
            });
        });

        SortedList<Book> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(booksTable.comparatorProperty());
        booksTable.setItems(sortedData);
    }

    /**
     * Configures the actions column with edit and delete buttons for each book entry.
     */
    private void initializeActionsColumn() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button();
            private final Button deleteButton = new Button();

            {
                editButton.setGraphic(new FontIcon("mdral-create"));
                deleteButton.setGraphic(new FontIcon("mdoal-delete"));

                editButton.getStyleClass().add("button-warning");
                deleteButton.getStyleClass().add("button-destructive");

                editButton.setOnAction(event -> editBook(getTableView().getItems().get(getIndex())));
                deleteButton.setOnAction(event -> deleteBook(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(5);
                    hBox.getChildren().addAll(editButton, deleteButton);
                    setGraphic(hBox);
                }
            }
        });
    }

    /**
     * Handles the action of adding a new book, including displaying a dialog and validating inputs.
     */
    @FXML
    private void handleAddBookAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/layout/edit_book.fxml"));
            DialogPane dialogPane = loader.load();

            EditBookController controller = loader.getController();
            controller.setBook(new Book());

            Dialog<Book> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Ajouter un Livre");
            dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            Button saveButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
                if (!controller.validateInputs()) {
                    event.consume();
                }
            });

            dialog.setResultConverter(dialogButton -> dialogButton == ButtonType.OK ? controller.getBook() : null);

            Optional<Book> result = dialog.showAndWait();
            result.ifPresent(newBook -> {
                bookData.add(newBook);
                updateStatusLabel("Le livre \"" + newBook.getTitle() + "\" a été ajouté avec succès.", "alert-success");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of editing an existing book, including displaying a dialog and updating the book data.
     *
     * @param book The book to be edited.
     */
    private void editBook(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/layout/edit_book.fxml"));
            DialogPane dialogPane = loader.load();

            EditBookController controller = loader.getController();
            controller.setBook(book);

            Dialog<Book> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Modification du livre");
            dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            Button saveButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
                if (!controller.validateInputs()) {
                    event.consume();
                }
            });

            dialog.setResultConverter(dialogButton -> dialogButton == ButtonType.OK ? controller.getBook() : null);

            Optional<Book> result = dialog.showAndWait();
            result.ifPresent(updatedBook -> {
                int index = booksTable.getItems().indexOf(book);
                if (index >= 0) {
                    bookData.set(index, updatedBook);
                    updateStatusLabel("Le livre \"" + updatedBook.getTitle() + "\" a été modifié avec succès.", "alert-success");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of deleting a book, including displaying a confirmation dialog and removing the book from the list.
     *
     * @param book The book to be deleted.
     */
    private void deleteBook(Book book) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce livre ?");
        alert.setContentText("Cette action est irréversible.");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/global.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        FontIcon icon = new FontIcon(Material2OutlinedAL.INFO);
        icon.setIconSize(48);
        icon.setIconColor(Color.web("#2E1274"));
        alert.setGraphic(icon);

        ButtonType yesButton = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, noButton);

        Button yesBtn = (Button) dialogPane.lookupButton(yesButton);
        yesBtn.setStyle("-fx-background-color: #F44336;");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            bookData.remove(book);
            updateStatusLabel("Le livre \"" + book.getTitle() + "\" a été supprimé avec succès.", "alert-success");
        }
    }

    /**
     * Updates the status label with a message and a CSS class, then clears it after a delay.
     *
     * @param message The message to display.
     * @param cssClass The CSS class to apply for styling.
     */
    private void updateStatusLabel(String message, String cssClass) {
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
