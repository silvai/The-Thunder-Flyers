package edu.gatech.thethunderflyers.ratapp.model;
import com.google.gson.annotations.SerializedName;

/**
 * Borough: enum representing the five boroughs of New York City
 */
public enum Borough {
    @SerializedName("MANHATTAN")
    MANHATTAN ("Manhattan"),

    @SerializedName("STATEN ISLAND")
    STATEN_ISLAND ("Staten Island"),

    @SerializedName("QUEENS")
    QUEENS ("Queens"),

    @SerializedName("BROOKLYN")
    BROOKLYN ("Brooklyn"),

    @SerializedName("BRONX")
    BRONX ("Bronx");

    private final String representation;

    Borough (String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}