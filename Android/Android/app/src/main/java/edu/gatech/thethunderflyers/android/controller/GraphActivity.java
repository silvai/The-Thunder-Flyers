package edu.gatech.thethunderflyers.android.controller;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.RatData;
import edu.gatech.thethunderflyers.android.util.APIClient;
import edu.gatech.thethunderflyers.android.util.AsyncHandler;
import edu.gatech.thethunderflyers.android.util.Navigator;

public class GraphActivity extends AppCompatActivity implements AsyncHandler<List<RatData>> {
    private GraphView graph;
    private Button begin;
    private Button end;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        graph = (GraphView) findViewById(R.id.graph);
        submit = (Button) findViewById(R.id.submitDates);
        begin = (Button) findViewById(R.id.beginDate);
        end = (Button) findViewById(R.id.endDate);
        begin.setOnClickListener(new View.OnClickListener() {
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
                        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                        begin.setText(format.format(d));
                    }
                }, year, month, day);
                dp.getDatePicker().setMaxDate(new Date().getTime());
                dp.show();
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
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
                        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                        end.setText(format.format(d));
                    }

                }, year, month, day);
                dpd.getDatePicker().setMaxDate(new Date().getTime());
                dpd.show();
            }
        });

    }

    /**
     * The button activity to get a response for the date range
     * to view the graph
     * @param view the callback parameter
     */
    public void submitDates(View view) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date dateBegin = format.parse((String) begin.getText());
            Date dateEnd = format.parse((String) end.getText());
            if (dateBegin.compareTo(dateEnd) > 0 || dateBegin == null ||dateEnd == null) {
                Toast.makeText(this, "Dates invalid!", Toast.LENGTH_SHORT).show();
            } else {
                APIClient.getInstance().getRatDataDateRangeGraph(dateBegin.getTime(), dateEnd.getTime(), new WeakReference<>(this));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * The button action to go back to main activity
     * @param view the call back parameter
     */
    public void main(View view) {
        Navigator.goToMainActivity(this);
    }

    @Override
    public void handleResponse(List<RatData> response, Exception ex) {
        //list of dates
        //put into series
        //for the same mm/dd/yyyy then increment x value

//        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
//          new DataPoint(0, 1),
//          new DataPoint(1, 5),
//          new DataPoint(2, 3),
//          new DataPoint(3, 2),
//          new DataPoint(4, 6)
//        });
//        graph.addSeries(series);
    }
}
