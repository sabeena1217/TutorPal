package com.example.sabeena.tutorpal.models;

import java.sql.Time;

/**
 * Created by SaBeeNa on 4/13/2017.
 */

public class Day {
    private DayOfTheWeek dayOfTheWeek;
    private String starTime;
    private String endTime;

    public enum DayOfTheWeek{
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    }
    public Day(DayOfTheWeek dayOfTheWeek, String starTime, String endTime) {
        this.dayOfTheWeek = dayOfTheWeek;
        this.starTime = starTime;
        this.endTime = endTime;
    }

    public DayOfTheWeek getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public String getStarTime() {
        return starTime;
    }

    //public void setStarTime(Time starTime) {
        //this.starTime = starTime;
    //}

    public String getEndTime() {
        return endTime;
    }

    //public void setEndTime(Time endTime) {
        //this.endTime = endTime;
    //}
}
