package com.yb.digilib.Controllers.Layout;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class MembersManagementController {

    @FXML
    private TableView<Member> membersTable;

    @FXML
    private TableColumn<Member, Integer> idColumn;

    @FXML
    private TableColumn<Member, String> nameColumn;

    @FXML
    private TableColumn<Member, String> emailColumn;

    @FXML
    private TableColumn<Member, String> phoneColumn;

    @FXML
    private TableColumn<Member, String> addressColumn;

    private ObservableList<Member> memberData;

    @FXML
    public void initialize() {
        // Empêcher le réarrangement des colonnes par l'utilisateur
        idColumn.setReorderable(false);
        nameColumn.setReorderable(false);
        emailColumn.setReorderable(false);
        phoneColumn.setReorderable(false);
        addressColumn.setReorderable(false);

        // Initialiser les colonnes du tableau
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        // Utiliser des bindings pour définir les largeurs en pourcentages
        idColumn.prefWidthProperty().bind(membersTable.widthProperty().multiply(0.1));    // 10% de la largeur totale
        nameColumn.prefWidthProperty().bind(membersTable.widthProperty().multiply(0.25));  // 25% de la largeur totale
        emailColumn.prefWidthProperty().bind(membersTable.widthProperty().multiply(0.25)); // 25% de la largeur totale
        phoneColumn.prefWidthProperty().bind(membersTable.widthProperty().multiply(0.2));  // 20% de la largeur totale
        addressColumn.prefWidthProperty().bind(membersTable.widthProperty().multiply(0.2));// 20% de la largeur totale

        // Rendre les colonnes éditables
        membersTable.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Ajouter des données fictives
        memberData = FXCollections.observableArrayList(
                new Member(1, "John Doe", "john.doe@example.com", "123-456-7890", "123 Main St"),
                new Member(2, "Jane Smith", "jane.smith@example.com", "098-765-4321", "456 Elm St"),
                new Member(3, "Sam Brown", "sam.brown@example.com", "555-555-5555", "789 Oak St"),
                new Member(4, "Emily Davis", "emily.davis@example.com", "444-444-4444", "321 Pine St"),
                new Member(5, "Michael Johnson", "michael.johnson@example.com", "333-333-3333", "654 Maple St")
        );

        // Ajouter les données au tableau
        membersTable.setItems(memberData);

        // Définir les actions d'édition
        nameColumn.setOnEditCommit(event -> event.getRowValue().setName(event.getNewValue()));
        emailColumn.setOnEditCommit(event -> event.getRowValue().setEmail(event.getNewValue()));
        phoneColumn.setOnEditCommit(event -> event.getRowValue().setPhone(event.getNewValue()));
        addressColumn.setOnEditCommit(event -> event.getRowValue().setAddress(event.getNewValue()));
    }

    // Classe interne représentant un membre
    public static class Member {
        private Integer id;
        private String name;
        private String email;
        private String phone;
        private String address;

        public Member(Integer id, String name, String email, String phone, String address) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.address = address;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
