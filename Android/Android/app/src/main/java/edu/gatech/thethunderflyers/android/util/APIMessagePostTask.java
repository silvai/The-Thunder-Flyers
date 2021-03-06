package edu.gatech.thethunderflyers.android.util;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.gatech.thethunderflyers.android.model.APIMessage;

public class APIMessagePostTask extends AsyncTask<String, Void, APIMessage> {

    public HttpURLConnection getConnection() {
        return connection;
    }

    private HttpURLConnection connection;

    public BufferedReader getReader() {
        return reader;
    }

    private BufferedReader reader;

    public Exception getEx() {
        return ex;
    }

    public AsyncHandler<APIMessage> getAh() {
        return ah;
    }

    public String getUrl() {
        return url;
    }

    private Exception ex;
    private final AsyncHandler<APIMessage> ah;
    private final String url;

    public APIMessagePostTask(String url, AsyncHandler<APIMessage> ah) {
        this.ah = ah;
        this.url = url;
    }

    @Override
    protected APIMessage doInBackground(String... strings) {
        try {
            URL url = new URL(this.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            if (strings.length == 2) {
                connection.setRequestProperty("Authorization", "Bearer " + strings[1]);
            }

            Writer w = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(),
                    "UTF-8"));
            w.write(strings[0]);
            w.close();

            InputStream is = connection.getInputStream();
            StringBuilder sb = new StringBuilder();
            if (is == null) {
                return null;
            } else {
                reader = new BufferedReader(new InputStreamReader(is));
                String input;
                while((input = reader.readLine()) != null) {
                    sb.append(String.format("%s%n", input));
                }
                if (sb.length() == 0) {
                    return null;
                }
                Log.i("APIMessagePostTask", "Successful request");
                Gson gson = new Gson();
                return gson.fromJson(sb.toString(), APIMessage.class);
            }
        } catch (IOException e) {
            Log.e("APIMessagePostTask", e.getMessage());
            ex = e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("APIMessagePostTask", e.getMessage());
                    ex = e;
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(APIMessage message) {
        ah.handleResponse(message, ex);
    }
}
