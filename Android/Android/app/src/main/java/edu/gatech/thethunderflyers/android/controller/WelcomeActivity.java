package edu.gatech.thethunderflyers.android.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.util.Navigator;

public class WelcomeActivity extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
    }

    /**
     * Handles login button click.
     * @param view the callback parameter
     */
    public void login(View view) {
        Navigator.goToLoginActivity(this);
    }

    /**
     * Handles register button click.
     * @param view the callback parameter
     */
    public void register(View view) {
        Navigator.goToRegisterActivity(this);
    }
}
