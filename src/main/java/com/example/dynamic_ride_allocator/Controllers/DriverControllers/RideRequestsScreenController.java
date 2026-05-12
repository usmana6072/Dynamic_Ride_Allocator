package com.example.dynamic_ride_allocator.Controllers.DriverControllers;

import com.example.dynamic_ride_allocator.Controllers.LoginController;
import com.example.dynamic_ride_allocator.DataLayer.UsersData;
import com.example.dynamic_ride_allocator.Models.Driver;
import com.example.dynamic_ride_allocator.Models.Trip;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class RideRequestsScreenController {

    public VBox requestsContainer;

    ArrayList<Trip> requests;
    Driver currentDriver =(Driver) LoginController.currentUser;
    @FXML
    public void initialize(){
        requests=currentDriver.getRideRequests();
        if(requests.size()==0)
            showEmptyState();
        else
            loadRequests();
    }

    //this method is to load all ride requests of current user
    private void loadRequests() {
        for(Trip p:requests)
            requestsContainer.getChildren().add(buildRequestCard(p));
    }

    // ── Card Builder ─────────────────────────────────────────────
    private VBox buildRequestCard(Trip request) {

        // Outer Card VBox that will contain a single request
        VBox card = new VBox();
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #1A6DBB;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-width: 0 0 0 3;" +
                        "-fx-padding: 14 16 14 16;"
        );
        VBox.setMargin(card, new Insets(0, 0, 10, 0));

        // this is to show name and status
        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(topRow, new Insets(0, 0, 8, 0));

        Label nameLabel = new Label(UsersData.riderData.get(request.getRiderEmail()).getName());
        nameLabel.setStyle(
                "-fx-text-fill: #1A3A5C;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;"
        );
        HBox.setHgrow(nameLabel, Priority.ALWAYS);

        Label badge = new Label("  " + request.getStatus() + "  ");
        badge.setStyle(
                "-fx-background-color:#E6F0FF;" +
                        "-fx-text-fill:#A06000" +
                        "-fx-background-radius: 20;" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 2 8 2 8;"
        );

        topRow.getChildren().addAll(nameLabel, badge);

        // this is to show the starting point
        Label pickupLabel = new Label("📍  Pickup: " + request.getPickupLocation());
        pickupLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12px;");
        VBox.setMargin(pickupLabel, new Insets(0, 0, 2, 0));

        // this is to show the destination
        Label destLabel = new Label("🏁  Drop: " + request.getDropOffLocation());
        destLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12px;");
        VBox.setMargin(destLabel, new Insets(0, 0, 10, 0));

        //this is to show fair and accept reject option
        HBox bottomRow = new HBox(10);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        Label fareLabel = new Label(String.format("RS %.2f",request.getFare()));
        fareLabel.setStyle(
                "-fx-text-fill: #27A065;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;"
        );
        HBox.setHgrow(fareLabel, Priority.ALWAYS);

        Button acceptBtn = new Button("Accept");
        acceptBtn.setPrefSize(90, 30);
        acceptBtn.setStyle(
                "-fx-background-color: #27A065;" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 6;" +
                        "-fx-cursor: hand;"
        );

        Button rejectBtn = new Button("Reject");
        rejectBtn.setPrefSize(90, 30);
        rejectBtn.setStyle(
                "-fx-background-color: #D94040;" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 6;" +
                        "-fx-cursor: hand;"
        );

        bottomRow.getChildren().addAll(fareLabel, acceptBtn, rejectBtn);

        // adding actions to the buttons
        acceptBtn.setOnAction(e -> handleAccept(request, card, bottomRow, badge));
        rejectBtn.setOnAction(e -> handleReject(request, card));

        // add all elements to the request card
        card.getChildren().addAll(topRow, pickupLabel, destLabel, bottomRow);
        return card;
    }

    // ── Accept Handler ────────────────────────────────────────────
    private void handleAccept(Trip request, VBox card,
                              HBox bottomRow, Label badge) {
        request.setStatus("In Progress");

        currentDriver.setCurrentRide(request);

        currentDriver.setAvailable(false);

        if(!UsersData.driverHistory.containsKey(currentDriver.getEmail()))
            UsersData.driverHistory.put(currentDriver.getEmail(),new ArrayList<Trip>());
        UsersData.driverHistory.get(currentDriver.getEmail()).add(request);

        // update the badge to accepted to show ride selected
        badge.setText("  Accepted  ");
        badge.setStyle(
                "-fx-background-color: #E6F7EF;" +
                        "-fx-text-fill: #1A7A4A;" +
                        "-fx-background-radius: 20;" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 2 8 2 8;"
        );

        // turn card border green to show difference
        card.setStyle(
                "-fx-background-color: #F0FFF7;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #27A065;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-width: 0 0 0 3;" +
                        "-fx-padding: 14 16 14 16;"
        );

        // Remove button from the card and add confirmation label
        bottomRow.getChildren().removeIf(node -> node instanceof Button);
        Label confirmedLabel = new Label("✓  Ride Accepted");
        confirmedLabel.setStyle(
                "-fx-text-fill: #27A065;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;"
        );
        bottomRow.getChildren().add(confirmedLabel);

        // this is to dim all other carts
        dimOtherCards(card);
        currentDriver.getRideRequests().clear();
        new Thread(UsersData::writeDriverHistory).start();
        if(!UsersData.riderHistory.containsKey(request.getRiderEmail()))
            UsersData.riderHistory.put(request.getRiderEmail(),new ArrayList<Trip>());
        UsersData.riderHistory.get(request.getRiderEmail()).add(request);
        new Thread(UsersData::writeRiderHistory).start();

    }

    // ── Reject Handler ────────────────────────────────────────────
    private void handleReject(Trip request, VBox card) {

        // animate card to gray and then remove from the list
        UsersData.riderData.get(request.getRiderEmail()).getCurrentRide().setStatus("Rejected");
        currentDriver.getRideRequests().remove(request);
        card.setStyle(
                "-fx-background-color: #FFF0F0;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #D94040;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-width: 0 0 0 3;" +
                        "-fx-padding: 14 16 14 16;" +
                        "-fx-opacity: 0.45;"
        );

        // 3. Remove from container after brief visual feedback
        javafx.animation.PauseTransition pause =
                new javafx.animation.PauseTransition(
                        javafx.util.Duration.millis(500)
                );
        pause.setOnFinished(e -> {
            requestsContainer.getChildren().remove(card);
            if (requestsContainer.getChildren().isEmpty()) {
                showEmptyState();
            }
        });
        pause.play();
    }

    // ── Dim All Cards Except The Accepted One ────────────────────
    private void dimOtherCards(VBox acceptedCard) {
        for (var node : requestsContainer.getChildren()) {
            if (node instanceof VBox otherCard && otherCard != acceptedCard) {
                otherCard.setOpacity(0.45);
                // Disable buttons on other cards
                for (var child : ((VBox) node).getChildren()) {
                    if (child instanceof HBox hbox) {
                        hbox.getChildren().forEach(btn -> {
                            if (btn instanceof Button b) b.setDisable(true);
                        });
                    }
                }
            }
        }
    }

    // ── Empty State ───────────────────────────────────────────────
    private void showEmptyState() {
        VBox empty = new VBox(10);
        empty.setAlignment(Pos.CENTER);
        empty.setPadding(new Insets(60, 20, 60, 20));

        Label icon = new Label("📋");
        icon.setStyle("-fx-font-size: 36px;");

        Label title = new Label("No ride requests at the moment");
        title.setStyle(
                "-fx-text-fill: #999999;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;"
        );

        Label subtitle = new Label("New requests will appear here automatically");
        subtitle.setStyle("-fx-text-fill: #BBBBBB; -fx-font-size: 11px;");

        empty.getChildren().addAll(icon, title, subtitle);
        requestsContainer.getChildren().add(empty);
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
