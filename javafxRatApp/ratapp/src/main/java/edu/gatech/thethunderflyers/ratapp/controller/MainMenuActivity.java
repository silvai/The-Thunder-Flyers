package edu.gatech.thethunderflyers.ratapp.controller;

import edu.gatech.thethunderflyers.ratapp.model.APIMessage;
import edu.gatech.thethunderflyers.ratapp.model.RatData;
import edu.gatech.thethunderflyers.ratapp.util.APIClient;
import edu.gatech.thethunderflyers.ratapp.util.AsyncHandler;
import edu.gatech.thethunderflyers.ratapp.util.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainMenuActivity implements Initializable, AsyncHandler<List<RatData>> {
    @FXML
    private ListView<RatData> reportTableView;
    @FXML
    private Button logoutButton, graphButton, mapButton, reportButton;
    private Navigator navigator = new Navigator();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void logout() {
        navigator.navigate("res/welcomeView.fxml", "Welcome!",
                (Stage) logoutButton.getScene().getWindow());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logged Out");
        alert.setHeaderText("Successfully logged out");
        alert.show();
    }

    public void graphs() {
        navigator.navigate("res/graphsView.fxml", "Rat Graph",
                (Stage) graphButton.getScene().getWindow());
    }

    public void maps() {
        navigator.navigate("res/mapsView.fxml", "Rat Map",
                (Stage) mapButton.getScene().getWindow());
    }

    public void report() {
        navigator.navigate("res/reportView.fxml", "New Rat Report",
                (Stage) reportButton.getScene().getWindow());
    }

    @Override
    public void handleResponse(List<RatData> response) {

    }
}
