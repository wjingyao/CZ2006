package com.example.groupprojectcmi;

public class registerUIFirebaseDataBase {

    String email1, password1, username1, plateNumber1, phoneNumber1;

    public registerUIFirebaseDataBase() {
    }

    public registerUIFirebaseDataBase(String email1, String password1, String username1, String plateNumber1, String phoneNumber1) {
        this.email1 = email1;
        this.password1 = password1;
        this.username1 = username1;
        this.plateNumber1 = plateNumber1;
        this.phoneNumber1 = phoneNumber1;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getPlateNumber1() {
        return plateNumber1;
    }

    public void setPlateNumber1(String plateNumber1) {
        this.plateNumber1 = plateNumber1;
    }

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }
}
