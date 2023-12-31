package com;

public class Data {

    private String name;
    private int creditPoints;

    // Constructors
    public Data() {
    }

    public Data(String name, int creditPoints) {
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
