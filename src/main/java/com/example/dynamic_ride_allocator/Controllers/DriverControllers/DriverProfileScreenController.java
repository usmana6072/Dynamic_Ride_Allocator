package com.example.dynamic_ride_allocator.Controllers.DriverControllers;

import com.example.dynamic_ride_allocator.Controllers.LoginController;
import com.example.dynamic_ride_allocator.DataLayer.UsersData;
import com.example.dynamic_ride_allocator.Models.Driver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DriverProfileScreenController {
    @FXML
    private TextField tvName;
    @FXML
    private TextField tvEmail;
    @FXML
    private TextField tvPhone;
    @FXML
    private TextField tvVehicleType;
    @FXML
    private TextField tvVehicleNumber;
    @FXML
    private TextField tvLicenceNumber;

    @FXML
    public void initialize() {
        Driver driver=(Driver) LoginController.currentUser;
        tvName.setText(driver.getName());
        tvEmail.setText(driver.getEmail());
        tvPhone.setText(driver.getPhone());
        tvLicenceNumber.setText(driver.getLicenceNumber());
        tvVehicleNumber.setText(driver.getVehicleNumber());
        tvVehicleType.setText(driver.getVehicleType());

    }

    //this is to perform action event on updateProfile
    @FXML
    public void updateProfile(ActionEvent event){
        Driver driver=(Driver) LoginController.currentUser ;
        String name=tvName.getText();
        String email=tvEmail.getText();
        String phone=tvPhone.getText();
        String vehicleType=tvVehicleType.getText();
        String vehicleNumber=tvVehicleNumber.getText();
        String licence=tvLicenceNumber.getText();
        if(name.isEmpty() || email.isEmpty() || phone.isEmpty() || vehicleType.isEmpty() || vehicleNumber.isEmpty() || licence.isEmpty()){
            showAlert("No field should be empty");
            return;
        }
        driver.setName(name);
        driver.setEmail(email);
        driver.setPhone(phone);
        driver.setVehicleNumber(vehicleNumber);
        driver.setVehicleType(vehicleType);
        driver.setLicenceNumber(licence);
        new Thread(UsersData::writeDriver).start();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Profile Updating");
        alert.setContentText("Profile Updated Successfully");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void showAlert(String s) {
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Update Profiel Error");
        alert.setContentText(s);
        alert.setHeaderText(null);
        alert.showAndWait();
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
}
