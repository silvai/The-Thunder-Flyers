package edu.gatech.thethunderflyers.ratapp.util;

import com.google.gson.Gson;
import edu.gatech.thethunderflyers.ratapp.model.APIMessage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.concurrent.Task;

public class APIMessagePostTask extends Task<APIMessage> {
    private HttpURLConnection connection;
    private BufferedReader reader;

    private final String url;
    private final String token;
    private final String body;

    APIMessagePostTask(String url, String body, String token) {
        this.token = token;
        this.body = body;
        this.url = url;
    }

    @Override
    protected APIMessage call() throws Exception {
        try {
            URL url = new URL(this.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            if (token != null) {
                connection.setRequestProperty("Authorization", "Bearer " + token);
            }

            Writer w = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(),
                    "UTF-8"));
            w.write(this.body);
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
                Gson gson = new Gson();
                return gson.fromJson(sb.toString(), APIMessage.class);
            }
        } catch (IOException e) {
            APIMessage am = new APIMessage();
            am.setSuccess(false);
            am.setMessage("Exception occurred while processing: " + e.getMessage());
            return am;
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
