package com.example.tuitioproject;

public class Staff {
    public String staffname, staffcontact, staffadrress, staffqulification, username, password, id, allocatedStd, allocatedDiv;

    public Staff() {
    }

    public Staff(String id, String staffname, String staffcontact, String staffaddress, String staffqlification, String username, String password, String allocatedStd, String allocatedDiv) {
        this.id = id;
        this.staffname = staffname;
        this.staffcontact = staffcontact;
        this.staffadrress = staffaddress;
        this.staffqulification = staffqlification;
        this.username = username;
        this.password = password;
        this.allocatedStd = allocatedStd;
        this.allocatedDiv = allocatedDiv;
    }

    public String getId() {

        return id;
    }

    public String getStaffname() {
        return staffname;
    }

    public String getStaffcontact() {
        return staffcontact;
    }

    public String getStaffadrress() {
        return staffadrress;
    }

    public String getStaffqulification() {
        return staffqulification;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAllocatedStd() {
        return allocatedStd;
    }

    public String getAllocatedDiv() {
        return allocatedDiv;
    }
}
