package edu.gatech.thethunderflyers.android.model;

import com.google.gson.Gson;

/**
 * Model: facade for various model classes
 */
public class Model {
    private static final Gson gson = new Gson();

    /**
     * Creates a LoginUser object with the data from LoginActivity and converts it to JSON for APIClient.
     * @param username the LoginUser's username
     * @param password the LoginUser's password
     * @return JSON representing the newly created LoginUser
     */
    public static String getLoginUser(String username, String password) {
        return gson.toJson(new LoginUser(username, password));
    }

    /**
     * Creates a RatData object with the data from ReportRatActivity and converts it to JSON for APIClient.
     * @param locationType the RatData's location type
     * @param zip the RatData's zip code
     * @param address the RatData's address
     * @param city the RatData's city
     * @param borough the RatData's borough (of NYC)
     * @param latitude the RatData's latitude
     * @param longitude the RatData's longitude
     * @return JSON representing the newly created RatData
     */
    public static String getRatData(LocationType locationType, int zip, String address,
                                     String city, Borough borough, double latitude, double longitude) {
        return gson.toJson(new RatData(locationType, zip, address, city, borough, latitude, longitude));
    }

    /**
     * Creates a User object with the data from RegisterActivity and converts it to JSON for APIClient.
     * @param firstName the User's first name
     * @param lastName the User's last name
     * @param username the User's username
     * @param password the User's password
     * @param userMode the User's user mode
     * @return JSON representing the newly created User
     */
    public static String getUser(String firstName, String lastName, String username, String password,
                               UserMode userMode) {
        return gson.toJson(new User(firstName, lastName, username, password, userMode));
    }
}
