package edu.gatech.thethunderflyers.android.model;

import com.google.gson.Gson;

public class Model {
    private static Gson gson = new Gson();

    public static String getLoginUser(String username, String password) {
        return gson.toJson(new LoginUser(username, password));
    }

    public static String getRatData(LocationType locatType, int zip, String address,
                                     String city, Borough borough, double latitude, double longitude) {
        return gson.toJson(new RatData(locatType, zip, address, city, borough, latitude, longitude));
    }

    public static String getUser(String firstName, String lastName, String username, String password,
                               UserMode userMode) {
        return gson.toJson(new User(firstName, lastName, username, password, userMode));
    }
}
