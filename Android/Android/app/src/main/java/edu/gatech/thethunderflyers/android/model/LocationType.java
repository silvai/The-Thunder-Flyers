package edu.gatech.thethunderflyers.android.model;

public enum LocationType {
    //location type (select from: 1-
    // 2 Family Dwelling, 3+ Family Apt. Building, 3+ Family Mixed Use Building,
    // Commercial Building, Vacant Lot, Construction Site, Hospital, Catch Basin/Sewer)
    FAMILYDWELLING ("2 Family Dwelling"), FAMILYAPT("3+ Family Apt. Building"),
        FAMILYMIXED("3+ Family Mixed Use Building"), COMMBUILD("Commercial Building"),
        VACANTLOT("Vacant Lot"), CONSITE("Construction Site"), HOSPITAL("Hospital"),
        CATCH("Catch Basin/Sewer");

    private String representation;

    LocationType (String representation) {
        this.representation = representation;
    }
}
