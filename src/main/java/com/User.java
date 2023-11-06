package com;

public class User {
    private String name;
    private String office;
    private String activity;

    // Constructors
    public User() {
    }

    public User(String name, String office, String activity) {
        this.name = name;
        this.office = office;
        this.activity = activity;
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
}
