package edu.gatech.thethunderflyers.android.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.Borough;
import edu.gatech.thethunderflyers.android.model.LocationType;
import edu.gatech.thethunderflyers.android.model.RatData;
import edu.gatech.thethunderflyers.android.util.Navigator;

/**
 * DetailRatDataActivity: Activity for seeing more information about a piece of rat data
 */
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
        Intent provided = getIntent();
        RatData rd = (RatData) provided.getSerializableExtra("rat");
        date = findViewById(R.id.dataDate);
        address = findViewById(R.id.dataAddress);
        zip = findViewById(R.id.dataZip);
        city = findViewById(R.id.dataCity);
        locationT = findViewById(R.id.dataLocationT);
        borough = findViewById(R.id.dataBorough);
        latLong = findViewById(R.id.dataLatLong);
        setDateField(rd);
        setAddressField(rd);
        setBoroughField(rd);
        setCityField(rd);
        setLatLongField(rd);
        setLocationTypeField(rd);
        setZipField(rd);
    }

    private void setDateField(RatData rd) {
        Date rdDate = rd.getDate();
        date.setText(String.format(Locale.US, "Date of Report%n%s", rdDate.toString()));
    }

    private void setAddressField(RatData rd) {
        address.setText(String.format(Locale.US, "Address%n%s", rd.getAddress()));
    }

    private void setZipField(RatData rd) {
        zip.setText(String.format(Locale.US, "Zip%n%d", rd.getZip()));
    }

    private void setCityField(RatData rd) {
        city.setText(String.format(Locale.US, "City%n%s", rd.getCity()));
    }

    private void setLocationTypeField(RatData rd) {
        LocationType rdLocationType = rd.getLocationType();
        locationT.setText(String.format(Locale.US, "Location Type%n%s", rdLocationType));
    }

    private void setBoroughField(RatData rd) {
        Borough rdBorough = rd.getBorough();
        borough.setText(String.format(Locale.US, "Borough%n%s", rdBorough));
    }

    private void setLatLongField(RatData rd) {
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
