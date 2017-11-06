package edu.gatech.thethunderflyers.android.controller;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.RatData;
import edu.gatech.thethunderflyers.android.util.APIClient;
import edu.gatech.thethunderflyers.android.util.AsyncHandler;
import edu.gatech.thethunderflyers.android.util.Navigator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, AsyncHandler<List<RatData>> {

    private GoogleMap mMap;
    private Button beginDate;
    private Button endDate;

    private final LatLng DEFAULT_LATLNG_ZOOM = new LatLng(40.7, -74.0);
    private final SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        beginDate = findViewById(R.id.beginDate);
        endDate = findViewById(R.id.endDate);
        beginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dp = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar c = new GregorianCalendar();
                        c.set(i,i1,i2);
                        Date d = c.getTime();
                        beginDate.setText(FULL_DATE_FORMAT.format(d));
                    }
                }, year, month, day);
                dp.getDatePicker().setMaxDate(new Date().getTime());
                dp.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar c = new GregorianCalendar();
                        c.set(i,i1,i2);
                        Date d = c.getTime();
                        endDate.setText(FULL_DATE_FORMAT.format(d));
                    }

                }, year, month, day);
                dpd.getDatePicker().setMaxDate(new Date().getTime());
                dpd.show();
            }
        });
    }

    public void search(View view) {
        try {
            Date dateBegin = FULL_DATE_FORMAT.parse((String) beginDate.getText());
            Date dateEnd = FULL_DATE_FORMAT.parse((String) endDate.getText());
            if ((dateBegin.compareTo(dateEnd) > 0) || (dateEnd == null)) {
                Toast.makeText(this, "Dates invalid!", Toast.LENGTH_SHORT).show();
            } else {
                APIClient.getInstance().getRatDataDateRange(dateBegin.getTime(), dateEnd.getTime(), new WeakReference<>(this));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float DEFAULT_FLOAT_ZOOM = 6.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LATLNG_ZOOM, DEFAULT_FLOAT_ZOOM));
    }

    /**
     * Button action to go to main screen
     * @param view the call back parameter
     */
    public void mainScreen(View view) {
        Navigator.goToMainActivity(this);
    }

    @Override
    public void handleResponse(List<RatData> response, Exception ex) {
        mMap.clear();
        for (RatData rd: response) {
            mMap.addMarker(rd.getMapMarkerOptions());
        }
    }
}
