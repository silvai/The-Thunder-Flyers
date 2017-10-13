package edu.gatech.thethunderflyers.android.controller;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.APIMessage;
import edu.gatech.thethunderflyers.android.model.Borough;
import edu.gatech.thethunderflyers.android.model.LocationType;
import edu.gatech.thethunderflyers.android.model.RatData;
import edu.gatech.thethunderflyers.android.util.APIMessagePostTask;
import edu.gatech.thethunderflyers.android.util.FormValidator;
import edu.gatech.thethunderflyers.android.util.AsyncHandler;

public class ReportRatActivity extends AppCompatActivity implements AsyncHandler<APIMessage> {
    private Button report;
    private Button submit;
    private EditText address;
    private EditText city;
    private EditText zip;
    private EditText lat;
    private EditText longitude;
    private Spinner locatType;
    private Spinner boro;

    private FusedLocationProviderClient client;
    private Location location;

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
        locatType = (Spinner) findViewById(R.id.locatType);
        boro = (Spinner) findViewById(R.id.borough);

        ArrayAdapter<LocationType> adapterLT = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, LocationType.values());
        adapterLT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locatType.setAdapter(adapterLT);
        ArrayAdapter<Borough> adapterB = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Borough.values());
        adapterB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boro.setAdapter(adapterB);

        FormValidator add = new FormValidator(address, "Address");
        FormValidator cit = new FormValidator(city, "City");
        FormValidator zipCode = new FormValidator(zip, "ZipCode");
        FormValidator latitude = new FormValidator(lat, "Latitude");
        FormValidator longi = new FormValidator(longitude, "Longitude");

        address.addTextChangedListener(add);
        address.setOnFocusChangeListener(add);
        city.addTextChangedListener(cit);
        city.setOnFocusChangeListener(cit);
        zip.addTextChangedListener(zipCode);
        zip.setOnFocusChangeListener(zipCode);
        lat.addTextChangedListener(latitude);
        lat.setOnFocusChangeListener(latitude);
        longitude.addTextChangedListener(longi);
        longitude.setOnFocusChangeListener(longi);

        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                ReportRatActivity.this.location = location;
            }
        });
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void submit(View view) {
        String add = address.getText().toString();
        String cit = city.getText().toString();
        int zi = 0;
        double la = 0.0, lo = 0.0;
        if (!TextUtils.isDigitsOnly(zip.getText().toString()) || TextUtils.isEmpty(zip.getText().toString())) {
            zip.setError("Must enter number");
        } else {
            zi = Integer.parseInt(zip.getText().toString());
        }
        if (!TextUtils.isDigitsOnly(lat.getText().toString()) || TextUtils.isEmpty(lat.getText().toString())) {
            lat.setError("Must enter number");
        } else {
            la = Double.parseDouble(lat.getText().toString());
        }
        if (!TextUtils.isDigitsOnly(longitude.getText().toString()) || TextUtils.isEmpty(longitude.getText().toString())) {
            longitude.setError("Must enter number");
        } else {
            lo = Double.parseDouble(longitude.getText().toString());
        }
        LocationType lt = (LocationType) locatType.getSelectedItem();
        Borough bor = (Borough) boro.getSelectedItem();

        boolean isValid = address.getError() == null && city.getError() == null
                && zip.getError() == null && lat.getError() == null
                && longitude.getError() == null && !TextUtils.isEmpty(add)
                && !TextUtils.isEmpty(cit) && !TextUtils.isEmpty(zip.getText().toString())
                && !TextUtils.isEmpty(lat.getText().toString())
                && !TextUtils.isEmpty(longitude.getText().toString());

        RatData rd = new RatData(lt, zi, cit, add, bor, la, lo);

        if (isValid) {
            new APIMessagePostTask(getString(R.string.post_rat_data_url), this).execute(new Gson().toJson(rd));
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "One or more fields invalid", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void handleResponse(APIMessage response, Exception ex) {
        if (ex != null) {
            AlertDialog ad = new AlertDialog.Builder(this)
                    .setMessage("An unexpected error occurred. Please try again later.")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create();
            ad.show();
        } else if (!response.isSuccess()) {
            AlertDialog ad = new AlertDialog.Builder(this)
                    .setMessage(response.getMessage())
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create();
            ad.show();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
