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


    }
}

//
//public class JunitTests {
//
//    LatLng latitude2 = new LatLng(90,-90);
//    Report report = new Report("Water", "Zohra Tabassum", latitude2 , "Bottled","Treatable-Clear", "08/30/97");
//
//    WorkerReport reportworker = new WorkerReport("Water", "Zohra Tabassum", latitude2 , "Bottled","Treatable-Clear", "08/30/97", -10, -20);
//
//
//
//
//    //Test to make sure you do
//    //Zohra's Junit
//    @Test(expected = IllegalArgumentException.class)
//    public void testReporter() {
//        report.setName("Lase");
//        assertEquals("Lase", report.getReporter());
//        report.setName(null);
//    }
//
//    //Josh's Junit
//    @Test(expected = IllegalArgumentException.class)
//    public void testWaterCondition(){
//        report.setWaterCondition("Treatable-Muddy");
//        assertEquals("Treatable-Muddy", report.getWaterCondition());
//        report.setWaterCondition(null);
//    }
//
//    //Lase's Junit
//    @Test(expected = IllegalArgumentException.class)
//    public void testWaterType(){
//        report.setWaterType("Bottled");
//        assertEquals("Bottled", report.getWaterType());
//        report.setWaterType(null);
//    }
//
//
//    //Jeffrey's JUnit
//
//    @Test(expected = IllegalArgumentException.class)
//    public void registeredUserTester() {
//        final RegisteredUser user = new RegisteredUser();
//        final String coordinates = user.getCoordinates();
//        final String address = user.getAddress();
//        final int login = user.getLoginAttemps();
//        assertEquals(coordinates, "None");
//        assertEquals(address, "None");
//        assertEquals(login, 0);
//        final Map<String, Object> ret = user.toMap();
//        assertEquals("None", ret.get("coord"));
//        user.setCoordinates(null);
//
//    }
//
//
//    // Michelle's Junit
//    @Test(expected = IllegalArgumentException.class)
//    public void testPPM(){
//        reportworker.setPpm(10);
//        assertEquals(10, reportworker.getPpm());
//        reportworker.setPpm(-1);
//
//
//
//    }
//


//}