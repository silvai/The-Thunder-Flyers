package edu.gatech.thethunderflyers.android;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Created by isabellasilva on 11/13/17.
 */

//Testing GraphActivity submitDates(View view)

@RunWith(AndroidJUnit4.class)
public class submitDatesTest {

    private final SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    private Date start;
    private Date end;

    /**
     * Test to see if the range of the dates goes from a previous time to a later time
     */
    @Test (expected = IllegalArgumentException.class)
    public void testValidDates() {
        String startDate = "11/11/11";
        String endDate = "10/10/10";
        Date begin = new Date();
        Date end = new Date();
        try {
            begin = FULL_DATE_FORMAT.parse(startDate);
            end = FULL_DATE_FORMAT.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertTrue(begin.compareTo(end) < 0);
        assertFalse(begin.compareTo(end) > 0);


    }

    /*
    * Test that the first date is smaller than the second date
     */
    @Test
    public void testRightOrder() {
        String startDate = "11/11/11";
        String endDate = "12/11/11";
        Date begin = new Date();
        Date end = new Date();
        try {
            begin = FULL_DATE_FORMAT.parse(startDate);
            end = FULL_DATE_FORMAT.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertTrue(begin.compareTo(end) < 0);
        assertFalse(begin.compareTo(end) > 0);


    }
}
