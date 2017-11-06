package edu.gatech.thethunderflyers.android.model;

public enum Graphs {
    BARCHART ("Bar Chart"), LINECHART ("Line Chart"), PIECHART("Pie Chart");

    private final String representation;

    Graphs (String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}