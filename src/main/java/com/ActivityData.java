package com;

public class ActivityData extends Data{
    public String category;

    // Constructors
    public ActivityData() {
    }
    
    // Getters and setters
    public ActivityData(String name, int creditPoints, String category) {
        super(name, creditPoints);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
