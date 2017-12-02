package edu.gatech.thethunderflyers.ratapp.controller;

import edu.gatech.thethunderflyers.ratapp.model.APIMessage;
import edu.gatech.thethunderflyers.ratapp.model.Model;
import edu.gatech.thethunderflyers.ratapp.model.UserMode;
import edu.gatech.thethunderflyers.ratapp.util.APIClient;
import edu.gatech.thethunderflyers.ratapp.util.AsyncHandler;
import edu.gatech.thethunderflyers.ratapp.util.Navigator;
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

public class RegisterActivity implements Initializable, AsyncHandler<APIMessage> {

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
        boolean passwordsMatch;
        Validator validator = new Validator(firstNameText, lastNameText,
                usernameText, passwordText, confirmPasswordText);
        if (validator.validate()) {
            passwordsMatch = (validator.passwordsMatch(passwordText.getText(), confirmPasswordText.getText()));
            if (passwordsMatch) {
                APIClient.API_CLIENT.register(Model.getUser(firstNameText.getText(),
                        lastNameText.getText(), usernameText.getText(), passwordText.getText(),
                        userModeBox.getValue()), this);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("One or more fields incorrect!");
            alert.show();
        }
    }

    public void cancel() {
        Stage current = (Stage) this.cancelButton.getScene().getWindow();
        new Navigator().navigate("res/welcomeView.fxml", "Welcome!", current);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userModeBox.getItems().setAll(UserMode.values());
        userModeBox.getSelectionModel().selectFirst();
    }

    @Override
    public void handleResponse(APIMessage response) {
        Alert alert;
        if (response.getSuccess()) {
            Stage current = (Stage) this.submitButton.getScene().getWindow();
            new Navigator().navigate("res/loginView.fxml", "Login Screen", current);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("Account registered successfully!");
            alert.show();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to register!");
            alert.setContentText("User may already be registered");
            alert.show();
        }
    }
}
