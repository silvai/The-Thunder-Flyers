package edu.gatech.thethunderflyers.ratapp.controller;

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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("res/loginView.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Login Screen");
            stage.setResizable(false);
            ((Stage) this.login.getScene().getWindow()).close();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("res/registerView.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Register Screen");
            stage.setResizable(false);
            ((Stage) this.register.getScene().getWindow()).close();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
