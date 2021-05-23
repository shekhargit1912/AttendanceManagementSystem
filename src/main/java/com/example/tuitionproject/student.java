package com.example.tuitioproject;


public class student {
    public String name, std, contact, address, divison, cast, registorNo, dob;
    public student(){}

    public student(String studentnamename, String classs, String contact, String address, String division, String cast, String registorNo, String dob) {
        this.name = studentnamename;
        this.std = classs;
        this.contact = contact;
        this.address = address;
        this.divison = division;
        this.cast = cast;
        this.registorNo = registorNo;
        this.dob = dob;

    }

    public student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getStd() {
        return std;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getDivison() {
        return divison;
    }

    public String getCast() {
        return cast;
    }

    public String getRegistorNo() {
        return registorNo;
    }

    public String getDob() {
        return dob;
    }
}
