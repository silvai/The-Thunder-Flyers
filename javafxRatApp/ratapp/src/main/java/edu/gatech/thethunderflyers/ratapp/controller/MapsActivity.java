package edu.gatech.thethunderflyers.ratapp.controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import edu.gatech.thethunderflyers.ratapp.model.RatData;
import edu.gatech.thethunderflyers.ratapp.util.APIClient;
import edu.gatech.thethunderflyers.ratapp.util.AsyncHandler;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import tornadofx.control.DateTimePicker;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MapsActivity implements Initializable, AsyncHandler<ObservableList<RatData>> {
    @FXML
    private GoogleMapView mapView;
    private GoogleMap map;

    @FXML
    private DateTimePicker beginDateTimePicker;
    @FXML
    private DateTimePicker endDateTimePicker;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInitializedListener(() -> configureMap());
        beginDateTimePicker.valueProperty().setValue(LocalDate.from(beginDateTimePicker.getDateTimeValue()));
        endDateTimePicker.valueProperty().setValue(LocalDate.from(endDateTimePicker.getDateTimeValue()));
    }

    public void submitDates() {
        APIClient.API_CLIENT.getRatDataDateRange(beginDateTimePicker.getDateTimeValue()
                        .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                endDateTimePicker.getDateTimeValue().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                "", this);
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
        System.out.println(response);
        map.clearMarkers();
        for (RatData rd: response) {
            Marker marker = new Marker(new MarkerOptions()
                    .position(new LatLong(rd.getLatitude(), rd.getLongitude())));
            map.addMarker(marker);
        }
    }
}
