package com.example.dynamic_ride_allocator.Controllers.AdminControllers;

import com.example.dynamic_ride_allocator.Controllers.LoginController;
import com.example.dynamic_ride_allocator.DataLayer.UsersData;
import com.example.dynamic_ride_allocator.Models.Driver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ApproveDriverScreenController {
    public VBox ridersContainer;
    public Button btnApproveSelected;


    @FXML
    public void initialize(){
        for(String s: UsersData.driverData.keySet()){
            Driver driver=UsersData.driverData.get(s);
            if(!driver.isApproved()){
                HBox row=buildRiderRow(driver);
                ridersContainer.getChildren().add(row);
            }
        }
    }
    private HBox buildRiderRow(Driver driver) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(8, 10, 8, 10));

        Label nameLabel = new Label(driver.getName());
        nameLabel.setPrefWidth(110);
        nameLabel.setStyle("-fx-text-fill: #1A3A5C; -fx-font-size: 12px; -fx-font-weight: bold;");

        Label emailLabel = new Label(driver.getEmail());
        emailLabel.setPrefWidth(130);
        emailLabel.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px;");

        Label vehicleLabel = new Label(driver.getVehicleType());
        vehicleLabel.setPrefWidth(70);
        vehicleLabel.setStyle("-fx-text-fill: #1A3A5C; -fx-font-size: 12px;");

        Label licenseLabel = new Label(driver.getLicenceNumber());
        licenseLabel.setPrefWidth(90);
        licenseLabel.setStyle("-fx-text-fill: #1A3A5C; -fx-font-size: 12px;");
        HBox.setHgrow(licenseLabel, Priority.ALWAYS);

        // Action area — show buttons only if Pending, else show badge
        HBox actionArea = new HBox(6);
        actionArea.setAlignment(Pos.CENTER_LEFT);

        if (!driver.isApproved()) {
            Button approveBtn = new Button("Approve");
            approveBtn.setPrefSize(70, 26);
            approveBtn.setStyle(
                    "-fx-background-color: #27A065; -fx-text-fill: #FFFFFF;" +
                            "-fx-font-size: 11px; -fx-font-weight: bold;" +
                            "-fx-background-radius: 6; -fx-cursor: hand;"
            );


            approveBtn.setOnAction(e -> handleApprove(driver, row));

            actionArea.getChildren().addAll(approveBtn);

        } else {
            // Already actioned — show status badge only
            String bg = driver.isApproved() ? "#E6F7EF" : "#FDE8E8";
            String fg = driver.isApproved() ? "#1A7A4A" : "#C02020";

            Label badge = new Label((driver.isApproved())?"Approved":"Pending");
            badge.setStyle(
                    "-fx-background-color: " + bg + ";" +
                            "-fx-text-fill: "        + fg + ";" +
                            "-fx-background-radius: 20;" +
                            "-fx-font-size: 11px; -fx-font-weight: bold;" +
                            "-fx-padding: 2 8 2 8;"
            );
            actionArea.getChildren().add(badge);
        }

        row.getChildren().addAll(nameLabel, emailLabel, vehicleLabel, licenseLabel, actionArea);
        return row;
    }

    private void handleApprove(Driver driver, HBox row) {
        driver.setApproved(true);
        refreshActionCell(row, driver);
        new Thread(UsersData::writeDriver).start();
    }

    // Swap buttons with badge after action
    private void refreshActionCell(HBox row, Driver driver) {
        HBox actionArea = (HBox) row.getChildren().get(4);
        actionArea.getChildren().clear();

        String bg = driver.isApproved() ? "#E6F7EF" : "#FDE8E8";
        String fg = driver.isApproved() ? "#1A7A4A" : "#C02020";

        Label badge = new Label((driver.isApproved())?"Approved":"Pending");
        badge.setStyle(
                "-fx-background-color: " + bg + ";" +
                        "-fx-text-fill: "        + fg + ";" +
                        "-fx-background-radius: 20;" +
                        "-fx-font-size: 11px; -fx-font-weight: bold;" +
                        "-fx-padding: 2 8 2 8;"
        );
        actionArea.getChildren().add(badge);
    }
    private void showEmptyState() {
        VBox empty = new VBox();
        empty.setAlignment(Pos.CENTER);
        empty.setSpacing(8);
        empty.setPadding(new Insets(40));
        Label icon = new Label("✅");
        icon.setStyle("-fx-font-size: 30px;");
        Label msg = new Label("No pending rider applications");
        msg.setStyle("-fx-text-fill: #999999; -fx-font-size: 12px;");
        empty.getChildren().addAll(icon, msg);
        ridersContainer.getChildren().add(empty);
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
