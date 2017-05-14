package com.example.sabeena.tutorpal.models;

import android.location.Location;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by SaBeeNa on 3/4/2017.
 */

public class TuitionClass {

    private int childID;
    private int tuitionID;
    private String subject;
    private ArrayList<Day> day = new ArrayList<Day>();
    private String tutorName;
    private String tutorACNumber;
    private String location;
    private Double tuitionFee;
    private Calendar startTime;
    private Calendar endTime;

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public TuitionClass(int childID) {
        this.childID = childID;
    }

    public int getTuitionID() {
        return tuitionID;
    }

    public void setTuitionID(int tuitionID) {
        this.tuitionID = tuitionID;
    }

    public int getChildID() {
        return childID;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getTutorACNumber() {
        return tutorACNumber;
    }

    public void setTutorACNumber(String tutorACNumber) {
        this.tutorACNumber = tutorACNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Day getDay() {
        return day.get(0);
    }

    public void setDay(Day day) {
        this.day.add(day);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(Double tuitionFee) {
        this.tuitionFee = tuitionFee;
    }
}
