package edu.gatech.thethunderflyers.android.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.gatech.thethunderflyers.android.R;

/**
 * LoadingActivity: an Activity to be shown while something is loading
 */
public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }
}
