package edu.gatech.thethunderflyers.android.controller;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    private Button begin;
    private Button end;
    private Button submit;
    private BarChart graph;

    private Date beginDate;
    private Date endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        graph = (BarChart) findViewById(R.id.graph);
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
            Calendar c1 = Calendar.getInstance();
            c1.setTime(dateBegin);
            c1.set(Calendar.DATE, c1.getActualMinimum(Calendar.DATE));
            dateBegin = c1.getTime();
            Date dateEnd = format.parse((String) end.getText());
            Calendar c2= Calendar.getInstance();
            c2.setTime(dateEnd);
            c2.set(Calendar.DATE, c2.getActualMaximum(Calendar.DATE));
            dateEnd = c2.getTime();
            if (dateBegin.compareTo(dateEnd) > 0 || dateBegin == null ||dateEnd == null) {
                Toast.makeText(this, "Dates invalid!", Toast.LENGTH_SHORT).show();
            } else {
                beginDate = dateBegin;
                endDate = dateEnd;
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
        if (response.size() == 0) {
            return;
        }

        final Calendar d1 = Calendar.getInstance();
        final Calendar d2 = Calendar.getInstance();

        d1.setTime(beginDate);
        d2.setTime(endDate);

        int months = getMonthDiff(d1, d2);

        int[] entries = new int[months];
        for (RatData rd: response) {
            Date tempDate = rd.getDate();
            Calendar tempCal = Calendar.getInstance();
            tempCal.setTime(tempDate);
            entries[months - getMonthDiff(tempCal, d2)] += 1;
        }

        ArrayList<BarEntry> entryBar = new ArrayList<>();
        for (int j = 0; j < months; j++) {
            entryBar.add(new BarEntry(j, entries[j]));
        }

        BarDataSet bds = new BarDataSet(entryBar, "Reports");
        BarData bd = new BarData(bds);
        graph.setData(bd);
        
    }
    public int getMonthDiff(Calendar d1, Calendar d2){
        int diff = (d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR)) * 12
                + d2.get(Calendar.MONTH) - d1.get(Calendar.MONTH);
        return diff;
    }
}
