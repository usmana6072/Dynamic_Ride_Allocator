package com.example.dynamic_ride_allocator.Controllers.RiderControllers;

import com.example.dynamic_ride_allocator.Controllers.LoginController;
import com.example.dynamic_ride_allocator.Models.Driver;
import com.example.dynamic_ride_allocator.Models.Rider;
import com.example.dynamic_ride_allocator.Models.Trip;
import com.example.dynamic_ride_allocator.graphs.RideAllocator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class BookRideController {
    @FXML
    public ComboBox<String> pickupComboBox;
    @FXML
    public ComboBox<String> destinationComboBox;
    @FXML
    public ComboBox<String> vehicleType;
    @FXML
    public Label bookingFeedback;

    @FXML
    public void initialize(){
        pickupComboBox.getItems().addAll(RideAllocator.getAllLocations());
        destinationComboBox.getItems().addAll(RideAllocator.getAllLocations());
        vehicleType.getItems().addAll("Bike","Car(Economy)","Car(Comfort");
    }
    public void findRiderAction(ActionEvent event) {
        String pickup=pickupComboBox.getSelectionModel().getSelectedItem();
        String destination=destinationComboBox.getSelectionModel().getSelectedItem();
        String vehicle=vehicleType.getSelectionModel().getSelectedItem();

        if(!(RideAllocator.locationExists(pickup))){
            showAlert("Pickup location cannot be empty",1);
            return;
        }
        if(!(RideAllocator.locationExists(destination))){
            showAlert("Destination location cannot be empty",1);
            return;
        }
        Driver nearestDriver=RideAllocator.requestRide(pickup,Integer.MAX_VALUE);
        if(nearestDriver==null){
            showAlert("No Driver Available",2);
            return;
        }
        Rider rider= (Rider) LoginController.currentUser;
        Trip trip=getTripObject(nearestDriver,rider,pickup,destination);
        nearestDriver.getRideRequests().add(trip);
        rider.setCurrentRide(trip);
        showAlert("Ride request sent successfully to nearest driver please wait till he accepts",2);
    }

    private Trip getTripObject(Driver nearestDriver, Rider rider,String pick,String drop) {
        int id= (int) (Math.random() * 100000);
        Trip trip=new Trip(id,nearestDriver.getEmail(),rider.getEmail(),pick,drop,nearestDriver.getDistance()*15.0);
        trip.setDistance(nearestDriver.getDistance());
        trip.setStatus("Ongoing");
        trip.setTime(System.currentTimeMillis());
        return trip;
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
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/welcome.fxml")));
        s.setTitle("Welcome To Dynamic Ride Allocator");
        s.setScene(new Scene(root));

    }

    public void showAlert(String message,int type){
        Alert alert;
        if(type==1)
            alert=new Alert(Alert.AlertType.ERROR);
        else
            alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
