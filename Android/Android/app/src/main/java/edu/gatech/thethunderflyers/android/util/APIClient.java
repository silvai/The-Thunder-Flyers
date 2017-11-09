package edu.gatech.thethunderflyers.android.util;

import java.lang.ref.WeakReference;

import edu.gatech.thethunderflyers.android.R;
import edu.gatech.thethunderflyers.android.controller.GraphActivity;
import edu.gatech.thethunderflyers.android.controller.LoginActivity;
import edu.gatech.thethunderflyers.android.controller.MainActivity;
import edu.gatech.thethunderflyers.android.controller.MapsActivity;
import edu.gatech.thethunderflyers.android.controller.RegisterActivity;
import edu.gatech.thethunderflyers.android.controller.ReportRatActivity;

/**
 * APIClient: helper class to facilitate calling the API
 */
public final class APIClient {
    public static final APIClient API_CLIENT = new APIClient();

    private APIClient() {}

    /**
     * Executes the APIMessagePostTask with the API login url.
     * @param lu JSON representing the login user's credentials
     * @param ref a WeakReference to LoginActivity (to prevent memory leaks)
     */
    public void login(String lu, WeakReference<LoginActivity> ref) {
        LoginActivity a = ref.get();
        APIMessagePostTask task = new APIMessagePostTask(a.getString(R.string.login_url), a);
        task.execute(lu);
    }

    /**
     * Executes the APIMessagePostTask with the API register url.
     * @param u JSON representing the user trying to register
     * @param ref a WeakReference to RegisterActivity (to prevent memory leaks)
     */
    public void register(String u, WeakReference<RegisterActivity> ref) {
        RegisterActivity r = ref.get();
        APIMessagePostTask task = new APIMessagePostTask(r.getString(R.string.register_url), r);
        task.execute(u);
    }

    /**
     * Executes the DataGetTask with the API getData (by date/id) url.
     * @param lastId the id of the last RatData in the previously returned list of RatData
     * @param millis the datetime (Unix epoch) of the last RatData in the previously returned list
     *               of RatData
     * @param ref a WeakReference to MainActivity (to prevent memory leaks)
     */
    public void getRatDataList(int lastId, long millis, WeakReference<MainActivity> ref) {
        MainActivity m = ref.get();
        DataGetTask task = new DataGetTask(m.getString(R.string.get_data_url) + lastId + "/"
                + millis + "/", m);
        task.execute();
    }

    /**
     * Executes the APIMessagePostTask with the API add rat report url.
     * @param rd JSON representing the new rat report
     * @param ref a WeakReference to ReportRatActivity (to prevent memory leaks)
     */
    public void submitRatReport(String rd, WeakReference<ReportRatActivity> ref) {
        ReportRatActivity rra = ref.get();
        APIMessagePostTask task =
                new APIMessagePostTask(rra.getString(R.string.post_rat_data_url), rra);
        task.execute(rd);
    }

    /**
     * Executes the DataGetTask with the API getData (between dates) url.
     * @param beginDate the datetime (Unix epoch) of the beginning of the range
     * @param endDate the datetime (Unix epoch) of the end of the range
     * @param ref a WeakReference to MapsActivity (to prevent memory leaks)
     */
    public void getRatDataDateRange(long beginDate, long endDate, WeakReference<MapsActivity> ref) {
        MapsActivity ma = ref.get();
        DataGetTask task =
                new DataGetTask(ma.getString(R.string.get_data_date_url) + beginDate + "/"
                        + endDate, ma);
        task.execute();
    }

    /**
     * Executes the DataGetTask with the API getData (between dates) url.
     * @param beginDate the datetime (Unix epoch) of the beginning of the range
     * @param endDate the datetime (Unix epoch) of the end of the range
     * @param ref a WeakReference to GraphActivity (to prevent memory leaks)
     */
    public void getRatDataDateRangeGraph(long beginDate, long endDate,
                                         WeakReference<GraphActivity> ref) {
        GraphActivity ga = ref.get();
        DataGetTask task =
                new DataGetTask(ga.getString(R.string.get_data_date_url) + beginDate + "/"
                        + endDate, ga);
        task.execute();
    }
}
