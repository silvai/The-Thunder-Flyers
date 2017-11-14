package edu.gatech.thethunderflyers.android;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;
import android.os.AsyncTask;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.List;

import edu.gatech.thethunderflyers.android.model.RatData;
import edu.gatech.thethunderflyers.android.util.AsyncHandler;
import edu.gatech.thethunderflyers.android.util.DataGetTask;

import static org.junit.Assert.assertEquals;

/**
 * J-unit tests for doInBackground(String... strings) from the dataGetTask class
 * Created by drew on 11/13/2017.
 */

public class dataGetTask_doInBackground_Test {
    /**
     * checks if doInBackground() throws an exception
     * @throws Exception in order to satisfy checked exception
     */
    @Test
    public void throwsException() throws Exception {
        DataGetTask badURL = new DataGetTask("www.google.com", new AsyncHandler<List<RatData>>() {
            @Override
            public void handleResponse(List<RatData> response, Exception ex) {

            }
        });
        IOException e = new IOException("blahhhhh");
        assertEquals(e, null);
    }

    /**
     * checks if the connection is closed
     * @throws Exception in order to satisfy checked exception
     */
    public void connectionClosed() throws Exception {
        HttpURLConnection userConn;
        HttpURLConnection actualConn;
        DataGetTask connectionStatus = new DataGetTask("", new AsyncHandler<List<RatData>>() {
            @Override
            public void handleResponse(List<RatData> response, Exception ex) {

            }
        });
        URL actURL = new URL("www.google.com");
        URL url = new URL(connectionStatus.getURL());
        userConn = (HttpURLConnection) url.openConnection();
        actualConn = (HttpURLConnection)   actURL.openConnection();
        assertEquals(userConn, actualConn);
    }

    /**
     * checks if the URL is valid
     * @throws Exception in order to satisfy the checked exception
     */
    public void qualityURL() throws Exception {
        DataGetTask badURL = new DataGetTask("", new AsyncHandler<List<RatData>>() {
            @Override
            public void handleResponse(List<RatData> response, Exception ex) {

            }
        });
        assertEquals(null, badURL.getURL());
    }
}
