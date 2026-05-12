package com.example.dynamic_ride_allocator.Controllers.DriverControllers;

import com.example.dynamic_ride_allocator.Controllers.LoginController;
import com.example.dynamic_ride_allocator.DataLayer.UsersData;
import com.example.dynamic_ride_allocator.Models.Driver;
import com.example.dynamic_ride_allocator.Models.Rider;
import com.example.dynamic_ride_allocator.Models.Trip;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CurrentRideScreenController {

    @FXML private Label lblAvatar;
    @FXML private Label lblCustomerName;
    @FXML private Label lblCustomerPhone;
    @FXML private Label lblStatus;
    @FXML private Label lblPickup;
    @FXML private Label lblDestination;
    @FXML private Label lblDistance;
    @FXML private Label lblFare;
    @FXML private Button btnCompleteRide;

    Driver currentDriver=(Driver) LoginController.currentUser;
    @FXML
    public void initialize(){
        Trip currentTrip=currentDriver.getCurrentRide();
        if(currentTrip==null){
            showEmptyState();
            return;
        }
        loadCurrentRide(currentTrip,currentDriver);
        btnCompleteRide.setOnAction(e->{
            completeRide();
        });
    }

    public void loadCurrentRide(Trip trip,Driver driver) {
        Rider rider= UsersData.riderData.get(trip.getRiderEmail());
        // First edit the avatar label
        lblAvatar.setText(String.valueOf(rider.getName().charAt(0)).toUpperCase());

        // add name and phone
        lblCustomerName.setText(rider.getName() + " (Customer)");
        lblCustomerPhone.setText(rider.getPhone());

        // set status
        lblStatus.setText("  In Progress  ");
        lblStatus.setStyle(
                "-fx-background-color: #E6F7EF; -fx-text-fill: #1A7A4A;" +
                        "-fx-background-radius: 20; -fx-font-size: 11px;" +
                        "-fx-font-weight: bold; -fx-padding: 3 9 3 9;"
        );

        // set pickup and destination information
        lblPickup.setText("📍  " + trip.getPickupLocation());
        lblDestination.setText("🏁  " + trip.getDropOffLocation());

        // Distance and fare
        lblDistance.setText(String.format("%.1f km", trip.getDistance()));
        lblFare.setText("Rs " + (int) trip.getFare());
    }

    private void completeRide() {
        currentDriver.setAvailable(true);
        // Call Person 3's saveCompletedRide() here
        // Then navigate back to home/requests screen
        lblStatus.setText("  Completed  ");
        currentDriver.getCurrentRide().setStatus("Completed");
        currentDriver.setCurrentRide(null);
        lblStatus.setStyle(
                "-fx-background-color: #E6F0FF; -fx-text-fill: #1A5DB5;" +
                        "-fx-background-radius: 20; -fx-font-size: 11px;" +
                        "-fx-font-weight: bold; -fx-padding: 3 9 3 9;"
        );
        btnCompleteRide.setDisable(true);
    }

    //this is to show empty state
    public void showEmptyState() {

        lblAvatar.setText("?");
        lblCustomerName.setText("No Active Ride");
        lblCustomerPhone.setText("—");
        lblPickup.setText("📍  —");
        lblDestination.setText("🏁  —");
        lblDistance.setText("— km");
        lblFare.setText("Rs —");

        lblStatus.setText("  No Ride  ");
        lblStatus.setStyle(
                "-fx-background-color: #F3F3F3; -fx-text-fill: #888888;" +
                        "-fx-background-radius: 20; -fx-font-size: 11px;" +
                        "-fx-font-weight: bold; -fx-padding: 3 9 3 9;"
        );

        btnCompleteRide.setDisable(true);
    }

    // this adds action listeners to the side menu
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
