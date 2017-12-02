package edu.gatech.thethunderflyers.ratapp.controller;

import edu.gatech.thethunderflyers.ratapp.model.*;
import edu.gatech.thethunderflyers.ratapp.util.APIClient;
import edu.gatech.thethunderflyers.ratapp.util.AsyncHandler;
import edu.gatech.thethunderflyers.ratapp.util.Navigator;
import edu.gatech.thethunderflyers.ratapp.util.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportActivity implements Initializable, AsyncHandler<APIMessage> {
    private Navigator navigator = new Navigator();
    private Validator validate;
    private Alert alert;
    private static APIMessage apiMessage;

    @FXML
    private Button submitButton, cancelButton;
    @FXML
    private TextField addressText, cityText, zipText, latitudeText, longitudeText;
    @FXML
    private ChoiceBox<Borough> boroughBox;
    @FXML
    private ChoiceBox<LocationType> locationTypeBox;

    public void submit() {
        validate = new Validator(addressText, cityText, zipText, latitudeText, longitudeText);
        if (validate.validate()) {
            int zip = Integer.parseInt(zipText.getText());
            double lat = Double.parseDouble(latitudeText.getText());
            double longitude = Double.parseDouble(longitudeText.getText());

            //need to change user id?
            //need to get the correct token.....

            APIClient.API_CLIENT.submitRatReport(
                    Model.getRatData(locationTypeBox.getSelectionModel().getSelectedItem(),
                            zip, addressText.getText(), cityText.getText(),
                            boroughBox.getSelectionModel().getSelectedItem(), lat,
                            longitude, 11), apiMessage.getMessage(), this);
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("One or more fields are empty!");
            alert.show();
        }
    }

    public void cancel() {
        navigator.navigate("res/mainMenuView.fxml", "Main Menu",
                (Stage) cancelButton.getScene().getWindow());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boroughBox.getItems().setAll(Borough.values());
        boroughBox.getSelectionModel().selectFirst();

        locationTypeBox.getItems().setAll(LocationType.values());
        locationTypeBox.getSelectionModel().selectFirst();
    }

    @Override
    public void handleResponse(APIMessage response) {
        apiMessage = response;
        if (response.getSuccess()) {
            navigator.navigate("mainMenuView.fxml", "Main Menu",
                    (Stage) submitButton.getScene().getWindow());
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("New Report Entered");
            alert.show();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("One or more fields are invalid!");
            alert.show();
        }
    }
}
