package com.example.dynamic_ride_allocator.Controllers.DriverControllers;

import com.example.dynamic_ride_allocator.Controllers.LoginController;
import com.example.dynamic_ride_allocator.DataLayer.UsersData;
import com.example.dynamic_ride_allocator.Models.Driver;
import com.example.dynamic_ride_allocator.Models.Trip;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class DriverDashboardController {

    @FXML
    private Label tvTotalRides;
    @FXML
    private Label tvTotalEarnings;
    @FXML
    private Label tvStatus;
    @FXML
    private Label tvStatusSmall;
    @FXML
    private VBox earningsContainer;

    @FXML
    public void initialize(){
        Driver driver=(Driver) LoginController.currentUser;
        tvTotalRides.setText(driver.getTotalTrips()+"");
        tvTotalEarnings.setText(driver.getTotalEarnings()+"");
        if(driver.isAvailable()) {
            tvStatus.setText("Available");
            tvStatusSmall.setText("Online");
        }
        else {
            tvStatus.setText("Offline");
            tvStatusSmall.setText("Offline");
        }

        if(UsersData.driverHistory.containsKey(driver.getEmail())){
            ArrayList<Trip> trips=UsersData.driverHistory.get(driver.getEmail());
            Collections.sort(trips,Collections.reverseOrder());
            for(Trip p:trips){
                earningsContainer.getChildren().add(buildRow(p));
            }
        }else
            showEmptyState();

    }

    private HBox buildRow(Trip entry) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPrefHeight(38.0);

        Label rideLabel = new Label("ID# : "+entry.getTripID()+"            ");
        rideLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12px;");
        HBox.setHgrow(rideLabel, Priority.ALWAYS);

        Label amountLabel = new Label(String.format("+ Rs %.2f", entry.getFare()));
        amountLabel.setStyle(
                "-fx-text-fill: #1A7A4A;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;"
        );

        row.getChildren().addAll(rideLabel, amountLabel);
        return row;
    }

    // ── Empty State ───────────────────────────────────────────────
    private void showEmptyState() {
        VBox empty = new VBox();
        empty.setAlignment(Pos.CENTER);
        empty.setSpacing(6);
        empty.setStyle("-fx-padding: 20;");

        Label icon = new Label("💰");
        icon.setStyle("-fx-font-size: 28px;");

        Label msg = new Label("No earnings yet today");
        msg.setStyle("-fx-text-fill: #999999; -fx-font-size: 12px;");

        empty.getChildren().addAll(icon, msg);
        earningsContainer.getChildren().add(empty);
    }



    // this adds action listeners on the side menu
    @FXML
    public void btnHomeAction(MouseEvent event) throws IOException {
        Stage s=(Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/driver_dashboard.fxml")));
        s.setTitle("Driver Dashboard Home Page");
        s.setScene(new Scene(root));
        s.show();
    }

    @FXML
    public void btnRequestsAction(MouseEvent event) throws IOException {
        Stage s=(Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/driver_ride_requests_screen.fxml")));
        s.setTitle("Driver Dashboard Ride Requests");
        s.setScene(new Scene(root));
        s.show();
    }

    @FXML
    public void btnAvailabilityAction(MouseEvent event) throws IOException {
        Stage s=(Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/driver_abailability_screen.fxml")));
        s.setTitle("Driver Dashboard Availability Page");
        s.setScene(new Scene(root));
        s.show();
    }

    @FXML
    public void btnCurrentRideAction(MouseEvent event) throws IOException {
        Stage s=(Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/driver_current_ride_screen.fxml")));
        s.setTitle("Driver Dashboard Current Ride Page");
        s.setScene(new Scene(root));
        s.show();
    }

    @FXML
    public void btnProfileAction(MouseEvent event) throws IOException {
        Stage s=(Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/driver_profile_screen.fxml")));
        s.setTitle("Driver Dashboard Profile Page");
        s.setScene(new Scene(root));
        s.show();
    }

    @FXML
    public void btnLogoutAction(MouseEvent event) throws IOException {
        Stage s=(Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/welcome.fxml")));
        s.setTitle("Welcome to Dynamic Ride Allocator");
        s.setScene(new Scene(root));
        s.show();
    }


}
