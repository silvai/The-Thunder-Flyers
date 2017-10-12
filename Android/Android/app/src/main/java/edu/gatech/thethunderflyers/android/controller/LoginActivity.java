package edu.gatech.thethunderflyers.android.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.APIMessage;
import edu.gatech.thethunderflyers.android.model.LoginUser;
import edu.gatech.thethunderflyers.android.util.APIMessagePostTask;
import edu.gatech.thethunderflyers.android.util.AsyncHandler;
import edu.gatech.thethunderflyers.android.util.FormValidator;

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

    public void submit(View v) {

        String user = username.getText().toString();
        String pass = password.getText().toString();

        boolean isValid = username.getError() == null && password.getError() == null
                && !TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass);

        if (isValid) {
            LoginUser lu = new LoginUser(user, pass);
            new APIMessagePostTask(getString(R.string.login_url), this).execute(new Gson().toJson(lu));
        } else {
            Toast.makeText(this, "One or more fields is invalid!", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel(View v) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void handleResponse(APIMessage response, Exception ex) {
        if (ex != null) {
            AlertDialog ad = new AlertDialog.Builder(this)
                    .setMessage("An unexpected error occurred. Please try again later.")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create();
            ad.show();
        } else if (!response.isSuccess()) {
            AlertDialog ad = new AlertDialog.Builder(this)
                    .setMessage(response.getMessage())
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create();
            ad.show();
        } else {
            userId = Integer.parseInt(response.getMessage());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
