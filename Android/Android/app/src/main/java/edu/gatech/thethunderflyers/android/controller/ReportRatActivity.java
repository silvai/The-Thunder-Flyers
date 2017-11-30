package edu.gatech.thethunderflyers.android.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.APIMessage;
import edu.gatech.thethunderflyers.android.model.Borough;
import edu.gatech.thethunderflyers.android.model.LocationType;
import edu.gatech.thethunderflyers.android.model.Model;
import edu.gatech.thethunderflyers.android.util.AsyncHandler;
import edu.gatech.thethunderflyers.android.util.ExceptionAlertDialog;
import edu.gatech.thethunderflyers.android.util.LocationProvider;
import edu.gatech.thethunderflyers.android.util.Navigator;
import edu.gatech.thethunderflyers.android.util.NotSuccessAlertDialog;
import edu.gatech.thethunderflyers.android.util.Validator;

import static edu.gatech.thethunderflyers.android.util.APIClient.API_CLIENT;

/**
 * This is the activity to create a new rat report
 */
public class ReportRatActivity extends AppCompatActivity implements AsyncHandler<APIMessage>,
        LocationProvider.LocationCallback {
    private EditText address;
    private EditText city;
    private EditText zip;
    private EditText lat;
    private EditText longitude;
    private Spinner locationType;
    private Spinner borough;

    private LocationProvider lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_rat);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        zip = findViewById(R.id.zip);
        lat = findViewById(R.id.lat);
        longitude = findViewById(R.id.longitude);
        locationType = findViewById(R.id.locationType);
        borough = findViewById(R.id.borough);

        ArrayAdapter<LocationType> adapterLT = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, LocationType.values());
        adapterLT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationType.setAdapter(adapterLT);
        ArrayAdapter<Borough> adapterB = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Borough.values());
        adapterB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        borough.setAdapter(adapterB);

        lp = new LocationProvider(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lp.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        lp.disconnect();
    }

    /**
     * Handles the cancel button click
     * @param view the call back parameter
     */
    public void cancel(View view) {
        Navigator.goToMainActivity(this);
    }

    /**
     * handles the submit button click
     * @param view the call back parameter
     */
    public void submit(View view) {
        Validator validator = new Validator(address, city, zip, lat, longitude);
        boolean isValid = validator.validate();
        if (isValid) {
            int zi;
            double la;
            double lo;

            Editable addressEditable = address.getText();
            Editable cityEditable = city.getText();
            Editable zipEditable = zip.getText();
            Editable latEditable = lat.getText();
            Editable longitudeEditable = longitude.getText();

            String add = addressEditable.toString();
            String cit = cityEditable.toString();
            zi = Integer.parseInt(zipEditable.toString());
            la = Double.parseDouble(latEditable.toString());
            lo = Double.parseDouble(longitudeEditable.toString());
            LocationType lt = (LocationType) locationType.getSelectedItem();
            Borough bor = (Borough) borough.getSelectedItem();

            SharedPreferences sp = getSharedPreferences(getString(R.string.userid_file),
                    Context.MODE_PRIVATE);
            int userid = sp.getInt(getString(R.string.userid_field), 0);

            SharedPreferences sp2 = getSharedPreferences(getString(R.string.token_file),
                    Context.MODE_PRIVATE);
            String token = sp2.getString(getString(R.string.token_field), "");

            API_CLIENT.submitRatReport(Model.getRatData(lt, zi, cit, add, bor, la, lo, userid),
                    new WeakReference<>(this), token);
        } else {
            Toast t = Toast.makeText(this, "One or more fields invalid", Toast.LENGTH_SHORT);
            t.show();
        }
    }

    @Override
    public void handleResponse(APIMessage response, Exception ex) {
        if (ex != null) {
            ExceptionAlertDialog ead = new ExceptionAlertDialog(ex, this);
            ead.show();
        } else if (response.isSuccess()) {
            String message = response.getMessage();
            NotSuccessAlertDialog notSuccessAlertDialog = new NotSuccessAlertDialog(message, this);
            notSuccessAlertDialog.show();
        } else {
            Toast t = Toast.makeText(this, "Successfully reported rat!", Toast.LENGTH_SHORT);
            t.show();
            Navigator.goToMainActivity(this);
        }
    }

    @Override
    public void handleLocation(Location loc) {
        lat.setText(String.valueOf(loc.getLatitude()));
        longitude.setText(String.valueOf(loc.getLongitude()));
    }
}
