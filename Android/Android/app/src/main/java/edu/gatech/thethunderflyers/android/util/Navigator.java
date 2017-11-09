package edu.gatech.thethunderflyers.android.util;

import android.content.Context;
import android.content.Intent;

import java.io.Serializable;

import edu.gatech.thethunderflyers.android.controller.DetailRatDataActivity;
import edu.gatech.thethunderflyers.android.controller.GraphActivity;
import edu.gatech.thethunderflyers.android.controller.LoginActivity;
import edu.gatech.thethunderflyers.android.controller.MainActivity;
import edu.gatech.thethunderflyers.android.controller.MapsActivity;
import edu.gatech.thethunderflyers.android.controller.RegisterActivity;
import edu.gatech.thethunderflyers.android.controller.ReportRatActivity;
import edu.gatech.thethunderflyers.android.controller.WelcomeActivity;

/**
 * Navigator: class to allow navigation between Activities.
 */
public class Navigator {
    /**
     * Allows navigation to LoginActivity.
     * @param c the Context of the Activity from which the method was called
     */
    public static void goToLoginActivity(Context c) {
        c.startActivity(new Intent(c, LoginActivity.class));
    }

    /**
     * Allows navigation to RegisterActivity.
     * @param c the Context of the Activity from which the method was called
     */
    public static void goToRegisterActivity(Context c) {
        c.startActivity(new Intent(c, RegisterActivity.class));
    }

    /**
     * Allows navigation to DetailRatDataActivity.
     * @param c the Context of the Activity from which the method was called
     * @param rd the RatData to be passed to DetailRatDataActivity
     */
    public static void goToDetailRatDataActivity(Context c, Serializable rd) {
        Intent i = new Intent(c, DetailRatDataActivity.class);
        i.putExtra("rat", rd);
        c.startActivity(i);
    }

    /**
     * Allows navigation to MainActivity.
     * @param c the Context of the Activity from which the method was called
     */
    public static void goToMainActivity(Context c) {
        c.startActivity(new Intent(c, MainActivity.class));
    }

    /**
     * Allows navigation to MapsActivity.
     * @param c the Context of the Activity from which the method was called
     */
    public static void goToMapsActivity(Context c) {
        c.startActivity(new Intent(c, MapsActivity.class));
    }

    /**
     * Allows navigation to ReportRatActivity.
     * @param c the Context of the Activity from which the method was called
     */
    public static void goToReportRatActivity(Context c) {
        c.startActivity(new Intent(c, ReportRatActivity.class));
    }

    /**
     * Allows navigation to WelcomeActivity.
     * @param c the Context of the Activity from which the method was called
     */
    public static void goToWelcomeActivity(Context c) {
        c.startActivity(new Intent(c, WelcomeActivity.class));
    }

    /**
     * Allows navigation to GraphActivity.
     * @param c the Context of the Activity from which the method was called
     */
    public static void goToGraphActivity(Context c) {
        c.startActivity(new Intent(c, GraphActivity.class));
    }
}
