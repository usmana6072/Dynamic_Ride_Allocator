package com.example.dynamic_ride_allocator.Controllers.AdminControllers;

import com.example.dynamic_ride_allocator.Controllers.LoginController;
import com.example.dynamic_ride_allocator.DataLayer.UsersData;
import com.example.dynamic_ride_allocator.Models.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AdminProfileController {
    public Label profileEmailDisplay;
    public TextField fieldUsername;
    public TextField fieldEmail;
    public PasswordField fieldCurrentPassword;
    public PasswordField fieldNewPassword;
    public PasswordField fieldConfirmPassword;

    @FXML
    public void initialize(){
        Admin admin=(Admin) LoginController.currentUser;
        profileEmailDisplay.setText(admin.getEmail());
        fieldUsername.setText(admin.getFullName());
        fieldEmail.setText(admin.getEmail());
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

    public void updateProfileAction(ActionEvent event) {
        String oldEmail=profileEmailDisplay.getText();
        String name=fieldUsername.getText();
        String email=fieldEmail.getText();
        String password=fieldCurrentPassword.getText();
        String newPass=fieldNewPassword.getText();
        String confirm=fieldConfirmPassword.getText();
        Admin admin=UsersData.adminData.get(oldEmail);
        if(password.equals(admin.getPassword()) && newPass.equals(confirm)){
            admin.setFullName(name);
            admin.setEmail(email);
            admin.setPassword(newPass);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Profile Update");
            alert.setContentText("Profile Updated Successfully");
            alert.setHeaderText(null);
            alert.showAndWait();
            new Thread(UsersData::writeAdmin).start();
        }
    }
}
