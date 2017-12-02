package edu.gatech.thethunderflyers.ratapp.controller;

import edu.gatech.thethunderflyers.ratapp.model.Graphs;
import edu.gatech.thethunderflyers.ratapp.util.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Chart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphActivity implements Initializable{
    private Chart chart;
    private Navigator navigator = new Navigator();
    @FXML
    private ChoiceBox<Graphs> graphBox;
    @FXML
    private Button mainMenuButton;

    public void graph() {

        Graphs selected = graphBox.getSelectionModel().getSelectedItem();
        switch (selected) {
//            case BAR_CHART: chart = new BarChart<>();
//            break;
//            case PIE_CHART: chart = new PieChart();
//            break;
//            case LINE_CHART: chart = new LineChart<>();
//            break;
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
    }
}
