package edu.gatech.thethunderflyers.android;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Test;
import org.junit.runner.RunWith;

import edu.gatech.thethunderflyers.android.util.Validator;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        EditText username = new EditText(appContext);
        EditText password = new EditText(appContext);

        username.setText("Ritvik");
        password.setText("Ritvik123");

        Validator v = new Validator(username, password);
        boolean ans = v.validate();
        assertTrue(ans);
    }

    //Ella's Tests for GraphActivity submitDates(View view) method
    @Test
    public void testSubmission() throws Exception {

    }

}
