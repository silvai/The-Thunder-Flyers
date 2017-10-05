package edu.gatech.thethunderflyers.android.model;

public enum Borough {
    MANHATTAN ("Manhattan"), STATENISLAND ("Staten Island"), QUEENS ("Queens"),
        BROOKLYN ("Brooklyn"), BRONX ("Bronx");

    private String representation;

    Borough (String representation) {
        this.representation = representation;
    }
}