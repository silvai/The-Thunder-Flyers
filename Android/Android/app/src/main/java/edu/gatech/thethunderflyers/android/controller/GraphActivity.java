package edu.gatech.thethunderflyers.android.controller;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.model.Graphs;
import edu.gatech.thethunderflyers.android.model.RatData;
import edu.gatech.thethunderflyers.android.util.AsyncHandler;
import edu.gatech.thethunderflyers.android.util.Navigator;

import static edu.gatech.thethunderflyers.android.util.APIClient.API_CLIENT;

/**
 * GraphActivity: an Activity to show a graph of rat sightings over time
 */
public class GraphActivity extends AppCompatActivity implements AsyncHandler<List<RatData>> {
    private Button begin;
    private Button end;
    private RelativeLayout relLay;
    private Spinner graphSpinner;
    private Date beginDate;
    private Date endDate;

    private final SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        relLay = findViewById(R.id.graph);
        begin = findViewById(R.id.beginDate);
        end = findViewById(R.id.endDate);
        graphSpinner = findViewById(R.id.graphSpinner);
        ArrayAdapter<Graphs> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Graphs.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        graphSpinner.setAdapter(adapter);

        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dp = new DatePickerDialog(view.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar c = new GregorianCalendar();
                        c.set(i,i1,i2);
                        Date d = c.getTime();
                        begin.setText(FULL_DATE_FORMAT.format(d));
                    }
                }, year, month, day);
                DatePicker datePicker = dp.getDatePicker();
                Date date = new Date();
                datePicker.setMaxDate(date.getTime());
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
                DatePickerDialog dpd = new DatePickerDialog(view.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar c = new GregorianCalendar();
                        c.set(i,i1,i2);
                        Date d = c.getTime();
                        end.setText(FULL_DATE_FORMAT.format(d));
                    }

                }, year, month, day);
                DatePicker datePicker = dpd.getDatePicker();
                Date date = new Date();
                datePicker.setMaxDate(date.getTime());
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
        try {
            Date dateBegin = FULL_DATE_FORMAT.parse((String) begin.getText());
            Calendar c1 = Calendar.getInstance();
            c1.setTime(dateBegin);
            c1.set(Calendar.DATE, c1.getActualMinimum(Calendar.DATE));
            dateBegin = c1.getTime();
            Date dateEnd = FULL_DATE_FORMAT.parse((String) end.getText());
            Calendar c2= Calendar.getInstance();
            c2.setTime(dateEnd);
            c2.set(Calendar.DATE, c2.getActualMaximum(Calendar.DATE));
            dateEnd = c2.getTime();
            if ((dateBegin.compareTo(dateEnd) > 0) || (dateEnd == null)) {
                Toast toast = Toast.makeText(this, "Dates invalid!", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                beginDate = dateBegin;
                endDate = dateEnd;

                SharedPreferences sp = getSharedPreferences(getString(R.string.token_file),
                        Context.MODE_PRIVATE);
                String token = sp.getString(getString(R.string.token_field), "");

                API_CLIENT.getRatDataDateRangeGraph(dateBegin.getTime(), dateEnd.getTime(),
                        new WeakReference<>(this), token);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void validateDates(Date start, Date end) {

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
        final float ROTATION_ANGLE = -45;

        if (response.isEmpty()) {
            Toast toast = Toast.makeText(this, "No reports in this Month and Year",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        final Calendar d1 = Calendar.getInstance();
        final Calendar d2 = Calendar.getInstance();
        final Calendar iterate = Calendar.getInstance();

        d1.setTime(beginDate);
        d2.setTime(endDate);
        iterate.setTime(beginDate);

        int months = getMonthDiff(d1, d2) + 1;

        int[] entries = new int[months];
        for (RatData rd: response) {
            Date tempDate = rd.getDate();
            Calendar tempCal = Calendar.getInstance();
            tempCal.setTime(tempDate);
            entries[months - getMonthDiff(tempCal, d2) - 1] += 1;
        }


        final String[] monthYears = new String[months];
        for (int k = 0; k < months; k++) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy", Locale.US);
            monthYears[k] = sdf.format(iterate.getTime());
            iterate.add(Calendar.MONTH, 1);
        }


        IAxisValueFormatter valueFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return monthYears[(int)value];
            }
        };

        Graphs um = (Graphs) graphSpinner.getSelectedItem();
        switch(um) {
            case BAR_CHART:
                relLay.removeAllViews();
                BarChart graph = new BarChart(this);
                relLay.addView(graph,
                        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));

                ArrayList<BarEntry> entryBar = new ArrayList<>();
                for (int j = 0; j < months; j++) {
                    entryBar.add(new BarEntry(j, entries[j]));
                }

                BarDataSet bds = new BarDataSet(entryBar, "Reports");
                BarData bd = new BarData(bds);
                graph.setData(bd);

                XAxis xAxis = graph.getXAxis();
                xAxis.setValueFormatter(valueFormatter);
                xAxis.setGranularity(1);
                xAxis.setLabelRotationAngle(ROTATION_ANGLE);

                Description desc = graph.getDescription();
                desc.setEnabled(false);
                graph.invalidate();
                break;
            case LINE_CHART:
                relLay.removeAllViews();
                LineChart lineChart = new LineChart(this);
                relLay.addView(lineChart,
                        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));

                ArrayList<Entry> entry = new ArrayList<>();
                for (int j = 0; j < months; j++) {
                    entry.add(new Entry(j, entries[j]));
                }

                LineDataSet lds = new LineDataSet(entry, "Reports");
                LineData ld = new LineData(lds);
                lineChart.setData(ld);

                XAxis lcxAxis = lineChart.getXAxis();
                lcxAxis.setValueFormatter(valueFormatter);
                lcxAxis.setGranularity(1);
                lcxAxis.setLabelRotationAngle(ROTATION_ANGLE);

                Description lineChartDescription = lineChart.getDescription();
                lineChartDescription.setEnabled(false);
                lineChart.invalidate();
                break;
            case PIE_CHART:
                relLay.removeAllViews();
                PieChart pieChart = new PieChart(this);
                relLay.addView(pieChart,
                        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));

                ArrayList<PieEntry> pieEntry = new ArrayList<>();
                for (int j = 0; j < months; j++) {
                    pieEntry.add(new PieEntry(j, entries[j]));
                }

                PieDataSet pds = new PieDataSet(pieEntry, "Reports");
                PieData pd = new PieData(pds);
                pieChart.setData(pd);

                Description pieChartDescription = pieChart.getDescription();
                pieChartDescription.setEnabled(false);
                pieChart.invalidate();
                break;
        }
    }


    private int getMonthDiff(Calendar d1, Calendar d2){
        final int MONTHS_IN_YEAR = 12;

        return (((d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR)) * MONTHS_IN_YEAR)
                + d2.get(Calendar.MONTH)) - d1.get(Calendar.MONTH);
    }
}
