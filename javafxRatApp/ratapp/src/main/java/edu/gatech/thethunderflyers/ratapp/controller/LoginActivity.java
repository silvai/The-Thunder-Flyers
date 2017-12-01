package edu.gatech.thethunderflyers.ratapp.controller;

import edu.gatech.thethunderflyers.ratapp.model.APIMessage;
import edu.gatech.thethunderflyers.ratapp.model.Model;
import edu.gatech.thethunderflyers.ratapp.util.APIClient;
import edu.gatech.thethunderflyers.ratapp.util.AsyncHandler;
import edu.gatech.thethunderflyers.ratapp.util.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginActivity implements AsyncHandler<APIMessage> {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button cancelButton;

    public void submit(ActionEvent actionEvent) {
        Validator validator = new Validator(username, password);
        if (validator.validate()) {
            String uname = username.getText();
            String pword = password.getText();

            APIClient.API_CLIENT.login(Model.getLoginUser(uname, pword), this);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("One or more fields was empty.");
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
    public void handleResponse(APIMessage response) {
        if (response.getSuccess()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("res/mapsView.fxml"));
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.setTitle("Rat Map");
                stage.setResizable(false);
                ((Stage) this.cancelButton.getScene().getWindow()).close();
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(response.getMessage());
            alert.show();
        }
    }
}
