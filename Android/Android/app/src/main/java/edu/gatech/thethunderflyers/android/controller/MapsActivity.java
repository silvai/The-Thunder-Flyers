package edu.gatech.thethunderflyers.android.controller;

import android.app.DatePickerDialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.RatData;
import edu.gatech.thethunderflyers.android.util.AsyncHandler;
import edu.gatech.thethunderflyers.android.util.DataGetTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback/*, AsyncHandler<List<RatData>> */{

    private GoogleMap mMap;
    private Button beginDate;
    private Button endDate;
    private Button searchReports;

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
                        beginDate.setText(String.format("%d/%d/%d", i1, i2, i));
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
                        endDate.setText(String.format("%d/%d/%d", i1, i2, i));
                    }

                }, year, month, day);
                dpd.getDatePicker().setMaxDate(new Date().getTime());
                dpd.show();
            }
        });
        searchReports = (Button) findViewById(R.id.searchReports);
    }

    public void search(View view) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date dateBegin = format.parse((String) beginDate.getText());
            Date dateEnd = format.parse((String) endDate.getText());
            if (dateBegin.compareTo(dateEnd) > 0) {
                Toast.makeText(this, "Dates invalid!", Toast.LENGTH_SHORT).show();
            } else {
                
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        new DataGetTask(getString(R.string.get_data_url), this).execute();
    }

//    @Override
//    public void handleResponse(List<RatData> response, Exception ex) {
//        for (RatData rd: response) {
//            MarkerOptions mo = new MarkerOptions()
//                    .position(new LatLng(rd.getLatitude(), rd.getLongitude()))
//                    .title(rd.getId() + "")
//                    .snippet(rd.getDate() + "\n"
//                    + rd.getLocatType());
//            mMap.addMarker(mo);
//        }
//    }

//
//    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
//
//        private final View myContentsView;
//
//        CustomInfoWindowAdapter() {
//            myContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
//        }
//
//        @Override
//        public View getInfoContents(Marker marker) {
//
//            TextView tvTitle = ((TextView) myContentsView.findViewById(R.id.title));
//            tvTitle.setText(marker.getTitle());
//            TextView tvSnippet = ((TextView) myContentsView.findViewById(R.id.snippet));
//            tvSnippet.setText(marker.getSnippet());
//
//            return myContentsView;
//        }
//
//        @Override
//        public View getInfoWindow(Marker marker) {
//            // TODO Auto-generated method stub
//            return null;
//        }
//    }
}
