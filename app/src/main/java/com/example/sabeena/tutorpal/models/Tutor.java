package com.example.sabeena.tutorpal.models;

/**
 * Created by SaBeeNa on 4/17/2017.
 */

public class Tutor {
    private String name;
    private String AC_Number;
    private String bankName;

    public Tutor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAC_Number() {
        return AC_Number;
    }

    public void setAC_Number(String AC_Number) {
        this.AC_Number = AC_Number;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
