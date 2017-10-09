package edu.gatech.thethunderflyers.android.model;
import com.google.gson.annotations.SerializedName;

public enum Borough {
    @SerializedName("MANHATTAN")
    MANHATTAN ("Manhattan"),

    @SerializedName("STATEN ISLAND")
    STATENISLAND ("Staten Island"),

    @SerializedName("QUEENS")
    QUEENS ("Queens"),

    @SerializedName("BROOKLYN")
    BROOKLYN ("Brooklyn"),

    @SerializedName("BRONX")
    BRONX ("Bronx");

    private String representation;

    Borough (String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}