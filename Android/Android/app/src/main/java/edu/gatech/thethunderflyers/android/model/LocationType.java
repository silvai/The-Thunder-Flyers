package edu.gatech.thethunderflyers.android.model;

import com.google.gson.annotations.SerializedName;

public enum LocationType {
    //location type (select from: 1-
    // 2 Family Dwelling, 3+ Family Apt. Building, 3+ Family Mixed Use Building,
    // Commercial Building, Vacant Lot, Construction Site, Hospital, Catch Basin/Sewer)

    @SerializedName("3+ Family Mixed Use Building")
    THREE_FAMILY_MIXED_USE ("3+ Family Mixed Use Building"),

    @SerializedName("Commercial Building")
    COMMERCIAL_BUILDING ("Commercial Building"),

    @SerializedName("1-2 Family Dwelling")
    ONE_TWO_FAMILY_DWELLING ("1-2 Family Dwelling"),

    @SerializedName("3+ Family Apt. Building")
    THREEPLUS_FAMILY_APT ("3+ Family Apt. Building"),

    @SerializedName("Public Stairs")
    PUBLIC_STAIRS ("Public Stairs"),

    @SerializedName("Other (Explain Below)")
    OTHER ("Other (Explain Below)"),

    @SerializedName("Hospital")
    HOSPITAL ("Hospital"),

    @SerializedName("Construction Site")
    CONSTRUCTION_SITE ("Construction Site"),

    @SerializedName("Vacant Lot")
    VACANT_LOT ("Vacant Lot"),

    @SerializedName("Vacant Building")
    VACANT_BUILDING ("Vacant Building"),

    @SerializedName("Parking Lot/Garage")
    PARKING_LOT_GARAGE ("Parking Lot/Garage"),

    @SerializedName("Public Garden")
    PUBLIC_GARDEN ("Public Garden"),

    @SerializedName("1-2 Family Mixed Use Building")
    ONE_TWO_FAMILY_MIXED_USE ("1-2 Family Mixed Use Building"),

    @SerializedName("Catch Basin/Sewer")
    CATCH_BASIN_SEWER ("Catch Basin/Sewer"),

    @SerializedName("Day Care/Nursery")
    DAY_CARE_NURSERY ("Day Care/Nursery"),

    @SerializedName("Government Building")
    GOVERNMENT_BUILDING ("Government Building"),

    @SerializedName("Office Building")
    OFFICE_BUILDING ("Office Building"),

    @SerializedName("School/Pre-School")
    SCHOOL_PRE_SCHOOL ("School/Pre-School"),

    @SerializedName("Single Room Occupancy (SRO)")
    SINGLE_ROOM_OCCUPANCY ("Single Room Occupancy (SRO");

    private final String representation;

    LocationType (String representation) {
        this.representation = representation;
    }


    @Override
    public String toString() {
        return representation;
    }
}
