package edu.gatech.thethunderflyers.android.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.APIMessage;
import edu.gatech.thethunderflyers.android.model.Model;
import static edu.gatech.thethunderflyers.android.util.APIClient.API_CLIENT;

import edu.gatech.thethunderflyers.android.util.AsyncHandler;
import edu.gatech.thethunderflyers.android.util.ExceptionAlertDialog;
import edu.gatech.thethunderflyers.android.util.Navigator;
import edu.gatech.thethunderflyers.android.util.NotSuccessAlertDialog;
import edu.gatech.thethunderflyers.android.util.Validator;

/**
 * LoginActivity: Activity to allow users to login to the application.
 */
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
        Validator validator = new Validator(username, password);
        boolean isValid = validator.validate();
        if (isValid) {
            Editable userEditable = username.getText();
            Editable passEditable = password.getText();
            String user = userEditable.toString();
            String pass = passEditable.toString();
            API_CLIENT.login(Model.getLoginUser(user, pass), new WeakReference<>(this));
        } else {
            Toast t = Toast.makeText(this, "One or more fields is invalid!", Toast.LENGTH_SHORT);
            t.show();
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
        if (ex != null) {
            ExceptionAlertDialog ead = new ExceptionAlertDialog(ex, this);
            ead.show();
        } else {
            String message = response.getMessage();
            if (response.isSuccess()) {
                NotSuccessAlertDialog nsad = new NotSuccessAlertDialog(message, this);
                nsad.show();
            } else {
                userId = Integer.parseInt(message);
                Navigator.goToMapsActivity(this);
            }
        }
    }
}
