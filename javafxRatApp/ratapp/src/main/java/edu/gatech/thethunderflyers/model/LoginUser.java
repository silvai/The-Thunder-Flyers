package edu.gatech.thethunderflyers.android.model;

import com.google.gson.annotations.SerializedName;

public class LoginUser {
    @SerializedName("username")
    private final String username;

    @SerializedName("password")
    private final String password;

    public LoginUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
