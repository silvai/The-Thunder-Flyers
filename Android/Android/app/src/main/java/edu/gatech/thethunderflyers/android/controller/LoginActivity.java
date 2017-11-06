package edu.gatech.thethunderflyers.android.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.APIMessage;
import edu.gatech.thethunderflyers.android.model.Model;
import edu.gatech.thethunderflyers.android.util.APIClient;
import edu.gatech.thethunderflyers.android.util.AlertDialogProvider;
import edu.gatech.thethunderflyers.android.util.AsyncHandler;
import edu.gatech.thethunderflyers.android.util.Navigator;
import edu.gatech.thethunderflyers.android.util.Validator;

public class LoginActivity extends AppCompatActivity implements AsyncHandler<APIMessage> {
    private EditText username;
    private EditText password;

    public static int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    /**
     * handles the submit button action
     * @param v the call back parameter
     */
    public void submit(View v) {
        boolean isValid = Validator.validate(username, password);
        if (isValid) {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            APIClient.getInstance().login(Model.getLoginUser(user, pass), new WeakReference<>(this));
        } else {
            Toast.makeText(this, "One or more fields is invalid!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handles the cancel button action
     * @param v the call back parameter
     */
    public void cancel(View v) {
        Navigator.goToWelcomeActivity(this);
    }

    @Override
    public void handleResponse(APIMessage response, Exception ex) {
        String message = response.getMessage();
        if (ex != null) {
            AlertDialogProvider.getExceptionDialog(this).show();
        } else if (!response.isSuccess()) {
            AlertDialogProvider.getNotSuccessDialog(this, message).show();
        } else {
            userId = Integer.parseInt(message);
            Navigator.goToMapsActivity(this);
        }
    }
}
