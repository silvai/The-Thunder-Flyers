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
    private TextView date;
    private TextView address;
    private TextView zip;
    private TextView city;
    private TextView locationT;
    private TextView borough;
    private TextView latLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rat_data);
        Button cancel = (Button) findViewById(R.id.cancelButton);
        RatData rd = (RatData) getIntent().getSerializableExtra("rat");
        setFields(rd);

        date = (TextView) findViewById(R.id.dataDate);
        address = (TextView) findViewById(R.id.dataAddress);
        zip = (TextView) findViewById(R.id.dataZip);
        city = (TextView) findViewById(R.id.dataCity);
        locationT = (TextView) findViewById(R.id.dataLocationT);
        borough = (TextView) findViewById(R.id.dataBorough);
        latLong = (TextView) findViewById(R.id.dataLatLong);
    }

    private void setFields(RatData rd) {
        date.setText(String.format(Locale.US, "Date of Report%n%s", rd.getDate().toString()));
        address.setText(String.format(Locale.US, "Address%n%s", rd.getAddress()));
        zip.setText(String.format(Locale.US, "Zip%n%d", rd.getZip()));
        city.setText(String.format(Locale.US, "City%n%s", rd.getCity()));
        locationT.setText(String.format(Locale.US, "Location Type%n%s", rd.getLocationType().toString()));
        borough.setText(String.format(Locale.US, "Borough%n%s", rd.getBorough().toString()));
        latLong.setText(String.format(Locale.US, "Latitude, Longitude%n(%.3f, %.3f)",
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
