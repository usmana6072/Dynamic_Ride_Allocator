package com.example.dynamic_ride_allocator.Controllers;

import com.example.dynamic_ride_allocator.DataLayer.UsersData;
import com.example.dynamic_ride_allocator.Models.Admin;
import com.example.dynamic_ride_allocator.Models.Driver;
import com.example.dynamic_ride_allocator.Models.Rider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    public static Object currentUser;
    private Stage stage;
    @FXML
    private TextField etEmail;
    @FXML
    private PasswordField etPassword;
    @FXML
    private ToggleGroup roleGroup;

    @FXML
    public void handleLogin(ActionEvent event) {
        String email=etEmail.getText().trim();
        String password=etPassword.getText();
        stage=(Stage) ((Node) event.getSource()).getScene().getWindow();

        RadioButton roleButton=(RadioButton) roleGroup.getSelectedToggle();
        if(roleButton==null){
            showAlert("Please Select a rolel");
            return;
        }
        String role=roleButton.getText().trim();
        try {
            switch (role) {
                case "Driver":
                    loginDriver(email, password);
                    break;
                case "Rider":
                    loginCustomer(email, password);
                    break;
                case "Admin":
                    loginAdmin(email, password);
                    break;
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void loginAdmin(String email, String password) throws IOException {
        if(UsersData.adminData.containsKey(email)){
            Admin admin=UsersData.adminData.get(email);
            if(password.equals(admin.getPassword())){
                currentUser=admin;
                Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/admin_dashboard.fxml")));
                stage.setTitle("Admin Dashboard");
                stage.setScene(new Scene(root));
            }else{
                showAlert("Wrong Password");
            }
        }else{
            showAlert("Admin email Does not exist");
        }
    }

    private void loginCustomer(String email, String password) throws IOException {
        if(UsersData.riderData.containsKey(email)){
            Rider rider=UsersData.riderData.get(email);
            if(rider.isBlocked()){
                showAlert("You have been blocked by admin Cannot login");
                return;
            }
            if(password.equals(rider.getPassword())) {
                currentUser=rider;
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/rider_dashboard.fxml")));
                stage.setTitle("Rider Dashboard");
                stage.setScene(new Scene(root));
            }else{
                showAlert("Wrong Password");
            }
        }else{
            showAlert("Rider email Does not exist");
        }
    }

    private void loginDriver(String email, String password) throws IOException {
        if(UsersData.driverData.containsKey(email)){
            Driver driver=UsersData.driverData.get(email);
            if(!driver.isApproved()){
                showAlert("You account is not approved Yet");
                return;
            }
            if(driver.isBlocked()){
                showAlert("You have been blocked by admin Cannot login");
                return;
            }
            if(password.equals(driver.getPassword())){
                currentUser=driver;
                Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/driver_dashboard.fxml")));
                stage.setTitle("Driver Dashboard");
                stage.setScene(new Scene(root));
            }else{
                showAlert("Wrong Password");
            }
        }else{
            showAlert("Driver Email Does not exist");
        }
        
    }

    public void showAlert(String s){
        Alert alert=new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Login Error");
        alert.setContentText(s);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void handleBack(ActionEvent event) throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("/Layouts/welcome.fxml"));
        Stage s=(Stage) ((Node) event.getSource()).getScene().getWindow();
        s.setTitle("Welcome Screen");
        s.setScene(new Scene(root));
    }
}
