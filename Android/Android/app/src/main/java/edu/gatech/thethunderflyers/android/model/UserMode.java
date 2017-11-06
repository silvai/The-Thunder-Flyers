package edu.gatech.thethunderflyers.android.model;

/**
 * The enum for userMode
 */
public enum UserMode {
    USER ("User"), ADMIN ("Admin");

    private final String representation;

    UserMode (String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}