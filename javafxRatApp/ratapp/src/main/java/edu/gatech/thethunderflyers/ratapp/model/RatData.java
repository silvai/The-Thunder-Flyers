package edu.gatech.thethunderflyers.ratapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * The class for RatData
 */
public class RatData implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("createdDate")
    private Date date;

    @SerializedName("locationType")
    private final LocationType locationType;

    @SerializedName("incidentZip")
    private final int zip;

    @SerializedName("incidentAddress")
    private final String address;

    @SerializedName("city")
    private final String city;

    @SerializedName("borough")
    private final Borough borough;

    @SerializedName("latitude")
    private final double latitude;

    @SerializedName("longitude")
    private final double longitude;

    @SerializedName("userId")
    private final int userId;


    RatData(LocationType locationType, int zip, String address,
            String city, Borough borough, double latitude, double longitude, int userId) {
        this.locationType = locationType;
        this.zip = zip;
        this.address = address;
        this.city = city;
        this.borough = borough;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userId = userId;
    }

    /**
     * Getter method
     * @return the id of the report
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method
     * @return the date of the report
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter method
     * @return the location type of the report
     */
    public LocationType getLocationType() {
        return locationType;
    }

    /**
     * Getter method
     * @return the zip of the report
     */
    public int getZip() {
        return zip;
    }

    /**
     * Getter method
     * @return the address of the report
     */
    public String getAddress() {
        return address;
    }

    /**
     * Getter method
     * @return the city of the report
     */
    public String getCity() {
        return city;
    }

    /**
     * Getter method
     * @return the borough of the report
     */
    public Borough getBorough() {
        return borough;
    }

    /**
     * Getter method
     * @return the latitude of the report
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Getter method
     * @return the longitude of the report
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * setter method
     * @param id sets the id to the new one
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * setter method
     * @param date sets the date to the new one
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
