package edu.gatech.thethunderflyers.ratapp.util;

import com.google.gson.Gson;
import edu.gatech.thethunderflyers.ratapp.model.RatData;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataGetTask extends Task<RatData[]> {

    private HttpURLConnection connection;
    private BufferedReader reader;

    private final String url;
    private final String token;

    public DataGetTask(String url, String token) {
        this.token = token;
        this.url = url;
    }

    @Override
    protected RatData[] call() throws Exception {
        try {
            URL url = new URL(this.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            InputStream is = connection.getInputStream();
            StringBuilder sb = new StringBuilder();
            if (is == null) {
                return null;
            } else {
                reader = new BufferedReader(new InputStreamReader(is));
                String input;
                while ((input = reader.readLine()) != null) {
                    sb.append(String.format("%s%n", input));
                }
                if (sb.length() == 0) {
                    return null;
                }
                Gson gson = new Gson();
                return gson.fromJson(sb.toString(), RatData[].class);
            }
        } catch (IOException e) {
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    reader = null;
                }
            }
        }
    }
}

