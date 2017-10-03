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
import java.net.MalformedURLException;
import java.net.URL;

import edu.gatech.thethunderflyers.android.model.APIMessage;

public class RegisterTask extends AsyncTask<String, Void, APIMessage> {
    private final String POST_URL = "http://10.0.2.2:3000/auth/register";

    private HttpURLConnection regConnection;
    private BufferedReader reader;
    private Exception ex;
    private AsyncHandler<APIMessage> ah;

    public RegisterTask(AsyncHandler<APIMessage> ah) {
        this.ah = ah;
    }

    // strings[0] MUST be the correct JSON data
    @Override
    protected APIMessage doInBackground(String... strings) {
        try {
            URL url = new URL(POST_URL);
            regConnection = (HttpURLConnection) url.openConnection();
            regConnection.setDoOutput(true);
            regConnection.setDoInput(true);
            regConnection.setRequestMethod("POST");
            regConnection.setRequestProperty("Content-Type", "application/json");
            regConnection.setRequestProperty("Accept", "application/json");

            Writer w = new BufferedWriter(new OutputStreamWriter(regConnection.getOutputStream(), "UTF-8"));
            w.write(strings[0]);
            w.close();

            InputStream is = regConnection.getInputStream();
            StringBuilder sb = new StringBuilder();
            if (is == null) {
                return null;
            } else {
                reader = new BufferedReader(new InputStreamReader(is));
                String input;
                while((input = reader.readLine()) != null) {
                    sb.append(input).append("\n");
                }
                if (sb.length() == 0) {
                    return null;
                }
                return new Gson().fromJson(sb.toString(), APIMessage.class);
            }
        } catch (IOException e) {
            Log.e("RegisterTask", e.getMessage());
            ex = e;
        } finally {
            if (regConnection != null) {
                regConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("RegisterTask", e.getMessage());
                    ex = e;
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(APIMessage am) {
        ah.handleResponse(am, ex);
    }
}
