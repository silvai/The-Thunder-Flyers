package edu.gatech.thethunderflyers.android.controller;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.RatData;

public class DetailRatDataActivity extends AppCompatActivity {
    private RatData rd;
    private Button cancel;
    private TextView date;
    private TextView address;
    private TextView zip;
    private TextView city;
    private TextView locationT;
    private TextView boro;
    private TextView latlong;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rat_data);
        cancel = (Button) findViewById(R.id.cancelButton);
        rd = (RatData) getIntent().getSerializableExtra("rat");
        date = (TextView) findViewById(R.id.dataDate);
        date.setText("Date of Report\n" + rd.getDate().toString());
        address = (TextView) findViewById(R.id.dataAddress);
        address.setText("Address\n " + rd.getAddress());
        zip = (TextView) findViewById(R.id.dataZip);
        zip.setText("Zip\n" + rd.getZip());
        city = (TextView) findViewById(R.id.dataCity);
        city.setText("City \n" + rd.getCity());
        locationT = (TextView) findViewById(R.id.dataLocationT);
        locationT.setText("Location Type \n" + rd.getLocatType().toString());
        boro = (TextView) findViewById(R.id.dataBorough);
        boro.setText("Borough\n" + rd.getBorough().toString());

        String lat = String.format("%.3f", rd.getLatitude());
        String longi = String.format("%.3f", rd.getLongitude());
        latlong = (TextView) findViewById(R.id.dataLatLong);
        latlong.setText("Latitude, Longitude\n" + lat + ", " + longi);

    }
    public void cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
