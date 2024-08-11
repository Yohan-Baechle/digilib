package com.yb.digilib.Controllers.Layout;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class BooksManagementController {

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, Integer> idColumn;

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

    private ObservableList<Book> bookData;

    @FXML
    public void initialize() {
        // Empêcher le réarrangement des colonnes par l'utilisateur
        idColumn.setReorderable(false);
        titleColumn.setReorderable(false);
        authorColumn.setReorderable(false);
        genreColumn.setReorderable(false);
        yearColumn.setReorderable(false);
        quantityColumn.setReorderable(false);

        // Initialiser les colonnes du tableau
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));


        booksTable.setColumnResizePolicy((param) -> {
            // Stratégie simple: répartir l'espace disponible entre toutes les colonnes
            double tableWidth = booksTable.getWidth();
            for (TableColumn<Book, ?> column : booksTable.getColumns()) {
                column.setPrefWidth(tableWidth / booksTable.getColumns().size());
            }
            return true;
        });


        // Rendre les colonnes éditables
        booksTable.setEditable(true);
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        genreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        yearColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        // Ajouter des données fictives
        bookData = FXCollections.observableArrayList(
                new Book(1, "To Kill a Mockingbird", "Harper Lee", "Fiction", 1960, 10),
                new Book(2, "1984", "George Orwell", "Dystopian", 1949, 8),
                new Book(3, "The Great Gatsby", "F. Scott Fitzgerald", "Classic", 1925, 5),
                new Book(4, "Moby Dick", "Herman Melville", "Adventure", 1851, 3),
                new Book(5, "War and Peace", "Leo Tolstoy", "Historical", 1869, 7)
        );

        // Ajouter les données au tableau
        booksTable.setItems(bookData);

        // Définir les actions d'édition
        titleColumn.setOnEditCommit(event -> event.getRowValue().setTitle(event.getNewValue()));
        authorColumn.setOnEditCommit(event -> event.getRowValue().setAuthor(event.getNewValue()));
        genreColumn.setOnEditCommit(event -> event.getRowValue().setGenre(event.getNewValue()));
        yearColumn.setOnEditCommit(event -> event.getRowValue().setYear(event.getNewValue()));
        quantityColumn.setOnEditCommit(event -> event.getRowValue().setQuantity(event.getNewValue()));
    }

    // Classe interne représentant un livre
    public static class Book {
        private Integer id;
        private String title;
        private String author;
        private String genre;
        private Integer year;
        private Integer quantity;

        public Book(Integer id, String title, String author, String genre, Integer year, Integer quantity) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.genre = genre;
            this.year = year;
            this.quantity = quantity;
        }

        public Integer getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}
