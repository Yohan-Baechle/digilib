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
import javafx.util.Callback;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.Optional;

public class BooksManagementController {

    public Button addBookBtn;
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

    @FXML
    public void initialize() {
        initializeColumns();
        initializeBookData();
        initializeSearchField();
        initializeActionsColumn();
    }

    private void initializeColumns() {
        // Désactiver le réarrangement des colonnes
        idColumn.setReorderable(false);
        isbnColumn.setReorderable(false);
        titleColumn.setReorderable(false);
        authorColumn.setReorderable(false);
        genreColumn.setReorderable(false);
        yearColumn.setReorderable(false);
        quantityColumn.setReorderable(false);
        actionColumn.setReorderable(false);

        // Configurer les colonnes du tableau pour utiliser les propriétés observables
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityAvailableProperty().asObject());

        // Définir la largeur des colonnes en pourcentage de la largeur totale du tableau
        idColumn.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.05));
        isbnColumn.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.20));
        titleColumn.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.25));
        authorColumn.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.15));
        genreColumn.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.10));
        yearColumn.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.10));
        quantityColumn.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.05));
        actionColumn.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.10));
    }

    private void initializeBookData() {
        // Initialiser les données des livres
        bookData = FXCollections.observableArrayList(
                new Book("To Kill a Mockingbird", "Harper Lee", "Penguin Books", "Fiction", 1960, 10),
                new Book("1984", "George Orwell", "Penguin Books", "Dystopian", 1949, 8),
                new Book("The Great Gatsby", "F. Scott Fitzgerald", "Scribner", "Classic", 1925, 5),
                new Book("Moby Dick", "Herman Melville", "Harper & Brothers", "Adventure", 1851, 3),
                new Book("War and Peace", "Leo Tolstoy", "The Russian Messenger", "Historical", 1869, 7)
        );

        booksTable.setItems(bookData);
    }

    private void initializeSearchField() {
        // Créer une FilteredList en fonction de la bookData ObservableList
        FilteredList<Book> filteredData = new FilteredList<>(bookData, b -> true);

        // Ajouter un listener au champ de recherche pour filtrer les données
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                // Si le champ de recherche est vide, afficher tous les livres
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Comparer le texte du champ de recherche avec les propriétés des livres
                String lowerCaseFilter = newValue.toLowerCase();
                return book.getTitle().toLowerCase().contains(lowerCaseFilter)
                        || book.getAuthor().toLowerCase().contains(lowerCaseFilter)
                        || book.getGenre().toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(book.getYear()).contains(lowerCaseFilter);
            });
        });

        // Créer une SortedList basée sur la FilteredList
        SortedList<Book> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(booksTable.comparatorProperty());
        booksTable.setItems(sortedData);
    }

    private void initializeActionsColumn() {
        actionColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
                final TableCell<Book, Void> cell = new TableCell<>() {
                    private final Button editButton = new Button();
                    private final Button deleteButton = new Button();

                    {
                        editButton.setGraphic(new FontIcon("mdral-create"));
                        deleteButton.setGraphic(new FontIcon("mdoal-delete"));

                        editButton.getStyleClass().add("button-warning");
                        deleteButton.getStyleClass().add("button-destructive");

                        editButton.setOnAction(event -> {
                            Book book = getTableView().getItems().get(getIndex());
                            editBook(book);
                        });

                        deleteButton.setOnAction(event -> {
                            Book book = getTableView().getItems().get(getIndex());
                            deleteBook(book);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : new HBox(editButton, deleteButton));
                    }
                };
                return cell;
            }
        });
    }

    @FXML
    private void handleAddBookAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/layout/edit_book.fxml"));
            DialogPane dialogPane = loader.load();

            EditBookController controller = loader.getController();
            controller.setBook(new Book());  // Passer un nouvel objet Book pour l'ajout

            Dialog<Book> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Ajouter un Livre");

            // Configurez les types de boutons du dialogue
            dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            Button saveButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            if (saveButton == null) {
                System.out.println("Save button not found in addBook dialog.");
            } else {
                saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
                    System.out.println("Save button clicked in addBook dialog.");
                    if (!controller.validateInputs()) {
                        event.consume();
                        System.out.println("Validation failed in addBook dialog.");
                    }
                });
            }

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    return controller.getBook();
                }
                return null;
            });

            Optional<Book> result = dialog.showAndWait();
            result.ifPresent(newBook -> {
                // Ajouter le nouveau livre à la liste
                bookData.add(newBook);
                statusLabel.setText("Le livre \"" + newBook.getTitle() + "\" a été ajouté avec succès.");
                statusLabel.getStyleClass().setAll("alert", "alert-success");
                booksTable.refresh();  // Rafraîchir le tableau pour afficher les nouvelles données

                // Utiliser un Timeline pour effacer le message après 3 secondes
                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.seconds(3),
                        ae -> {
                            statusLabel.setText("");
                            statusLabel.getStyleClass().clear();
                        }
                ));
                timeline.play();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editBook(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/layout/edit_book.fxml"));
            DialogPane dialogPane = loader.load();

            EditBookController controller = loader.getController();
            controller.setBook(book);

            Dialog<Book> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Modification du livre");

            // Configurez les types de boutons du dialogue
            dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            Button saveButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            if (saveButton == null) {
                System.out.println("Save button not found in editBook dialog.");
            } else {
                saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
                    System.out.println("Save button clicked in editBook dialog.");
                    if (!controller.validateInputs()) {
                        event.consume();
                        System.out.println("Validation failed in editBook dialog.");
                    }
                });
            }

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    return controller.getBook();
                }
                return null;
            });

            Optional<Book> result = dialog.showAndWait();
            result.ifPresent(updatedBook -> {
                // Mettre à jour les informations du livre dans la liste
                int index = booksTable.getItems().indexOf(book);
                if (index >= 0) {
                    bookData.set(index, updatedBook);
                    statusLabel.setText("Le livre \"" + updatedBook.getTitle() + "\" a été modifié avec succès.");
                    statusLabel.getStyleClass().setAll("alert", "alert-success");
                    booksTable.refresh();  // Rafraîchir le tableau pour afficher les données mises à jour

                    // Utiliser un Timeline pour effacer le message après 3 secondes
                    Timeline timeline = new Timeline(new KeyFrame(
                            Duration.seconds(3),
                            ae -> {
                                statusLabel.setText("");
                                statusLabel.getStyleClass().clear();
                            }
                    ));
                    timeline.play();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteBook(Book book) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce livre ?");
        alert.setContentText("Cette action est irréversible.");

        ButtonType yesButton = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            bookData.remove(book);
            statusLabel.setText("Le livre \"" + book.getTitle() + "\" a été supprimé avec succès.");
            statusLabel.getStyleClass().setAll("alert", "alert-success");
            booksTable.refresh();  // Rafraîchir le tableau pour retirer le livre supprimé

            // Utiliser un Timeline pour effacer le message après 3 secondes
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
}
