package com.example.dynamic_ride_allocator.Controllers.RiderControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RideConfirmedController {
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
