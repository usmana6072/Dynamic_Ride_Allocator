package com.example.dynamic_ride_allocator.Controllers.DriverControllers;

import com.example.dynamic_ride_allocator.Controllers.LoginController;
import com.example.dynamic_ride_allocator.DataLayer.UsersData;
import com.example.dynamic_ride_allocator.Models.Driver;
import com.example.dynamic_ride_allocator.graphs.CityGraph;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AvailabilityScreenController {
    public ComboBox<String> locationsSet;
    public Button btnUpdateLocation;
    @FXML
    private ToggleButton availabilityToggle;
    @FXML
    private Label tvStaus;

    @FXML
    public void initialize(){

        Driver driver=(Driver) LoginController.currentUser;

        availabilityToggle.setSelected(driver.isAvailable());

        locationsSet.getItems().addAll(CityGraph.getAllLocations());

        if(driver.getLocation()==null || driver.getLocation().isEmpty())
            locationsSet.setValue("");
        else
            locationsSet.getSelectionModel().select(driver.getLocation());
        availabilityToggle.setText((driver.isAvailable())? "Online":"Offline");
        tvStaus.setText((driver.isAvailable())?"You are Currently Online":"You are Currently Offline");

        availabilityToggle.selectedProperty().addListener((obs,oldValue,newValue)->{

            driver.setAvailable(newValue);

            if(newValue){
                tvStaus.setText("You are currently Online");
                availabilityToggle.setText("Online");
            }else{
                tvStaus.setText("You are currently Offline");
                availabilityToggle.setText("Offline");
            }
        });
        btnUpdateLocation.setOnAction(e->{
            String location=locationsSet.getSelectionModel().getSelectedItem().trim();
            if(location.isEmpty())
                return;
            driver.setLocation(location);
            new Thread(UsersData::writeDriver).start();
            showAlert("Location updated Successfully");
        });
    }

    // this adds action listeners to side menu
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

    private void showAlert(String message){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}