package com.example.dynamic_ride_allocator.Controllers.RiderControllers;

import com.example.dynamic_ride_allocator.Controllers.LoginController;
import com.example.dynamic_ride_allocator.DataLayer.UsersData;
import com.example.dynamic_ride_allocator.Models.Rider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RiderProfileController {
    public Label profileDisplayName;
    public TextField fieldFullName;
    public Label profileAvatarLabel;
    public TextField fieldEmail;
    public TextField fieldPhone;
    public TextField fieldAddress;
    public PasswordField fieldNewPassword;
    public Label profileFeedback;

    @FXML
    public void initialize(){
        Rider rider=(Rider) LoginController.currentUser;
        profileAvatarLabel.setText(rider.getName().charAt(0)+"");
        profileDisplayName.setText(rider.getName());
        fieldFullName.setText(rider.getName());
        fieldEmail.setText(rider.getEmail());
        fieldPhone.setText(rider.getPhone());
        fieldAddress.setText(rider.getAddress());
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

    public void updateProfileAction(ActionEvent event) {
        String name    = fieldFullName.getText().trim();
        String email   = fieldEmail.getText().trim();
        String phone   = fieldPhone.getText().trim();
        String address = fieldAddress.getText().trim();
        String newPass = fieldNewPassword.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showFeedback("Name, email and phone cannot be empty.", false);
            return;
        }
        if (address.isEmpty()) {
            showFeedback("Address cannot be empty.", false);
            return;
        }
        if (!email.contains("@")) {
            showFeedback("Please enter a valid email address.", false);
            return;
        }
        if (!newPass.isEmpty() && newPass.length() < 6) {
            showFeedback("Password must be at least 6 characters.", false);
            return;
        }

        Rider rider=(Rider)LoginController.currentUser;
        rider.setEmail(email);
        rider.setName(name);
        rider.setPhone(phone);
        rider.setAddress(address);
        rider.setPassword(newPass);
        profileAvatarLabel.setText(String.valueOf(name.charAt(0)).toUpperCase());
        profileDisplayName.setText(name);
        fieldNewPassword.clear();
        new Thread(UsersData::writeRider).start();

        showFeedback("Profile updated successfully!", true);
    }

    private void showFeedback(String msg, boolean success) {
        profileFeedback.setText(msg);
        profileFeedback.setStyle(
                "-fx-font-size: 11px; -fx-font-weight: bold;" +
                        "-fx-text-fill: " + (success ? "#1A7A4A" : "#C02020") + ";"
        );
    }
}
