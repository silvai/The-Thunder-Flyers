package edu.gatech.thethunderflyers.android.model;

public enum UserMode {
    USER ("user"), ADMIN ("admin");

    private String representation;

    UserMode (String representation) {
        this.representation = representation;
    }
}