package com.example.dynamic_ride_allocator.Controllers.AdminControllers;

import com.example.dynamic_ride_allocator.Controllers.LoginController;
import com.example.dynamic_ride_allocator.DataLayer.UsersData;
import com.example.dynamic_ride_allocator.Models.Driver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AdminDashboardController {


    public Label tvTotalRider;
    public Label tvTotalDriver;
    public Label tvPendingApprovals;
    public Label tvActiveDriver;

    @FXML
    public void initialize(){
        int active=0,pending=0;
        for(String s: UsersData.driverData.keySet()){
            if(UsersData.driverData.get(s).isAvailable())
                active++;
            if(!UsersData.driverData.get(s).isApproved()) {
                pending++;
            }

        }
        tvTotalDriver.setText(UsersData.driverData.size()+"");
        tvTotalRider.setText(UsersData.riderData.size()+"");
        tvActiveDriver.setText(active+"");
        tvPendingApprovals.setText(pending+"");
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
}
