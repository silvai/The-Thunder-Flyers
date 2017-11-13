package edu.gatech.thethunderflyers.android;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    /**
     * Example test provided by Android Studio
     * @throws Exception in order to satisfy checked exception
     */
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("edu.gatech.thethunderflyers.android", appContext.getPackageName());
    }

    //Ella's Tests for handleResponse(List<RatData> response, Exception ex)
    //  method in GraphActivity
    @Test
    public void testHandleResponse() {

    }

}
