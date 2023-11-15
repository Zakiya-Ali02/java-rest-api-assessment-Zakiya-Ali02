package com;

import java.util.List;

public class CreditPointsData {
    private List<ActivityData> activities;

    // Constructors
    public CreditPointsData() {
    }

    public CreditPointsData(List<ActivityData> activities) {
        this.activities = activities;
    }

    // Getter and setter
    public List<ActivityData> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityData> activities) {
        this.activities = activities;
    }
}
