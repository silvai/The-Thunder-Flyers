package edu.gatech.thethunderflyers.ratapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * APIMessage: class to represent a generic response from the API
 */
public class APIMessage {
    /**
     * Whether or not the request was successful
     */
    @SerializedName("success")
    private boolean success;

    /**
     * The message returned by the API
     */
    @SerializedName("message")
    private String message;

    public boolean getSuccess() { return this.success; }

    public void setSuccess(boolean success) { this.success = success; }

    /**
     * Method to get the message returned from the API
     * @return the message returned from the API
     */
    public String getMessage() {
        return message;
    }

    /**
     * Method to set the message to a new value
     * @param message the new value the message should be set to
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
