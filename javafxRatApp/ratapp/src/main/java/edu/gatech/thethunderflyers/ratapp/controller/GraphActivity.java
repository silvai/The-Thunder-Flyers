package edu.gatech.thethunderflyers.ratapp.controller;

import edu.gatech.thethunderflyers.ratapp.model.Graphs;
import edu.gatech.thethunderflyers.ratapp.model.RatData;
import edu.gatech.thethunderflyers.ratapp.util.APIClient;
import edu.gatech.thethunderflyers.ratapp.util.AsyncHandler;
import edu.gatech.thethunderflyers.ratapp.util.Navigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class GraphActivity implements Initializable, AsyncHandler<ObservableList<RatData>> {
    private Chart chart;
    private Navigator navigator = new Navigator();
    @FXML
    private ChoiceBox<Graphs> graphBox;
    @FXML
    private Button mainMenuButton;
    @FXML
    private Pane graphContainer;
    @FXML
    private DateTimePicker beginDateTimePicker;
    @FXML
    private DateTimePicker endDateTimePicker;

    public void graph() {
        if (beginDateTimePicker.getValue().compareTo(endDateTimePicker.getValue()) > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect Dates");
            alert.show();
        } else {
            APIClient.API_CLIENT.getRatDataDateRange(beginDateTimePicker.getDateTimeValue()
                            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                    endDateTimePicker.getDateTimeValue().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                    LoginActivity.apiMessage, this);
        }
    }

    public void mainMenu() {
        navigator.navigate("res/mainMenuView.fxml", "Main Menu",
                (Stage) mainMenuButton.getScene().getWindow());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graphBox.getItems().setAll(Graphs.values());
        graphBox.getSelectionModel().selectFirst();
        beginDateTimePicker.valueProperty().setValue(LocalDate.from(beginDateTimePicker.getDateTimeValue()));
        endDateTimePicker.valueProperty().setValue(LocalDate.from(endDateTimePicker.getDateTimeValue()));
    }

    @Override
    public void handleResponse(ObservableList<RatData> response) {
        final Calendar d1 = Calendar.getInstance();
        final Calendar d2 = Calendar.getInstance();
        final Calendar iterate = Calendar.getInstance();

        d1.setTime(new Date(beginDateTimePicker.getDateTimeValue().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        d2.setTime(new Date(endDateTimePicker.getDateTimeValue().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        iterate.setTime(new Date(beginDateTimePicker.getDateTimeValue().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));

        int months = getMonthDiff(d1, d2) + 1;

        int[] entries = new int[months];
        for (RatData rd: response) {
            Date tempDate = rd.getDate();
            Calendar tempCal = Calendar.getInstance();
            tempCal.setTime(tempDate);
            entries[months - getMonthDiff(tempCal, d2) - 1] += 1;
        }


        final String[] monthYears = new String[months];
        for (int k = 0; k < months; k++) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy", Locale.US);
            monthYears[k] = sdf.format(iterate.getTime());
            iterate.add(Calendar.MONTH, 1);
        }

        Graphs selected = graphBox.getSelectionModel().getSelectedItem();
        switch (selected) {
            case BAR_CHART:
                graphContainer.getChildren().clear();
                final CategoryAxis xAxis = new CategoryAxis();
                final NumberAxis yAxis = new NumberAxis();
                chart = new BarChart<>(xAxis, yAxis);

                xAxis.setLabel("Months");
                yAxis.setLabel("Rat Sightings");

                XYChart.Series series = new XYChart.Series();
                series.setName("Sightings");
                for (int i = 0; i < months; i++) {
                    series.getData().add(new XYChart.Data<>(monthYears[i], entries[i]));
                }

                ((BarChart) chart).getData().add(series);
                chart.setMaxSize(graphContainer.getWidth(), graphContainer.getHeight());
                graphContainer.getChildren().add(chart);
                break;
            case PIE_CHART:
                graphContainer.getChildren().clear();

                ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
                for (int j = 0; j < months; j++) {
                    data.add(new PieChart.Data(monthYears[j], entries[j]));
                }
                chart = new PieChart(data);
                chart.setMaxSize(graphContainer.getWidth(), graphContainer.getHeight());
                graphContainer.getChildren().add(chart);
                break;
            case LINE_CHART:
                graphContainer.getChildren().clear();
                final CategoryAxis xAxisLine = new CategoryAxis();
                final NumberAxis yAxisLine = new NumberAxis();
                chart = new LineChart<>(xAxisLine, yAxisLine);

                xAxisLine.setLabel("Months");
                yAxisLine.setLabel("Rat Sightings");

                XYChart.Series seriesLine = new XYChart.Series();
                seriesLine.setName("Sightings");
                for (int i = 0; i < months; i++) {
                    seriesLine.getData().add(new XYChart.Data<>(monthYears[i], entries[i]));
                }

                ((LineChart) chart).getData().add(seriesLine);
                chart.setMaxSize(graphContainer.getWidth(), graphContainer.getHeight());
                graphContainer.getChildren().add(chart);
                break;
        }
    }

    private int getMonthDiff(Calendar d1, Calendar d2){
        final int MONTHS_IN_YEAR = 12;

        return (((d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR)) * MONTHS_IN_YEAR)
                + d2.get(Calendar.MONTH)) - d1.get(Calendar.MONTH);
    }
}
