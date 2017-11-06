package edu.gatech.thethunderflyers.android.model;

import com.google.gson.annotations.SerializedName;

public class APIMessage {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return !success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
