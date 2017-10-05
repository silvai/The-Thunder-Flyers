package edu.gatech.thethunderflyers.android.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.util.AsyncHandler;

public class MainActivity extends AppCompatActivity implements AsyncHandler<RatData[]> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Handles logout button click.
     * @param view the callback parameter
     */
    public void logout(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void handleResponse(RatData[] response, Exception ex) {

    }
}
