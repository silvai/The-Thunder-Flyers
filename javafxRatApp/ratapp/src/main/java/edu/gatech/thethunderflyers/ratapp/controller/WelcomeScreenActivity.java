package edu.gatech.thethunderflyers.ratapp.controller;

import edu.gatech.thethunderflyers.ratapp.util.Navigator;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WelcomeScreenActivity extends Application {

    @FXML
    private Button login;
    @FXML
    private Button register;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("res/welcomeView.fxml"));
        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(new Scene(root, 600, 350));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void login() {
        Stage current = (Stage) this.login.getScene().getWindow();
        new Navigator().navigate("res/loginView.fxml", "Login Screen", current);
    }

    public void register() {
        Stage current = (Stage) this.register.getScene().getWindow();
        new Navigator().navigate("res/registerView.fxml", "Register Screen", current);
    }
}
