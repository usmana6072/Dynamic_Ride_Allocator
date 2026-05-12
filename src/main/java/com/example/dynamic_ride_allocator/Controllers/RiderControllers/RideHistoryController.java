package com.example.dynamic_ride_allocator.Controllers.RiderControllers;

import com.example.dynamic_ride_allocator.Controllers.LoginController;
import com.example.dynamic_ride_allocator.DataLayer.UsersData;
import com.example.dynamic_ride_allocator.Models.Rider;
import com.example.dynamic_ride_allocator.Models.Trip;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class RideHistoryController {
    public VBox historyContainer;
    Rider rider=(Rider) LoginController.currentUser;

    @FXML
    public void initialize(){

        if(!UsersData.riderHistory.containsKey(rider.getEmail())){
            showEmptyState();
            UsersData.riderHistory.put(rider.getEmail(),new ArrayList<>());
            return;
        }

        ArrayList<Trip> trips=UsersData.riderHistory.get(rider.getEmail());
        for(Trip p:trips)
            historyContainer.getChildren().add(buildHistoryRow(p));
    }

    private HBox buildHistoryRow(Trip trip) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(8, 10, 8, 10));

        Date date=new Date(trip.getTime());
        SimpleDateFormat format=new SimpleDateFormat("dd//MM//yyyy");
        Label dateLabel = new Label(format.format(date).toString());
        dateLabel.setPrefWidth(90);
        dateLabel.setStyle("-fx-text-fill: #AAAAAA; -fx-font-size: 11px;");

        Label riderLabel = new Label(UsersData.driverData.get(trip.getDriverEmail()).getName());
        riderLabel.setPrefWidth(100);
        riderLabel.setStyle("-fx-text-fill: #1A3A5C; -fx-font-size: 12px;");

        Label routeLabel = new Label(String.format("%s -> %s",trip.getPickupLocation(),trip.getDropOffLocation()));
        routeLabel.setPrefWidth(150);
        routeLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 11px;");

        Label fareLabel = new Label(String.format("RS %.2f",trip.getFare()));
        fareLabel.setPrefWidth(60);
        fareLabel.setStyle("-fx-text-fill: #1A6DBB; -fx-font-size: 12px; -fx-font-weight: bold;");
        HBox.setHgrow(fareLabel, Priority.ALWAYS);

        boolean completed = trip.getStatus().equals("Completed");
        Label badge = new Label(trip.getStatus());
        badge.setStyle(
                "-fx-background-color: " + (completed ? "#E6F7EF" : "#FDE8E8") + ";" +
                        "-fx-text-fill: "        + (completed ? "#1A7A4A" : "#C02020") + ";" +
                        "-fx-background-radius: 20;" +
                        "-fx-font-size: 10px; -fx-font-weight: bold;" +
                        "-fx-padding: 2 8 2 8;"
        );

        row.getChildren().addAll(dateLabel, riderLabel, routeLabel, fareLabel, badge);
        return row;
    }

    private void showEmptyState() {
        VBox empty = new VBox(8);
        empty.setAlignment(Pos.CENTER);
        empty.setPadding(new Insets(40));
        Label icon = new Label("📜");
        icon.setStyle("-fx-font-size: 30px;");
        Label msg = new Label("No ride history yet");
        msg.setStyle("-fx-text-fill: #999999; -fx-font-size: 12px;");
        empty.getChildren().addAll(icon, msg);
        historyContainer.getChildren().add(empty);
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
