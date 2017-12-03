package edu.gatech.thethunderflyers.ratapp.controller;

import edu.gatech.thethunderflyers.ratapp.model.RatData;
import edu.gatech.thethunderflyers.ratapp.util.APIClient;
import edu.gatech.thethunderflyers.ratapp.util.AsyncHandler;
import edu.gatech.thethunderflyers.ratapp.util.Navigator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.ScrollEvent;

import javafx.stage.Stage;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainMenuActivity implements Initializable, AsyncHandler<ObservableList<RatData>> {
    @FXML
    private ListView<RatData> reportListView;
    @FXML
    private Button logoutButton, graphButton, mapButton, reportButton;
    private Navigator navigator = new Navigator();

    private ObservableList<RatData> dataInList = FXCollections.observableArrayList();

    private boolean loading;
    private int lastId = 0;
    private Date lastDate = new Date(0);
    private int scrollDelta = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reportListView.setItems(dataInList);
        reportListView.setFixedCellSize(24);
        reportListView.setEditable(false);
        reportListView.addEventFilter(ScrollEvent.SCROLL, (event) -> {
            scrollDelta += -event.getDeltaY();
            if (!loading && scrollDelta >= 280) {
                loading = true;
                scrollDelta = 0;
                APIClient.API_CLIENT.getRatDataList(lastId, lastDate.getTime(), LoginActivity.apiMessage, this);
            }
        });
        reportListView.setOnMouseClicked((event) -> {
            RatData rd = reportListView.getSelectionModel().getSelectedItem();
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText(String.format("Id: %d%nCreated on: %s%nLocation Type: %s%nZip: %d%nAddress: %s%nCity: %s%n" +
                    "Borough: %s%nLatitude: %.4f%nLongitude: %.4f%n",
                    rd.getId(), rd.getDate().toString(), rd.getLocationType().toString(), rd.getZip(), rd.getAddress(),
                    rd.getCity(), rd.getBorough().toString(), rd.getLatitude(), rd.getLongitude()));
            a.setTitle("Detailed Rat Data Information");
            a.setHeaderText(String.format("Sighting Id: %d", rd.getId()));
            a.show();
        });
        loading = true;
        APIClient.API_CLIENT.getRatDataList(lastId, lastDate.getTime(), LoginActivity.apiMessage, this);
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
    public void handleResponse(ObservableList<RatData> response) {
        dataInList.addAll(response);
        RatData rd = response.get(response.size() - 1);
        this.lastDate = rd.getDate();
        this.lastId = rd.getId();
        reportListView.refresh();
        loading = false;
    }
}
