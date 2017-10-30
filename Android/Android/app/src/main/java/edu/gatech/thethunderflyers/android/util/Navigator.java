package edu.gatech.thethunderflyers.android.util;

import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

import edu.gatech.thethunderflyers.android.controller.DetailRatDataActivity;
import edu.gatech.thethunderflyers.android.controller.GraphActivity;
import edu.gatech.thethunderflyers.android.controller.LoginActivity;
import edu.gatech.thethunderflyers.android.controller.MainActivity;
import edu.gatech.thethunderflyers.android.controller.MapsActivity;
import edu.gatech.thethunderflyers.android.controller.RegisterActivity;
import edu.gatech.thethunderflyers.android.controller.ReportRatActivity;
import edu.gatech.thethunderflyers.android.controller.WelcomeActivity;
import edu.gatech.thethunderflyers.android.model.RatData;

public class Navigator {
    public static void goToLoginActivity(Context c) {
        c.startActivity(new Intent(c, LoginActivity.class));
    }

    public static void goToRegisterActivity(Context c) {
        c.startActivity(new Intent(c, RegisterActivity.class));
    }

    public static void goToDetailRatDataActivity(Context c, RatData rd) {
        c.startActivity(new Intent(c, DetailRatDataActivity.class).putExtra("rat", rd));
    }

    public static void goToMainActivity(Context c) {
        c.startActivity(new Intent(c, MainActivity.class));
    }

    public static void goToMapsActivity(Context c) {
        c.startActivity(new Intent(c, MapsActivity.class));
    }

    public static void goToReportRatActivity(Context c) {
        c.startActivity(new Intent(c, ReportRatActivity.class));
    }

    public static void goToWelcomeActivity(Context c) {
        c.startActivity(new Intent(c, WelcomeActivity.class));
    }

    public static void goToGraphActivity(Context c) {
        c.startActivity(new Intent(c, GraphActivity.class));
    }
}
