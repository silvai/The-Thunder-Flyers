package edu.gatech.thethunderflyers.android.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.gatech.thethunderflyers.android.R;

public class WelcomeActivity extends AppCompatActivity {

    private Button loginbutton;
    private Button registerbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        loginbutton = (Button) findViewById(R.id.loginbutton);
        registerbutton = (Button) findViewById(R.id.registerButton);
    }

    /**
     * Handles login button click.
     * @param view the callback parameter
     */
    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Handles register button click.
     * @param view the callback parameter
     */
    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
