package edu.gatech.thethunderflyers.android.util;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import edu.gatech.thethunderflyers.android.model.RatData;

public class DataGetTask extends AsyncTask<String, Void, List<RatData>> {

    private HttpURLConnection connection;
    private BufferedReader reader;
    private Exception ex;
    private final AsyncHandler<List<RatData>> ah;
    private final String url;

    DataGetTask(String url, AsyncHandler<List<RatData>> ah) {
        this.ah = ah;
        this.url = url;
    }

    @Override
    protected List<RatData> doInBackground(String... strings) {
        try {
            URL url = new URL(this.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000);
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
                    sb.append(String.format("%s%n", input));
                }
                if (sb.length() == 0) {
                    return null;
                }
                Log.i("DataGetTask", "Successful request");
                TypeToken<List<RatData>> t = new TypeToken<List<RatData>>(){};
                Type type = t.getType();
                Gson gson = new Gson();
                return gson.fromJson(sb.toString(), type);
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
    protected void onPostExecute(List<RatData> ratData) {
        ah.handleResponse(ratData, ex);
    }
}

