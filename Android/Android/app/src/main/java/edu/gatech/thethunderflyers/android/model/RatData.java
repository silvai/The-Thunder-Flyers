package edu.gatech.thethunderflyers.android.model;
import java.util.Date;
import com.google.gson.annotations.SerializedName;

public class RatData {
    /*
Location Type (make an ENUM)
Borough (ENUM)
     */
    private int id;
    private Date date;

    private int zip;
    private int address;
    private String city;
    private Borough borough;
    private double latitude;
    private double longitude;
}
