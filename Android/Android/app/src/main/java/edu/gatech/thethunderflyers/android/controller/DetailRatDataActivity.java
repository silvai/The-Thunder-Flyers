package edu.gatech.thethunderflyers.android.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.RatData;
import edu.gatech.thethunderflyers.android.util.Navigator;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rat_data);
        cancel = (Button) findViewById(R.id.cancelButton);
        rd = (RatData) getIntent().getSerializableExtra("rat");

        date = (TextView) findViewById(R.id.dataDate);
        date.setText(String.format(Locale.US, "Date of Report%n%s", rd.getDate().toString()));

        address = (TextView) findViewById(R.id.dataAddress);
        address.setText(String.format(Locale.US, "Address%n%s", rd.getAddress()));

        zip = (TextView) findViewById(R.id.dataZip);
        zip.setText(String.format(Locale.US, "Zip%n%d", rd.getZip()));

        city = (TextView) findViewById(R.id.dataCity);
        city.setText(String.format(Locale.US, "City%n%s", rd.getCity()));

        locationT = (TextView) findViewById(R.id.dataLocationT);
        locationT.setText(String.format(Locale.US, "Location Type%n%s", rd.getLocatType().toString()));

        boro = (TextView) findViewById(R.id.dataBorough);
        boro.setText(String.format(Locale.US, "Borough%n%s", rd.getBorough().toString()));

        latlong = (TextView) findViewById(R.id.dataLatLong);
        latlong.setText(String.format(Locale.US, "Latitude, Longitude%n(%.3f, %.3f)",
                rd.getLatitude(), rd.getLongitude()));
    }

    /**
     * handles the cancel button click
     * @param view the call back parameter
     */
    public void cancel(View view) {
        Navigator.goToMainActivity(this);
    }
}
