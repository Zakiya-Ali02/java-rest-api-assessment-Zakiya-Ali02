package com;

import lombok.Data;

@Data
public class CreditsData {
    private String activity;
    private int credit;

    public CreditsData(String activity, int credit) {
        this.activity = activity;
        this.credit = credit;
    }
}