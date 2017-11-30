package edu.gatech.thethunderflyers.ratapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RegisterActivity {

    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;

    public void register() {
        boolean registerSuccess = true;
        //if register successful
        if (registerSuccess) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("res/loginView.fxml"));
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.setTitle("Login Screen");
                stage.setResizable(false);
                ((Stage) this.submitButton.getScene().getWindow()).close();
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to register!");
            alert.show();
        }
    }

    public void cancel() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("res/welcomeView.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Welcome!");
            stage.setResizable(false);
            ((Stage) this.cancelButton.getScene().getWindow()).close();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
