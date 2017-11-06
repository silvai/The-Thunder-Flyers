package edu.gatech.thethunderflyers.android.controller;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.APIMessage;
import edu.gatech.thethunderflyers.android.model.Borough;
import edu.gatech.thethunderflyers.android.model.LocationType;
import edu.gatech.thethunderflyers.android.model.Model;
import edu.gatech.thethunderflyers.android.util.APIClient;
import edu.gatech.thethunderflyers.android.util.AlertDialogProvider;
import edu.gatech.thethunderflyers.android.util.AsyncHandler;
import edu.gatech.thethunderflyers.android.util.LocationProvider;
import edu.gatech.thethunderflyers.android.util.Navigator;
import edu.gatech.thethunderflyers.android.util.Validator;

public class ReportRatActivity extends AppCompatActivity implements AsyncHandler<APIMessage>,
        LocationProvider.LocationCallback {
    private Button report;
    private Button submit;
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
        report = (Button) findViewById(R.id.cancelReport);
        submit = (Button) findViewById(R.id.submitButton);
        address = (EditText) findViewById(R.id.address);
        city = (EditText) findViewById(R.id.city);
        zip = (EditText) findViewById(R.id.zip);
        lat = (EditText) findViewById(R.id.lat);
        longitude = (EditText) findViewById(R.id.longitude);
        locationType = (Spinner) findViewById(R.id.locationType);
        borough = (Spinner) findViewById(R.id.borough);

        ArrayAdapter<LocationType> adapterLT = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, LocationType.values());
        adapterLT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationType.setAdapter(adapterLT);
        ArrayAdapter<Borough> adapterB = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Borough.values());
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
        boolean isValid = Validator.validate(address, city, zip, lat, longitude);
        if (isValid) {
            int zi;
            double la, lo;
            String add = address.getText().toString();
            String cit = city.getText().toString();
            zi = Integer.parseInt(zip.getText().toString());
            la = Double.parseDouble(lat.getText().toString());
            lo = Double.parseDouble(longitude.getText().toString());
            LocationType lt = (LocationType) locationType.getSelectedItem();
            Borough bor = (Borough) borough.getSelectedItem();
            APIClient.getInstance().submitRatReport(Model.getRatData(lt, zi, cit, add, bor, la, lo),
                    new WeakReference<>(this));
        } else {
            Toast.makeText(this, "One or more fields invalid", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void handleResponse(APIMessage response, Exception ex) {
        if (ex != null) {
            AlertDialogProvider.getExceptionDialog(this).show();
        } else if (!response.isSuccess()) {
            AlertDialogProvider.getNotSuccessDialog(this, response.getMessage()).show();
        } else {
            Toast.makeText(this, "Successfully reported rat!", Toast.LENGTH_SHORT).show();
            Navigator.goToMainActivity(this);
        }
    }

    public void handleLocation(Location loc) {
        lat.setText(String.valueOf(loc.getLatitude()));
        longitude.setText(String.valueOf(loc.getLongitude()));
    }
}
