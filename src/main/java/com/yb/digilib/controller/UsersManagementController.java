package com.yb.digilib.controller;

import com.yb.digilib.model.User;
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

/**
 * Controller for managing users in the application.
 * Handles interactions with the user list and manages user addition, editing, and deletion.
 */
public class UsersManagementController {

    @FXML
    private Button addUserBtn;
    @FXML
    private TableView<User> membersTable;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> phoneColumn;
    @FXML
    private TableColumn<User, String> addressColumn;
    @FXML
    private TableColumn<User, Void> actionColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label statusLabel;

    private ObservableList<User> memberData;

    /**
     * Initializes the controller by setting up columns, user data, search functionality, and action buttons.
     */
    @FXML
    public void initialize() {
        initializeColumns();
        initializeMemberData();
        initializeSearchField();
        initializeActionsColumn();
    }

    /**
     * Configures the columns of the TableView.
     * Sets up columns to use observable properties and adjusts their widths.
     */
    private void initializeColumns() {
        // Disable column reordering
        idColumn.setReorderable(false);
        nameColumn.setReorderable(false);
        emailColumn.setReorderable(false);
        phoneColumn.setReorderable(false);
        addressColumn.setReorderable(false);
        actionColumn.setReorderable(false);

        // Configure columns to use observable properties
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());

        // Set column widths as percentages of the TableView width
        idColumn.prefWidthProperty().bind(membersTable.widthProperty().multiply(0.05));
        nameColumn.prefWidthProperty().bind(membersTable.widthProperty().multiply(0.15));
        emailColumn.prefWidthProperty().bind(membersTable.widthProperty().multiply(0.25));
        phoneColumn.prefWidthProperty().bind(membersTable.widthProperty().multiply(0.15));
        addressColumn.prefWidthProperty().bind(membersTable.widthProperty().multiply(0.30));
        actionColumn.prefWidthProperty().bind(membersTable.widthProperty().multiply(0.10));
    }

    /**
     * Initializes the user data for the TableView with some example users.
     */
    private void initializeMemberData() {
        memberData = FXCollections.observableArrayList(
                new User("John Doe", "john.doe@example.com", "123-456-7890", "123 Main St"),
                new User("Jane Smith", "jane.smith@example.com", "098-765-4321", "456 Elm St"),
                new User("Sam Brown", "sam.brown@example.com", "555-555-5555", "789 Oak St"),
                new User("Emily Davis", "emily.davis@example.com", "444-444-4444", "321 Pine St"),
                new User("Michael Johnson", "michael.johnson@example.com", "333-333-3333", "654 Maple St")
        );

        membersTable.setItems(memberData);
    }

    /**
     * Sets up the search functionality for filtering users in the TableView.
     */
    private void initializeSearchField() {
        FilteredList<User> filteredData = new FilteredList<>(memberData, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return user.getName().toLowerCase().contains(lowerCaseFilter)
                        || user.getEmail().toLowerCase().contains(lowerCaseFilter)
                        || user.getPhone().toLowerCase().contains(lowerCaseFilter)
                        || user.getAddress().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(membersTable.comparatorProperty());
        membersTable.setItems(sortedData);
    }

    /**
     * Configures the action buttons (edit and delete) in the TableView.
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

                editButton.setOnAction(event -> editUser(getTableView().getItems().get(getIndex())));
                deleteButton.setOnAction(event -> deleteUser(getTableView().getItems().get(getIndex())));
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
     * Handles the action of adding a new user.
     */
    @FXML
    private void handleAddUserAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/layout/edit_user.fxml"));
            DialogPane dialogPane = loader.load();

            EditUserController controller = loader.getController();
            controller.setUser(new User());

            Dialog<User> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Ajouter un Membre");
            dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            Button saveButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
                if (!controller.validateInputs()) {
                    event.consume();
                }
            });

            dialog.setResultConverter(dialogButton -> dialogButton == ButtonType.OK ? controller.getUser() : null);

            Optional<User> result = dialog.showAndWait();
            result.ifPresent(newUser -> {
                memberData.add(newUser);
                updateStatusLabel("L'utilisateur \"" + newUser.getName() + "\" a été ajouté avec succès.", "alert-success");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog for editing the selected user.
     * Updates the user data if the user confirms the changes.
     *
     * @param user The user to be edited.
     */
    private void editUser(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/layout/edit_user.fxml"));
            DialogPane dialogPane = loader.load();

            EditUserController controller = loader.getController();
            controller.setUser(user);

            Dialog<User> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Modification de l'utilisateur");
            dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            Button saveButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
                if (!controller.validateInputs()) {
                    event.consume();
                }
            });

            dialog.setResultConverter(dialogButton -> dialogButton == ButtonType.OK ? controller.getUser() : null);

            Optional<User> result = dialog.showAndWait();
            result.ifPresent(updatedUser -> {
                int index = membersTable.getItems().indexOf(user);
                if (index >= 0) {
                    memberData.set(index, updatedUser);
                    updateStatusLabel("L'utilisateur' \"" + updatedUser.getName() + "\" a été modifié avec succès.", "alert-success");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a confirmation dialog to delete the selected user.
     * If confirmed, the user is removed from the list.
     *
     * @param user The user to be deleted.
     */
    private void deleteUser(User user) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");
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
            memberData.remove(user);
            updateStatusLabel("L'utilisateur \"" + user.getName() + "\" a été supprimé avec succès.", "alert-success");
        }
    }

    /**
     * Updates the status label with a message and a CSS class.
     * The message is cleared after 3 seconds.
     *
     * @param message  The message to display.
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
