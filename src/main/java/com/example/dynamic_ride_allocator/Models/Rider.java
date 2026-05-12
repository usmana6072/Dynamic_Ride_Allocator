package com.example.dynamic_ride_allocator.Models;

import java.io.Serializable;

public class Rider implements Serializable {
    private String name;
    private String location;
    private String phone;
    private String email;
    private String password;
    private String address;
    private boolean approved;
    private boolean blocked;
    private Trip currentRide;

    public Rider( String name, String phone, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Trip getCurrentRide() {
        return currentRide;
    }

    public void setCurrentRide(Trip currentRide) {
        this.currentRide = currentRide;
    }
}
