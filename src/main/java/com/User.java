package com;

public class User {
    private String name;
    private String office;
    private String activity;
    private int creditPoints;

    // Constructors
    public User() {
    }

    public User(String name, String office, String activity,int creditPoints) {
        this.name = name;
        this.office = office;
        this.activity = activity;
        this.creditPoints = creditPoints;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
    
    public int getCreditPoints() {
        return creditPoints;
    }

    public void setCreditPoints(int creditPoints) {
        this.creditPoints = creditPoints;
    }
}
