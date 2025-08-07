package com.example.dangerdetector.data;

/**
 * Model representing the XML Tag TimeStep
 * */
public class TimeStep {
    private Vehicle vehicle;

    public TimeStep(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
