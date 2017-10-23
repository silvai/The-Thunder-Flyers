package edu.gatech.thethunderflyers.android.util;

import java.lang.ref.WeakReference;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.controller.LoginActivity;
import edu.gatech.thethunderflyers.android.controller.MainActivity;
import edu.gatech.thethunderflyers.android.controller.RegisterActivity;
import edu.gatech.thethunderflyers.android.controller.ReportRatActivity;

public class APIClient {
    private static APIClient client;

    private APIClient() {}

    public static APIClient getInstance() {
        if (client == null) {
            client = new APIClient();
        }
        return client;
    }

    public void login(String lu, WeakReference<LoginActivity> ref) {
        LoginActivity a = ref.get();
        new APIMessagePostTask(a.getString(R.string.login_url), a).execute(lu);
    }

    public void register(String u, WeakReference<RegisterActivity> ref) {
        RegisterActivity r = ref.get();
        new APIMessagePostTask(r.getString(R.string.register_url), r).execute(u);
    }

    public void getRatDataList(int lastId, long millis, WeakReference<MainActivity> ref) {
        MainActivity m = ref.get();
        new DataGetTask(m.getString(R.string.get_data_url) + lastId + "/"
                + millis + "/", m).execute();
    }

    public void submitRatReport(String rd, WeakReference<ReportRatActivity> ref) {
        ReportRatActivity rra = ref.get();
        new APIMessagePostTask(rra.getString(R.string.post_rat_data_url), rra).execute(rd);
    }

    public void getRatDataDateRange() {

    }
}
