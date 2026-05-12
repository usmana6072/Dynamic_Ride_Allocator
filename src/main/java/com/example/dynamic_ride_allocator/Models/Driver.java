package com.example.dynamic_ride_allocator.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Driver implements Serializable {
    private String  name;
    private String location;
    private boolean isAvailable;
    private boolean isApproved;
    private float rating;
    private String phone;
    private String email;           // to display on GUI
    private String vehicleType;     // "Car", "Bike", "Rickshaw"
    private String vehicleNumber;   // "ABC-123"
    private int totalTrips;
    private String password;
    private String address;
    private String licenceNumber;
    private double totalEarnings;
    private Trip currentRide;
    private double distance;
    private ArrayList<Trip> rideRequests=new ArrayList<>();
    boolean blocked;
    public Driver() {
    }

    public Driver( String name, String phone, String vehicleType, String vehicleNumber,String password) {
        this.name = name;
        this.phone = phone;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.password=password;
    }

    public double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public int getTotalTrips() {
        return totalTrips;
    }

    public void setTotalTrips(int totalTrips) {
        this.totalTrips = totalTrips;
    }

    public Trip getCurrentRide() {
        return currentRide;
    }

    public void setCurrentRide(Trip currentRide) {
        this.currentRide = currentRide;
    }

    public ArrayList<Trip> getRideRequests() {
        return rideRequests;
    }

    public void setRideRequests(ArrayList<Trip> rideRequests) {
        this.rideRequests = rideRequests;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
