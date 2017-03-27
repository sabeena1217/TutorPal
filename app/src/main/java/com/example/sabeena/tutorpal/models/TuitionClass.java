package com.example.sabeena.tutorpal.models;

import android.location.Location;

import java.sql.Time;

/**
 * Created by SaBeeNa on 3/4/2017.
 */

public class TuitionClass {

    private Child childName;
    private String subject;
    private String day;
    private Time starTime;
    private Time endTime;
    private Location location;
    private Double tuitionFee;

    public TuitionClass(Child childName, String subject) {
        this.childName = childName;
        this.subject = subject;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Time getStarTime() {
        return starTime;
    }

    public void setStarTime(Time starTime) {
        this.starTime = starTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Double getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(Double tuitionFee) {
        this.tuitionFee = tuitionFee;
    }
}
