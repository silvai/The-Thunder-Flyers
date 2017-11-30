package edu.gatech.thethunderflyers.ratapp.util;

import edu.gatech.thethunderflyers.ratapp.model.APIMessage;
import edu.gatech.thethunderflyers.ratapp.model.RatData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * APIClient: helper class to facilitate calling the API
 */
public final class APIClient {
    public static final APIClient API_CLIENT = new APIClient();

    private APIClient() {}

    /**
     * Executes the APIMessagePostTask with the API login url.
     * @param lu JSON representing the login user's credentials
     */
    public void login(String lu, AsyncHandler<APIMessage> handler) {
        APIMessagePostTask ampt = new APIMessagePostTask("http://localhost:3000/auth/login", lu, null);
        ampt.setOnSucceeded(event -> {
            handler.handleResponse((APIMessage) event.getSource().getValue());
        });
        new Thread(ampt).start();
    }

    /**
     * Executes the APIMessagePostTask with the API register url.
     * @param u JSON representing the user trying to register
     */
    public void register(String u, AsyncHandler<APIMessage> handler) {
        APIMessagePostTask ampt = new APIMessagePostTask("http://localhost:3000/auth/register", u, null);
        ampt.setOnSucceeded(event -> {
            handler.handleResponse((APIMessage) event.getSource().getValue());
        });
        new Thread(ampt).start();
    }

    /**
     * Executes the DataGetTask with the API getData (by date/id) url.
     * @param lastId the id of the last RatData in the previously returned list of RatData
     * @param millis the datetime (Unix epoch) of the last RatData in the previously returned list
     *               of RatData
     */
    public void getRatDataList(int lastId, long millis, String token, AsyncHandler<ObservableList<RatData>> handler) {
        final ObservableList<RatData> ratDataObservableList = FXCollections.observableArrayList();
        DataGetTask dgt = new DataGetTask("http://localhost:3000/data/" + lastId + "/" + millis, token);
        dgt.setOnSucceeded(event -> {
            ratDataObservableList.addAll((RatData[]) event.getSource().getValue());
            handler.handleResponse(ratDataObservableList);
        });
        new Thread(dgt).start();
    }

    /**
     * Executes the APIMessagePostTask with the API add rat report url.
     * @param rd JSON representing the new rat report
     */
    public void submitRatReport(String rd, String token, AsyncHandler<APIMessage> handler) {
        APIMessagePostTask ampt = new APIMessagePostTask("http://localhost:3000/data/add", rd, token);
        ampt.setOnSucceeded(event -> {
            handler.handleResponse((APIMessage) event.getSource().getValue());
        });
        new Thread(ampt).start();
    }

    /**
     * Executes the DataGetTask with the API getData (between dates) url.
     * @param beginDate the datetime (Unix epoch) of the beginning of the range
     * @param endDate the datetime (Unix epoch) of the end of the range
     */
    public void getRatDataDateRange(long beginDate, long endDate, String token, AsyncHandler<ObservableList<RatData>> handler) {
        final ObservableList<RatData> ratDataObservableList = FXCollections.observableArrayList();
        DataGetTask dgt = new DataGetTask("http://localhost:3000/data/search/" + beginDate + "/" + endDate, token);
        dgt.setOnSucceeded(event -> {
            ratDataObservableList.addAll((RatData[]) event.getSource().getValue());
            handler.handleResponse(ratDataObservableList);
        });
        new Thread(dgt).start();
    }
}
