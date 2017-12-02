package edu.gatech.thethunderflyers.ratapp.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navigator {
    public void navigate(String resource, String title, Stage currentWindow) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(resource));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle(title);
            stage.setResizable(false);
            currentWindow.close();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
