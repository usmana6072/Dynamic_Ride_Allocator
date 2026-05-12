package com.example.dynamic_ride_allocator.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.util.Objects;

public class WelcomeController {
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button exitButton;
    @FXML
    private void handleLogin(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("/Layouts/login_screen.fxml")));

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void handleRegister(ActionEvent event) throws Exception{
        Parent root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Layouts/register_screen.fxml")));
        Stage stage=(Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private  void handleExit(ActionEvent event) throws Exception{
        Platform.exit();
    }
}
