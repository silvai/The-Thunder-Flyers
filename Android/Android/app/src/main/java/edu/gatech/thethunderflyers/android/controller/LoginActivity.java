package edu.gatech.thethunderflyers.android.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import edu.gatech.thethunderflyers.android.util.FormValidator;
import edu.gatech.thethunderflyers.android.util.Navigator;

public class LoginActivity extends AppCompatActivity implements AsyncHandler<APIMessage> {
    private EditText username;
    private EditText password;

    public static int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        FormValidator usernameFV = new FormValidator(username, "Username");
        FormValidator passwordFV = new FormValidator(password, "Password");

        username.addTextChangedListener(usernameFV);
        username.setOnFocusChangeListener(usernameFV);
        password.addTextChangedListener(passwordFV);
        password.setOnFocusChangeListener(passwordFV);
    }

    /**
     * handles the submit button action
     * @param v the call back parameter
     */
    public void submit(View v) {

        String user = username.getText().toString();
        String pass = password.getText().toString();

        boolean isValid = username.getError() == null && password.getError() == null
                && !TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass);

        if (isValid) {
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
        if (ex != null) {
            AlertDialogProvider.getExceptionDialog(this).show();
        } else if (!response.isSuccess()) {
            AlertDialogProvider.getNotSuccessDialog(this, response.getMessage()).show();
        } else {
            userId = Integer.parseInt(response.getMessage());
            Navigator.goToMapsActivity(this);
        }
    }
}
