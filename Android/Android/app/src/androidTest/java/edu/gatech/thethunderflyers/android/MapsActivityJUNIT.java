package edu.gatech.thethunderflyers.android;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Steven Nguyen on 11/13/2017.
 * This JUNIT is to test if the dates are not null and if the
 * begin date is before the end date.
 */
@RunWith(AndroidJUnit4.class)
public class MapsActivityJUNIT {

    private final SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    private Date beginDate;
    private Date endDate;

    /**
     * Sets the date to the dates passed through
     * This will try to a parseException
     * although
     * --in this case, the dates will not throw an error
     * --because it's picked from a calendar view, so all dates
     * --passed will be valid;
     * but we test it here just in case
     * so we pass in non date strings and parse through it
     * and assign a date to variables
     * this should return a null pointer
     * @throws ParseException throws the parsing exception

     */
    @Test(expected = ParseException.class)
    public void setDate() throws ParseException {
        String begin = "asdasd";
        String end = "asdasdasd";
        beginDate = FULL_DATE_FORMAT.parse(begin);
    }

    /**
     * this is to test the parse exception for end date
     */
    @Test(expected = ParseException.class)
    public void setDateEnd() throws ParseException {
        String begin = "asdasd";
        String end = "ASDAaSD";
        endDate = FULL_DATE_FORMAT.parse(end);
    }

    /**
     * The next 3 tests are to check for null pointer, although we
     * actually do that in the controller
     * This test is to check if the begin date is null
     * this will throw a NullPointerException because you cant parse a null
     */
    @Test(expected = NullPointerException.class)
    public void testNullBeginDate() throws NullPointerException, ParseException{
        String begin = null;
        String end = "10/10/12";
        Date beginning = FULL_DATE_FORMAT.parse(begin);
    }
    /**
     Test to see if the end date is null
     this should throw a ParseException because
     you can't pare a null.
     */
    @Test(expected =  NullPointerException.class)
    public void testNullEndDate() throws NullPointerException, ParseException{
        String begin = "10/10/12";
        String end = null;
        Date ending = FULL_DATE_FORMAT.parse(end);
    }

    /**
     * Test to see if both dates are null
     */
    @Test(expected = NullPointerException.class)
    public void testNullBothDates()  throws NullPointerException, ParseException{
        String begin = null;
        String end = null;
        Date begin2 = FULL_DATE_FORMAT.parse(begin);
        Date end2 = FULL_DATE_FORMAT.parse(end);
    }

    /**
     * Test to see if the begin date is after the end date
     */
    @Test
    public void testBeginGreaterThanEnd() {
        String dateBegin = "10/10/12";
        String dateEnd = "09/09/12";
        Date begin = new Date();
        Date end = new Date();
        try {
            begin = FULL_DATE_FORMAT.parse(dateBegin);
            end = FULL_DATE_FORMAT.parse(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertTrue(begin.compareTo(end) > 0);
        assertFalse(begin.compareTo(end) < 0);
    }
    /**
     * Test to see if the begin date is before end date
     */
    @Test
    public void testBeginBeforeEnd() {
        String dateBegin = "05/10/12";
        String dateEnd = "12/09/12";
        Date begin = new Date();
        Date end = new Date();
        try {
            begin = FULL_DATE_FORMAT.parse(dateBegin);
            end = FULL_DATE_FORMAT.parse(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertTrue(begin.compareTo(end) < 0);
        assertFalse(begin.compareTo(end) > 0);
    }

    /**
     * Test to see when the dates are the same
     */
    public void testSame() {
        String dateBegin = "12/09/12";
        String dateEnd = "12/09/12";
        Date begin = new Date();
        Date end = new Date();
        try {
            begin = FULL_DATE_FORMAT.parse(dateBegin);
            end = FULL_DATE_FORMAT.parse(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertTrue(begin.compareTo(end) == 0);
    }
}
