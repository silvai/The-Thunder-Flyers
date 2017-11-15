package edu.gatech.thethunderflyers.android;

import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import edu.gatech.thethunderflyers.android.model.APIMessage;
import edu.gatech.thethunderflyers.android.model.LoginUser;
import edu.gatech.thethunderflyers.android.util.APIMessagePostTask;
import edu.gatech.thethunderflyers.android.util.AsyncHandler;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class APIMessagePostTaskTest {
    @Test
    public void testBadUrlException() {
        APIMessagePostTask ampt = new APIMessagePostTask("flipflop", new AsyncHandler<APIMessage>() {
            @Override
            public void handleResponse(APIMessage response, Exception ex) {
                assertNotNull(ex);
                assertTrue(ex instanceof IOException);
                assertNull(response);
            }
        });
        ampt.execute();
    }

    @Test
    public void testConnectionDisconnectsWhenDone() {
        APIMessagePostTask ampt = new APIMessagePostTask("http://10.0.2.2:3000/auth/register", new AsyncHandler<APIMessage>() {
            @Override
            public void handleResponse(APIMessage response, Exception ex) {
            }
        });
        ampt.execute();
        assertNull(ampt.getConnection());
        APIMessagePostTask ampt2 = new APIMessagePostTask("flipflop", new AsyncHandler<APIMessage>() {
            @Override
            public void handleResponse(APIMessage response, Exception ex) {
            }
        });
        ampt2.execute();
        assertNull(ampt2.getConnection());
    }

    @Test
    public void testReaderClosesWhenDone() {
        APIMessagePostTask ampt = new APIMessagePostTask("http://10.0.2.2:3000/auth/register", new AsyncHandler<APIMessage>() {
            @Override
            public void handleResponse(APIMessage response, Exception ex) {
            }
        });
        ampt.execute();
        assertNull(ampt.getReader());
        APIMessagePostTask ampt2 = new APIMessagePostTask("flipflop", new AsyncHandler<APIMessage>() {
            @Override
            public void handleResponse(APIMessage response, Exception ex) {
            }
        });
        ampt2.execute();
        assertNull(ampt2.getReader());
    }

    @Test
    public void testJsonReturnIfGoodRequest() {
        APIMessagePostTask ampt = new APIMessagePostTask("http://10.0.2.2:3000/auth/login", new AsyncHandler<APIMessage>() {
            @Override
            public void handleResponse(APIMessage response, Exception ex) {
                assertNotNull(response);
                assertTrue(response.isSuccess());
            }
        });
        ampt.execute(new Gson().toJson(new LoginUser("sbhat45", "ace123")));
    }
}
