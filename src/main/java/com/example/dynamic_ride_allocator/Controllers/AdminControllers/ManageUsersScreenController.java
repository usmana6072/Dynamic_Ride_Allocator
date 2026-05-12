package com.example.dynamic_ride_allocator.Controllers.AdminControllers;

import com.example.dynamic_ride_allocator.Controllers.LoginController;
import com.example.dynamic_ride_allocator.DataLayer.UsersData;
import com.example.dynamic_ride_allocator.Models.Driver;
import com.example.dynamic_ride_allocator.Models.Rider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ManageUsersScreenController {
    public Button btnBlock;
    public Label tabRiders;
    public Label tabDrivers;
    public VBox usersContainer;

    @FXML
    public void initialize() {
        // Start with Customers tab active
        loadRiders(UsersData.riderData);
    }

    // ── Tab Switching ────────────────────────────────────────────
    @FXML
    private void switchToRiders(MouseEvent event) {
        tabRiders.setStyle(
                "-fx-text-fill: #1A6DBB; -fx-font-size: 12px; -fx-font-weight: bold;" +
                        "-fx-border-color: transparent transparent #1A6DBB transparent;" +
                        "-fx-border-width: 0 0 2 0; -fx-padding: 9 18 9 18; -fx-cursor: hand;"
        );
        tabDrivers.setStyle(
                "-fx-text-fill: #888888; -fx-font-size: 12px;" +
                        "-fx-padding: 9 18 9 18; -fx-cursor: hand;"
        );
        loadRiders(UsersData.riderData);
    }

    @FXML
    private void switchToDrivers(MouseEvent event) {
        tabDrivers.setStyle(
                "-fx-text-fill: #1A6DBB; -fx-font-size: 12px; -fx-font-weight: bold;" +
                        "-fx-border-color: transparent transparent #1A6DBB transparent;" +
                        "-fx-border-width: 0 0 2 0; -fx-padding: 9 18 9 18; -fx-cursor: hand;"
        );
        tabRiders.setStyle(
                "-fx-text-fill: #888888; -fx-font-size: 12px;" +
                        "-fx-padding: 9 18 9 18; -fx-cursor: hand;"
        );
        loadDrivers(UsersData.driverData);
    }

    // ── Load Users Into Table ────────────────────────────────────
    private void loadDrivers(Map<String,Driver> users) {
        usersContainer.getChildren().clear();

        if (users.isEmpty()) {
            showEmptyState();
            return;
        }

        for(String s:users.keySet()){
            usersContainer.getChildren().add(buildDriverRow(users.get(s)));
        }
    }

    // ── Load Users Into Table ────────────────────────────────────
    private void loadRiders(Map<String, Rider> users) {
        usersContainer.getChildren().clear();

        if (users.isEmpty()) {
            showEmptyState();
            return;
        }

        for(String s:users.keySet()){
            usersContainer.getChildren().add(buildRiderRow(users.get(s)));
        }
    }

    private HBox buildDriverRow(Driver user) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(7, 10, 7, 10));

        Label nameLabel = new Label(user.getName());
        nameLabel.setPrefWidth(120);
        nameLabel.setStyle("-fx-text-fill: #1A3A5C; -fx-font-size: 12px; -fx-font-weight: bold;");

        Label emailLabel = new Label(user.getEmail());
        emailLabel.setPrefWidth(140);
        emailLabel.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px;");

        Label phoneLabel = new Label(user.getPhone());
        phoneLabel.setPrefWidth(110);
        phoneLabel.setStyle("-fx-text-fill: #1A3A5C; -fx-font-size: 11px;");

        Label statusBadge = new Label((user.isBlocked())?"Blocked":"Active");
        statusBadge.setPrefWidth(60);
        statusBadge.setStyle(
                "-fx-background-color: " + (user.isAvailable() ? "#E6F7EF" : "#FDE8E8") + ";" +
                        "-fx-text-fill: "        + (user.isAvailable() ? "#1A7A4A" : "#C02020") + ";" +
                        "-fx-background-radius: 20;" +
                        "-fx-font-size: 10px; -fx-font-weight: bold;" +
                        "-fx-padding: 2 6 2 6;"
        );

        // Action Buttons
        HBox actions = new HBox(5);
        actions.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(actions, Priority.ALWAYS);

        if (!user.isBlocked()) {
            Button blockBtn = new Button("Block");
            blockBtn.setPrefSize(52, 24);
            blockBtn.setStyle(
                    "-fx-background-color: #E6F0FF; -fx-text-fill: #1A6DBB;" +
                            "-fx-font-size: 10px; -fx-font-weight: bold;" +
                            "-fx-background-radius: 6; -fx-cursor: hand;"
            );
            blockBtn.setOnAction(e -> handleDriverBlock(user, row, statusBadge, actions));
            actions.getChildren().add(blockBtn);
        } else {
            Button unblockBtn = new Button("Unblock");
            unblockBtn.setPrefSize(62, 24);
            unblockBtn.setStyle(
                    "-fx-background-color: #E6F7EF; -fx-text-fill: #1A7A4A;" +
                            "-fx-font-size: 10px; -fx-font-weight: bold;" +
                            "-fx-background-radius: 6; -fx-cursor: hand;"
            );
            unblockBtn.setOnAction(e -> handleDriverUnblock(user, row, statusBadge, actions));
            actions.getChildren().add(unblockBtn);
        }

        Button deleteBtn = new Button("Delete");
        deleteBtn.setPrefSize(52, 24);
        deleteBtn.setStyle(
                "-fx-background-color: #FDE8E8; -fx-text-fill: #C02020;" +
                        "-fx-font-size: 10px; -fx-font-weight: bold;" +
                        "-fx-background-radius: 6; -fx-cursor: hand;"
        );
        deleteBtn.setOnAction(e -> handleDriverDelete(user, row));
        actions.getChildren().add(deleteBtn);

        row.getChildren().addAll(nameLabel, emailLabel, phoneLabel, statusBadge, actions);
        return row;
    }
    private void handleDriverBlock(Driver user, HBox row, Label statusBadge, HBox actions) {
        user.setBlocked(true);
        statusBadge.setText("Blocked");
        statusBadge.setStyle(
                "-fx-background-color: #FDE8E8; -fx-text-fill: #C02020;" +
                        "-fx-background-radius: 20; -fx-font-size: 10px;" +
                        "-fx-font-weight: bold; -fx-padding: 2 6 2 6;"
        );
        // Swap Block button to Unblock
        actions.getChildren().clear();
        Button unblockBtn = new Button("Unblock");
        unblockBtn.setPrefSize(62, 24);
        unblockBtn.setStyle(
                "-fx-background-color: #E6F7EF; -fx-text-fill: #1A7A4A;" +
                        "-fx-font-size: 10px; -fx-font-weight: bold;" +
                        "-fx-background-radius: 6; -fx-cursor: hand;"
        );
        unblockBtn.setOnAction(e -> handleDriverUnblock(user, row, statusBadge, actions));

        Button deleteBtn = new Button("Delete");
        deleteBtn.setPrefSize(52, 24);
        deleteBtn.setStyle(
                "-fx-background-color: #FDE8E8; -fx-text-fill: #C02020;" +
                        "-fx-font-size: 10px; -fx-font-weight: bold;" +
                        "-fx-background-radius: 6; -fx-cursor: hand;"
        );
        deleteBtn.setOnAction(e -> handleDriverDelete(user, row));
        actions.getChildren().addAll(unblockBtn, deleteBtn);
        new Thread(UsersData::writeDriver).start();

    }

    private void handleDriverUnblock(Driver user, HBox row, Label statusBadge, HBox actions) {
        // DatabaseHelper.unblockUser(user.id);
        user.setBlocked(false);
        statusBadge.setText("Active");
        statusBadge.setStyle(
                "-fx-background-color: #E6F7EF; -fx-text-fill: #1A7A4A;" +
                        "-fx-background-radius: 20; -fx-font-size: 10px;" +
                        "-fx-font-weight: bold; -fx-padding: 2 6 2 6;"
        );
        actions.getChildren().clear();
        Button blockBtn = new Button("Block");
        blockBtn.setPrefSize(52, 24);
        blockBtn.setStyle(
                "-fx-background-color: #E6F0FF; -fx-text-fill: #1A6DBB;" +
                        "-fx-font-size: 10px; -fx-font-weight: bold;" +
                        "-fx-background-radius: 6; -fx-cursor: hand;"
        );
        blockBtn.setOnAction(e -> handleDriverBlock(user, row, statusBadge, actions));

        Button deleteBtn = new Button("Delete");
        deleteBtn.setPrefSize(52, 24);
        deleteBtn.setStyle(
                "-fx-background-color: #FDE8E8; -fx-text-fill: #C02020;" +
                        "-fx-font-size: 10px; -fx-font-weight: bold;" +
                        "-fx-background-radius: 6; -fx-cursor: hand;"
        );
        deleteBtn.setOnAction(e -> handleDriverDelete(user, row));
        actions.getChildren().addAll(blockBtn, deleteBtn);
        new Thread(UsersData::writeDriver).start();
    }

    private void handleDriverDelete(Driver user, HBox row) {
        UsersData.driverData.remove(user.getEmail());
        VBox parent = (VBox) row.getParent();
        int index   = parent.getChildren().indexOf(row);
        parent.getChildren().remove(row);
        // Remove separator above or below if it exists
        if (index < parent.getChildren().size() &&
                parent.getChildren().get(index) instanceof Separator) {
            parent.getChildren().remove(index);
        } else if (index > 0 &&
                parent.getChildren().get(index - 1) instanceof Separator) {
            parent.getChildren().remove(index - 1);
        }
        if (parent.getChildren().isEmpty()) showEmptyState();
        new Thread(UsersData::writeDriver).start();
    }



    private HBox buildRiderRow(Rider user) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(7, 10, 7, 10));

        Label nameLabel = new Label(user.getName());
        nameLabel.setPrefWidth(120);
        nameLabel.setStyle("-fx-text-fill: #1A3A5C; -fx-font-size: 12px; -fx-font-weight: bold;");

        Label emailLabel = new Label(user.getEmail());
        emailLabel.setPrefWidth(140);
        emailLabel.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px;");

        Label phoneLabel = new Label(user.getPhone());
        phoneLabel.setPrefWidth(110);
        phoneLabel.setStyle("-fx-text-fill: #1A3A5C; -fx-font-size: 11px;");

        // Status Badge
        Label statusBadge = new Label((user.isBlocked())?"Blocked":"Active");
        statusBadge.setPrefWidth(60);
        statusBadge.setStyle(
                "-fx-background-color: " + (user.isBlocked() ? "#E6F7EF" : "#FDE8E8") + ";" +
                        "-fx-text-fill: "        + (user.isBlocked() ? "#1A7A4A" : "#C02020") + ";" +
                        "-fx-background-radius: 20;" +
                        "-fx-font-size: 10px; -fx-font-weight: bold;" +
                        "-fx-padding: 2 6 2 6;"
        );

        // Action Buttons
        HBox actions = new HBox(5);
        actions.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(actions, Priority.ALWAYS);

        if (!user.isBlocked()) {
            Button blockBtn = new Button("Block");
            blockBtn.setPrefSize(52, 24);
            blockBtn.setStyle(
                    "-fx-background-color: #E6F0FF; -fx-text-fill: #1A6DBB;" +
                            "-fx-font-size: 10px; -fx-font-weight: bold;" +
                            "-fx-background-radius: 6; -fx-cursor: hand;"
            );
            blockBtn.setOnAction(e -> handleRiderBlock(user, row, statusBadge, actions));
            actions.getChildren().add(blockBtn);
        } else {
            Button unblockBtn = new Button("Unblock");
            unblockBtn.setPrefSize(62, 24);
            unblockBtn.setStyle(
                    "-fx-background-color: #E6F7EF; -fx-text-fill: #1A7A4A;" +
                            "-fx-font-size: 10px; -fx-font-weight: bold;" +
                            "-fx-background-radius: 6; -fx-cursor: hand;"
            );
            unblockBtn.setOnAction(e -> handleRiderUnblock(user, row, statusBadge, actions));
            actions.getChildren().add(unblockBtn);
        }

        Button deleteBtn = new Button("Delete");
        deleteBtn.setPrefSize(52, 24);
        deleteBtn.setStyle(
                "-fx-background-color: #FDE8E8; -fx-text-fill: #C02020;" +
                        "-fx-font-size: 10px; -fx-font-weight: bold;" +
                        "-fx-background-radius: 6; -fx-cursor: hand;"
        );
        deleteBtn.setOnAction(e -> handleRiderDelete(user, row));
        actions.getChildren().add(deleteBtn);

        row.getChildren().addAll(nameLabel, emailLabel, phoneLabel, statusBadge, actions);
        return row;
    }


    // ── Action Handlers ──────────────────────────────────────────
    private void handleRiderBlock(Rider user, HBox row, Label statusBadge, HBox actions) {
        user.setBlocked(true);
        statusBadge.setText("Blocked");
        statusBadge.setStyle(
                "-fx-background-color: #FDE8E8; -fx-text-fill: #C02020;" +
                        "-fx-background-radius: 20; -fx-font-size: 10px;" +
                        "-fx-font-weight: bold; -fx-padding: 2 6 2 6;"
        );
        // Swap Block button to Unblock
        actions.getChildren().clear();
        Button unblockBtn = new Button("Unblock");
        unblockBtn.setPrefSize(62, 24);
        unblockBtn.setStyle(
                "-fx-background-color: #E6F7EF; -fx-text-fill: #1A7A4A;" +
                        "-fx-font-size: 10px; -fx-font-weight: bold;" +
                        "-fx-background-radius: 6; -fx-cursor: hand;"
        );
        unblockBtn.setOnAction(e -> handleRiderUnblock(user, row, statusBadge, actions));

        Button deleteBtn = new Button("Delete");
        deleteBtn.setPrefSize(52, 24);
        deleteBtn.setStyle(
                "-fx-background-color: #FDE8E8; -fx-text-fill: #C02020;" +
                        "-fx-font-size: 10px; -fx-font-weight: bold;" +
                        "-fx-background-radius: 6; -fx-cursor: hand;"
        );
        deleteBtn.setOnAction(e -> handleRiderDelete(user, row));
        actions.getChildren().addAll(unblockBtn, deleteBtn);
        new Thread(UsersData::writeRider).start();
    }

    private void handleRiderUnblock(Rider user, HBox row, Label statusBadge, HBox actions) {
        user.setBlocked(false);
        statusBadge.setText("Active");
        statusBadge.setStyle(
                "-fx-background-color: #E6F7EF; -fx-text-fill: #1A7A4A;" +
                        "-fx-background-radius: 20; -fx-font-size: 10px;" +
                        "-fx-font-weight: bold; -fx-padding: 2 6 2 6;"
        );
        actions.getChildren().clear();
        Button blockBtn = new Button("Block");
        blockBtn.setPrefSize(52, 24);
        blockBtn.setStyle(
                "-fx-background-color: #E6F0FF; -fx-text-fill: #1A6DBB;" +
                        "-fx-font-size: 10px; -fx-font-weight: bold;" +
                        "-fx-background-radius: 6; -fx-cursor: hand;"
        );
        blockBtn.setOnAction(e -> handleRiderBlock(user, row, statusBadge, actions));

        Button deleteBtn = new Button("Delete");
        deleteBtn.setPrefSize(52, 24);
        deleteBtn.setStyle(
                "-fx-background-color: #FDE8E8; -fx-text-fill: #C02020;" +
                        "-fx-font-size: 10px; -fx-font-weight: bold;" +
                        "-fx-background-radius: 6; -fx-cursor: hand;"
        );
        deleteBtn.setOnAction(e -> handleRiderDelete(user, row));
        actions.getChildren().addAll(blockBtn, deleteBtn);
        new Thread(UsersData::writeRider).start();
    }

    private void handleRiderDelete(Rider user, HBox row) {
        UsersData.riderData.remove(user.getEmail());
        VBox parent = (VBox) row.getParent();
        int index   = parent.getChildren().indexOf(row);
        parent.getChildren().remove(row);
        // Remove separator above or below if it exists
        if (index < parent.getChildren().size() &&
                parent.getChildren().get(index) instanceof Separator) {
            parent.getChildren().remove(index);
        } else if (index > 0 &&
                parent.getChildren().get(index - 1) instanceof Separator) {
            parent.getChildren().remove(index - 1);
        }
        if (parent.getChildren().isEmpty()) showEmptyState();
        new Thread(UsersData::writeRider).start();
    }

    private void showEmptyState() {
        VBox empty = new VBox();
        empty.setAlignment(Pos.CENTER);
        empty.setSpacing(8);
        empty.setPadding(new Insets(40));
        Label icon = new Label("👥");
        icon.setStyle("-fx-font-size: 30px;");
        Label msg  = new Label("No users found");
        msg.setStyle("-fx-text-fill: #999999; -fx-font-size: 12px;");
        empty.getChildren().addAll(icon, msg);
        usersContainer.getChildren().add(empty);
    }

    public void btnDashboardAction(MouseEvent mouseEvent) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/admin_dashboard.fxml")));
        Stage s=(Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        s.setTitle("Admin Dashboard");
        s.setScene(new Scene(root));
    }

    public void btnApproveRidersAction(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/approve_drivers_screen.fxml")));
        Stage s = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        s.setTitle("Admin Dashboard/Approve Drivers");
        s.setScene(new Scene(root));
    }

    public void btnManageUsersAction(MouseEvent mouseEvent) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/manage_users_screen.fxml")));
        Stage s=(Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        s.setTitle("Admin Dashboard/Manage Users");
        s.setScene(new Scene(root));
    }

    public void btnProfileAction(MouseEvent mouseEvent) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/admin_profile_screen.fxml")));
        Stage s=(Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        s.setTitle("Admin Dashboard/Admin Profile");
        s.setScene(new Scene(root));
    }

    public void btnLogoutAction(MouseEvent mouseEvent) throws IOException {
        LoginController.currentUser=null;
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/welcome.fxml")));
        Stage s=(Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        s.setTitle("Admin Dashboard");
        s.setScene(new Scene(root));
    }

}
