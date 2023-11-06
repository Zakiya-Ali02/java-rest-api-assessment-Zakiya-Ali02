package com;

public class Activity {
    private String name;
    private int creditPoints;

    // Constructors
    public Activity() {
    }

    public Activity(String name, int creditPoints) {
        this.name = name;
        this.creditPoints = creditPoints;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreditPoints() {
        return creditPoints;
    }

    public void setCreditPoints(int creditPoints) {
        this.creditPoints = creditPoints;
    }
}
