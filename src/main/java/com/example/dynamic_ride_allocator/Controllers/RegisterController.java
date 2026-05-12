package com.example.dynamic_ride_allocator.Controllers;

import com.example.dynamic_ride_allocator.DataLayer.UsersData;
import com.example.dynamic_ride_allocator.Models.Driver;
import com.example.dynamic_ride_allocator.Models.Rider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    private Stage stage;
    @FXML private TextField tvName;
    @FXML private TextField tvEmail;
    @FXML private TextField tvPhone;
    @FXML private TextField tvAddress;

    @FXML private PasswordField tvPassword;
    @FXML private PasswordField tvConfirmPassword;
    @FXML private TextField tvVehicleType;
    @FXML private TextField tvVehicleNumber;
    @FXML private TextField tvLicenceNumber;
    @FXML private RadioButton btnDriver;
    @FXML private RadioButton btnRider;
    @FXML
    private ToggleGroup regRoleGroup;
    @FXML
    private VBox driverInfoSection;
    @FXML
    public void initialize() {
        regRoleGroup.selectedToggleProperty().addListener(
                (observable, oldValue, newValue) -> {

                    RadioButton selected =
                            (RadioButton) newValue;

                    boolean isRider =
                            selected.getText().equals("Driver");

                    driverInfoSection.setVisible(isRider);
                    driverInfoSection.setManaged(isRider);
                }
        );
    }

    @FXML
    public void handleRegister(ActionEvent event) throws IOException {
        stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        RadioButton button=(RadioButton) regRoleGroup.getSelectedToggle();
        switch (button.getText()){
            case "Driver":
                registerDriver();
                break;
            case "Rider":
                registerRider();
        }
    }

    public void registerDriver() throws IOException {
        String name,email,phone,password,confirmPassword,vehicleType,vehicleNumber,licenceNumber,address;
        name=tvName.getText();
        email=tvEmail.getText();
        phone=tvPhone.getText();
        password=tvPassword.getText();
        confirmPassword=tvConfirmPassword.getText();
        vehicleType=tvVehicleType.getText();
        vehicleNumber=tvVehicleNumber.getText();
        licenceNumber=tvLicenceNumber.getText();
        address=tvAddress.getText();

        if (name.trim().isEmpty() ||
                email.trim().isEmpty() ||
                phone.trim().isEmpty() ||
                password.isEmpty() ||
                confirmPassword.isEmpty() ||
                address.trim().isEmpty() ||
                (btnDriver.isSelected() &&
                        (vehicleType.trim().isEmpty() ||
                                vehicleNumber.trim().isEmpty() ||
                                licenceNumber.trim().isEmpty()))) {

            showAlert("Please fill in all required fields.");
            return;
        }

        if(!password.equals(confirmPassword)){
            showAlert("Password and ConfirmedPassword mismatch ");
            return;
        }
        Driver driver=new Driver(name,phone,vehicleType,vehicleNumber,password);
        driver.setEmail(email);
        driver.setLicenceNumber(licenceNumber);
        driver.setAddress(address);
        driver.setApproved(false);
        UsersData.driverData.put(email,driver);
        UsersData.appendDriver(email);
        showInformation("Registration Successfully processed");

        Parent root= FXMLLoader.load(getClass().getResource("/Layouts/welcome.fxml"));
        stage.setTitle("Welcome Screen");
        stage.setScene(new Scene(root));
    }

    public void registerRider() throws IOException {
        String name,email,phone,password,confirmPassword,address;
        name=tvName.getText();
        email=tvEmail.getText();
        phone=tvPhone.getText();
        password=tvPassword.getText();
        confirmPassword=tvConfirmPassword.getText();
        address=tvAddress.getText();

        if (name.trim().isEmpty() ||
                email.trim().isEmpty() ||
                phone.trim().isEmpty() ||
                password.isEmpty() ||
                confirmPassword.isEmpty() ||
                address.trim().isEmpty()) {
            showAlert("Please fill in all required fields.");
            return;
        }

        if(!password.equals(confirmPassword)){
            showAlert("Password and ConfirmedPassword mismatch ");
            return;
        }
        Rider rider=new Rider(name,phone,password);
        rider.setAddress(address);
        rider.setEmail(email);
        UsersData.riderData.put(email,rider);
        UsersData.appendRider(email);
        showInformation("Registration Successfully processed");

        Parent root= FXMLLoader.load(getClass().getResource("/Layouts/welcome.fxml"));
        stage.setTitle("Welcome Screen");
        stage.setScene(new Scene(root));
    }

    private void showAlert(String s){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Register error");
        alert.setContentText(s);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void showInformation(String s){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Register Information");
        alert.setContentText(s);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void handleBack(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/Layouts/welcome.fxml"));
        Stage s=(Stage) ((Node) event.getSource()).getScene().getWindow();
        s.setTitle("Welcome Screen");
        s.setScene(new Scene(root));
    }
}
