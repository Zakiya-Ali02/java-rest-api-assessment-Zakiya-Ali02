package com;

public class UserData extends Data {
    private String office;
    private String activity;

    // Constructors
    public UserData() {
    }

    public UserData(String name, String office, String activity, int creditPoints) {
        super(name, creditPoints);
        this.office = office;
        this.activity = activity;
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
