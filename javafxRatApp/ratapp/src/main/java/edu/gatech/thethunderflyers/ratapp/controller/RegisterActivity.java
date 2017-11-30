package edu.gatech.thethunderflyers.ratapp.controller;

import edu.gatech.thethunderflyers.ratapp.model.UserMode;
import edu.gatech.thethunderflyers.ratapp.util.Validator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterActivity implements Initializable {

    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField firstNameText, lastNameText, usernameText,
            passwordText, confirmPasswordText;
    @FXML
    private ChoiceBox<UserMode> userModeBox;

    public void register() {
        boolean registerSuccess = false;
        Validator validator = new Validator(firstNameText, lastNameText,
                usernameText, passwordText, confirmPasswordText);

        //validate the text fields to see if it's null or not
        if (validator.validate()) {


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userModeBox.getItems().setAll(UserMode.values());
        userModeBox.getSelectionModel().selectFirst();
    }
}
