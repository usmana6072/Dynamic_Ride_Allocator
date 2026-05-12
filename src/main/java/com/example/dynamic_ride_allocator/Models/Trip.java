package com.example.dynamic_ride_allocator.Models;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Trip implements Serializable,Comparable<Trip>{
    int tripID;
    String driverEmail;
    String riderEmail;
    String pickupLocation;       // ← store locations in trip too
    String dropOffLocation;
    double fare;
    boolean accepted;
    double distance;           // calculated by BFS
    String status;          // "completed", "cancelled", "ongoing"
    long time;

    public Trip(){

    }

    public Trip(int tripID, String driverEmail, String riderEmail, String pickupLocation, String dropOffLocation, double fare) {
        this.tripID = tripID;
        this.driverEmail = driverEmail;
        this.riderEmail = riderEmail;
        this.pickupLocation = pickupLocation;
        this.dropOffLocation = dropOffLocation;
        this.fare = fare;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getRiderEmail() {
        return riderEmail;
    }

    public void setRiderEmail(String riderEmail) {
        this.riderEmail = riderEmail;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(String dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int compareTo(@NotNull Trip o) {
        return Long.compare(this.time,o.time);
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
