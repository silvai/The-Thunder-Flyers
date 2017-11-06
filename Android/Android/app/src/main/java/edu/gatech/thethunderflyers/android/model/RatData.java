package edu.gatech.thethunderflyers.android.model;
import java.io.Serializable;
import java.util.Date;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.annotations.SerializedName;

import edu.gatech.thethunderflyers.android.controller.LoginActivity;

/**
 * The class for RatData
 */
public class RatData implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("createdDate")
    private Date date;

    @SerializedName("locationType")
    private LocationType locationType;

    @SerializedName("incidentZip")
    private int zip;

    @SerializedName("incidentAddress")
    private String address;

    @SerializedName("city")
    private String city;

    @SerializedName("borough")
    private Borough borough;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("userId")
    private int userId;


    public RatData(LocationType locationType, int zip, String address,
                   String city, Borough borough, double latitude, double longitude) {
        this.locationType = locationType;
        this.zip = zip;
        this.address = address;
        this.city = city;
        this.borough = borough;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userId = LoginActivity.userId;
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

    /**
     * setter method
     * @param locationType sets the locationType to the new one
     */
    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    /**
     * setter method
     * @param zip sets the zip to the new one
     */
    public void setZip(int zip) {
        this.zip = zip;
    }

    /**
     * setter method
     * @param address sets the address to the new one
     */
    public void setAddress(String address) {
        this.address = address;
    }


    /**
     * setter method
     * @param city sets the city to the new one
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * setter method
     * @param borough sets the borough to the new one
     */
    public void setBorough(Borough borough) {
        this.borough = borough;
    }

    /**
     * setter method
     * @param latitude sets the latitude to the new one
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    /**
     * setter method
     * @param longitude sets the longitude to the new one
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets the user ID
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * sets the user id
     * @param userId the new userId to set userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    private LatLng getMapLatLng() { return new LatLng(latitude, longitude); }

    private String getMapSnippet() { return this.getDate() + "\n"
            + this.getLocationType(); }

    private String getMapTitle() { return this.getId() + ""; }

    /**
     * method for markers for maps
     * @return returns the MarkerOptions for this ratData
     */
    public MarkerOptions getMapMarkerOptions() {
        return new MarkerOptions()
                .position(this.getMapLatLng())
                .title(this.getMapTitle())
                .snippet(this.getMapSnippet());
    }
}
