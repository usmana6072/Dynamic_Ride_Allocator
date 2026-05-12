package com.example.dynamic_ride_allocator.Controllers.RiderControllers;

import com.almasb.fxgl.core.collection.Array;
import com.example.dynamic_ride_allocator.Controllers.LoginController;
import com.example.dynamic_ride_allocator.DataLayer.UsersData;
import com.example.dynamic_ride_allocator.Models.Driver;
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

public class RiderDashboardController {

    public VBox recentRidesContainer;

    Rider currentRider=(Rider) LoginController.currentUser;
    @FXML
    public void initialize(){

        if(!UsersData.riderHistory.containsKey(currentRider.getEmail())){
            showEmptyState();
            UsersData.riderHistory.put(currentRider.getEmail(),new ArrayList<>());
            return;
        }
        ArrayList<Trip> trips= UsersData.riderHistory.get(currentRider.getEmail());
        for(Trip p:trips)
            recentRidesContainer.getChildren().add(buildRideCard(p));

    }

    private VBox buildRideCard(Trip ride) {
        Driver driver=UsersData.driverData.get(ride.getDriverEmail());
        VBox card = new VBox();
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #E4EAF4;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-width: 0.8;" +
                        "-fx-padding: 12 14 12 14;"
        );

        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        VBox leftCol = new VBox(3);
        HBox.setHgrow(leftCol, Priority.ALWAYS);

        Label routeLabel = new Label(String.format("%s --> %s",ride.getPickupLocation(),ride.getDropOffLocation()));
        routeLabel.setStyle("-fx-text-fill: #1A3A5C; -fx-font-size: 13px; -fx-font-weight: bold;");

        Date date=new Date(ride.getTime());
        SimpleDateFormat format=new SimpleDateFormat("dd//MM//yyyy");

        Label subLabel = new Label(driver.getName() + "  •  " + format.format(date).toString());
        subLabel.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px;");

        leftCol.getChildren().addAll(routeLabel, subLabel);

        VBox rightCol = new VBox(4);
        rightCol.setAlignment(Pos.CENTER_RIGHT);

        Label fareLabel = new Label(String.format("RS %.2f",ride.getFare()));
        fareLabel.setStyle("-fx-text-fill: #1A6DBB; -fx-font-size: 13px; -fx-font-weight: bold;");

        boolean completed = ride.getStatus().equals("Completed");
        Label badge = new Label(ride.getStatus());
        badge.setStyle(
                "-fx-background-color: " + (completed ? "#E6F7EF" : "#FDE8E8") + ";" +
                        "-fx-text-fill: "        + (completed ? "#1A7A4A" : "#C02020") + ";" +
                        "-fx-background-radius: 20;" +
                        "-fx-font-size: 10px; -fx-font-weight: bold;" +
                        "-fx-padding: 2 8 2 8;"
        );

        rightCol.getChildren().addAll(fareLabel, badge);
        row.getChildren().addAll(leftCol, rightCol);
        card.getChildren().add(row);
        return card;
    }

    private void showEmptyState() {
        VBox empty = new VBox(8);
        empty.setAlignment(Pos.CENTER);
        empty.setPadding(new Insets(30));
        Label icon = new Label("🚖");
        icon.setStyle("-fx-font-size: 30px;");
        Label msg = new Label("No rides yet. Book your first ride!");
        msg.setStyle("-fx-text-fill: #999999; -fx-font-size: 12px;");
        empty.getChildren().addAll(icon, msg);
        recentRidesContainer.getChildren().add(empty);
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
