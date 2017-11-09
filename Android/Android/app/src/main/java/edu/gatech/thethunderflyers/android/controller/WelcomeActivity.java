package edu.gatech.thethunderflyers.android.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.util.Navigator;

/**
 * WelcomeActivity: the first Activity of the application.
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
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
