package com.example.sabeena.tutorpal.models;

import java.util.ArrayList;

/**
 * Created by SaBeeNa on 3/4/2017.
 */

public class Child {

    private String name;
    private String gender;
    private int ID;
    private ArrayList<TuitionClass> tuitions =  new ArrayList<>();
    private int noOfClasses;

    public Child( String name, String gender) {

        this.name = name;
        this.gender = gender;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getNoOfClasses() {
        return noOfClasses;
    }

    public void setNoOfClasses(int noOfClasses) {
        this.noOfClasses = noOfClasses;
    }

    public ArrayList<TuitionClass> getTuitions() {
        return tuitions;
    }

    public void setTuitions(TuitionClass t) {
        tuitions.add(t);
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }
}
