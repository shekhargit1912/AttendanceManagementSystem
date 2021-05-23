package com.example.tuitioproject;

public class Average {
    public String studname, totaldays, totalpresent;

    public Average() {
    }

    public Average(String studname, String totaldays, String totalpresent) {
        this.studname = studname;
        this.totaldays = totaldays;
        this.totalpresent = totalpresent;
    }

    public String getStudname() {
        return studname;
    }

    public String getTotaldays() {
        return totaldays;
    }

    public String getTotalpresent() {
        return totalpresent;
    }
}
