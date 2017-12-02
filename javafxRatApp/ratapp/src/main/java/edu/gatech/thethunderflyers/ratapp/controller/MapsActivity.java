package edu.gatech.thethunderflyers.ratapp.controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import edu.gatech.thethunderflyers.ratapp.model.RatData;
import edu.gatech.thethunderflyers.ratapp.util.APIClient;
import edu.gatech.thethunderflyers.ratapp.util.AsyncHandler;
import edu.gatech.thethunderflyers.ratapp.util.Navigator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;

import javax.print.attribute.standard.MediaSize;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class MapsActivity implements Initializable, AsyncHandler<ObservableList<RatData>> {
    @FXML
    private GoogleMapView mapView;
    private GoogleMap map;

    @FXML
    private DateTimePicker beginDateTimePicker;
    @FXML
    private DateTimePicker endDateTimePicker;
    @FXML
    private Button mainButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInitializedListener(() -> configureMap());
        beginDateTimePicker.valueProperty().setValue(LocalDate.from(beginDateTimePicker.getDateTimeValue()));
        endDateTimePicker.valueProperty().setValue(LocalDate.from(endDateTimePicker.getDateTimeValue()));
    }

    public void main() {
        Stage current = (Stage) this.mainButton.getScene().getWindow();
        new Navigator().navigate("res/mainMenuView.fxml", "Main Menu", current);
    }

    public void submitDates() {
        if (beginDateTimePicker.getDateTimeValue().compareTo(endDateTimePicker.getDateTimeValue()) > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect Dates");
            alert.show();
        } else {
            APIClient.API_CLIENT.getRatDataDateRange(beginDateTimePicker.getDateTimeValue()
                            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                    endDateTimePicker.getDateTimeValue().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                    LoginActivity.apiMessage.getMessage(), this);
        }
    }

    protected void configureMap() {
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(40.7128, -74.0060))
                .mapType(MapTypeIdEnum.ROADMAP)
                .zoom(5);
        map = mapView.createMap(mapOptions, false);

        //the map becomes buggy without this when you try to click and drag
        map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {

        });

    }

    @Override
    public void handleResponse(ObservableList<RatData> response) {
        map.clearMarkers();
        for (RatData rd: response) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLong(rd.getLatitude(), rd.getLongitude()))
                    .title(rd.getDate().toString());
            Marker marker = new Marker(markerOptions);
            map.addMarker(marker);
            map.addUIEventHandler(marker, UIEventType.click, jsObject -> {
                //displays the information window
                InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                infoWindowOptions.content("ZIP: " + rd.getZip() + "<br>" + rd.getDate().toString());
                InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
                infoWindow.open(map, marker);
            });
        }
    }
}
