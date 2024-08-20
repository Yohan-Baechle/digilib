package com.yb.digilib.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class UsersManagementController {

    public Button addMemberButton;
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
        // Prevent user from rearranging the columns
        idColumn.setReorderable(false);
        nameColumn.setReorderable(false);
        emailColumn.setReorderable(false);
        phoneColumn.setReorderable(false);
        addressColumn.setReorderable(false);

        // Initialize the table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        // Use bindings to set column widths as percentages
        idColumn.prefWidthProperty().bind(membersTable.widthProperty().multiply(0.1));    // 10% of total width
        nameColumn.prefWidthProperty().bind(membersTable.widthProperty().multiply(0.25));  // 25% of total width
        emailColumn.prefWidthProperty().bind(membersTable.widthProperty().multiply(0.25)); // 25% of total width
        phoneColumn.prefWidthProperty().bind(membersTable.widthProperty().multiply(0.2));  // 20% of total width
        addressColumn.prefWidthProperty().bind(membersTable.widthProperty().multiply(0.2));// 20% of total width

        // Make the columns editable
        membersTable.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Add sample data
        memberData = FXCollections.observableArrayList(
                new Member(1, "John Doe", "john.doe@example.com", "123-456-7890", "123 Main St"),
                new Member(2, "Jane Smith", "jane.smith@example.com", "098-765-4321", "456 Elm St"),
                new Member(3, "Sam Brown", "sam.brown@example.com", "555-555-5555", "789 Oak St"),
                new Member(4, "Emily Davis", "emily.davis@example.com", "444-444-4444", "321 Pine St"),
                new Member(5, "Michael Johnson", "michael.johnson@example.com", "333-333-3333", "654 Maple St")
        );

        // Add the data to the table
        membersTable.setItems(memberData);

        // Set up editing actions
        nameColumn.setOnEditCommit(event -> event.getRowValue().setName(event.getNewValue()));
        emailColumn.setOnEditCommit(event -> event.getRowValue().setEmail(event.getNewValue()));
        phoneColumn.setOnEditCommit(event -> event.getRowValue().setPhone(event.getNewValue()));
        addressColumn.setOnEditCommit(event -> event.getRowValue().setAddress(event.getNewValue()));
    }

    // Inner class representing a member
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
