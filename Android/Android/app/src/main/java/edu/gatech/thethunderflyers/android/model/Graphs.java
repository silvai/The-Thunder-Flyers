package edu.gatech.thethunderflyers.android.model;

/**
 * Graphs: an enum representing the types of charts supported by the application
 */
public enum Graphs {
    BAR_CHART ("Bar Chart"), LINE_CHART ("Line Chart"), PIE_CHART("Pie Chart");

    private final String representation;

    Graphs (String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}