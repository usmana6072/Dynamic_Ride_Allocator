package com.example.dynamic_ride_allocator.Controllers.RiderControllers;

import com.example.dynamic_ride_allocator.Controllers.LoginController;
import com.example.dynamic_ride_allocator.DataLayer.UsersData;
import com.example.dynamic_ride_allocator.Models.Driver;
import com.example.dynamic_ride_allocator.Models.Rider;
import com.example.dynamic_ride_allocator.Models.Trip;
import javafx.event.ActionEvent;
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

public class CurrentRideController {

    @FXML private Label currentRiderAvatar;
    @FXML private Label currentRiderName;
    @FXML private Label currentRiderVehicle;
    @FXML private Label currentRideStatusBadge;
    @FXML private Label currentPickupLabel;
    @FXML private Label currentDestLabel;
    @FXML private Label currentETALabel;
    @FXML private Button btnEndRide;

    Rider currentRider=(Rider) LoginController.currentUser;
    Trip trip=currentRider.getCurrentRide();
    @FXML
    public void initialize(){
        if(trip==null){
            showEmptyState();
            return;
        }
        if (trip.getStatus().equalsIgnoreCase("Rejected")) {
            currentRider.setCurrentRide(null);
            showRejectedState();
            return;
        }
        Driver driver= UsersData.driverData.get(trip.getDriverEmail());
        loadCurrentRide(trip,driver);
    }
    public void loadCurrentRide(Trip trip, Driver driver) {
        currentRiderAvatar.setText(String.valueOf(driver.getName().charAt(0)).toUpperCase());
        currentRiderName.setText(driver.getName());
        currentRiderVehicle.setText(driver.getVehicleType() + " • " + driver.getVehicleNumber());
        currentPickupLabel.setText("📍  " + trip.getPickupLocation());
        currentDestLabel.setText("🏁  " + trip.getDropOffLocation());

        // ETA: estimate 2 min per km, rounded
        int eta = (int) Math.round(trip.getDistance() * 2);
        currentETALabel.setText("~" + eta + " min");
    }

    @FXML
    private void endRideAction() {
        currentRideStatusBadge.setText("Completed");
        currentRideStatusBadge.setStyle(
                "-fx-background-color: #E6F7EF; -fx-text-fill: #1A7A4A;" +
                        "-fx-background-radius: 20; -fx-font-size: 11px;" +
                        "-fx-font-weight: bold; -fx-padding: 3 9 3 9;"
        );
        btnEndRide.setDisable(true);
        currentRider.setCurrentRide(null);
        Driver driver = UsersData.driverData.get(trip.getDriverEmail());

        if(driver != null){
            driver.setCurrentRide(null);
            driver.setAvailable(true);
            driver.getRideRequests().remove(trip);
        }
        // navigate back to home screen
    }

    // this is to show empty state if there is no ride
    public void showEmptyState() {

        currentRiderAvatar.setText("?");
        currentRiderName.setText("No Active Ride");
        currentRiderVehicle.setText("—");
        currentPickupLabel.setText("—");
        currentDestLabel.setText("—");
        currentETALabel.setText("~-- min");

        currentRideStatusBadge.setText("  No Ride  ");
        currentRideStatusBadge.setStyle(
                "-fx-background-color: #F3F3F3; -fx-text-fill: #888888;" +
                        "-fx-background-radius: 20; -fx-font-size: 11px;" +
                        "-fx-font-weight: bold; -fx-padding: 3 9 3 9;"
        );

        btnEndRide.setDisable(true);
    }

    private void showRejectedState() {

        currentRiderAvatar.setText("!");
        currentRiderName.setText("Ride Rejected");
        currentRiderVehicle.setText("Driver rejected your request");
        currentPickupLabel.setText("Please book another ride");
        currentDestLabel.setText("—");
        currentETALabel.setText("--");

        currentRideStatusBadge.setText("  Rejected  ");
        currentRideStatusBadge.setStyle(
                "-fx-background-color: #FFF0F0;" +
                        "-fx-text-fill: #D94040;" +
                        "-fx-background-radius: 20;" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 3 9 3 9;"
        );

        btnEndRide.setDisable(true);
    }

    public void btnHomeAction(MouseEvent mouseEvent) throws IOException {
        Stage s=(Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/rider_dashboard.fxml")));
        s.setTitle("Rider Dashboard");
        s.setScene(new Scene(root));
    }

    public void btnBookRideAction(MouseEvent mouseEvent) throws IOException {
        Stage s=(Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/book_ride_screen.fxml")));
        s.setTitle("Rider Dashboard/Book Ride");
        s.setScene(new Scene(root));
    }

    public void btnRideHistoryAction(MouseEvent mouseEvent) throws IOException {
        Stage s=(Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/ride_history_screen.fxml")));
        s.setTitle("Rider Dashboard/Ride History");
        s.setScene(new Scene(root));
    }

    public void btnCurrentRideAction(MouseEvent mouseEvent) throws IOException {
        Stage s=(Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/current_ride_controller.fxml")));
        s.setTitle("Rider Dashboard/Current Ride");
        s.setScene(new Scene(root));
    }

    public void btnProfileAction(MouseEvent mouseEvent) throws IOException {
        Stage s=(Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/rider_profile_screen.fxml")));
        s.setTitle("Rider Dashboard/Rider Profile");
        s.setScene(new Scene(root));
    }

    public void btnLogoutAction(MouseEvent mouseEvent) throws IOException {
        Stage s=(Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/rider_dashboard.fxml")));
        s.setTitle("Welcome To Dynamic Ride Allocator");
        s.setScene(new Scene(root));

    }

}
