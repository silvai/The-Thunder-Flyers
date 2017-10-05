package edu.gatech.thethunderflyers.android.util;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import edu.gatech.thethunderflyers.android.model.RatData;

public class DataGetTask extends AsyncTask<String, Void, RatData[]> {

    private HttpURLConnection connection;
    private BufferedReader reader;
    private Exception ex;
    private AsyncHandler<RatData[]> ah;
    private final String url;

    public DataGetTask(String url, AsyncHandler<RatData[]> ah) {
        this.ah = ah;
        this.url = url;
    }

    @Override
    protected RatData[] doInBackground(String... strings) {
        try {
            URL url = new URL(this.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            InputStream is = connection.getInputStream();
            StringBuilder sb = new StringBuilder();
            if (is == null) {
                return null;
            } else {
                reader = new BufferedReader(new InputStreamReader(is));
                String input;
                while ((input = reader.readLine()) != null) {
                    sb.append(input).append("\n");
                }
                if (sb.length() == 0) {
                    return null;
                }
                Log.i("DataGetTask", "Successful request");
                return new Gson().fromJson(sb.toString(), RatData[].class);
            }
        } catch (IOException e) {
            Log.e("DataGetTask", e.getMessage());
            ex = e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("DataGetTask", e.getMessage());
                    ex = e;
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(RatData[] ratData) {
        ah.handleResponse(ratData, ex);
    }
}

