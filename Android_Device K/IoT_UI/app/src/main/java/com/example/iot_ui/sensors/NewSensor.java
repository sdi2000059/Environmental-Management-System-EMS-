package com.example.iot_ui.sensors;

import com.example.iot_ui.fragments.NewSensorFragment;

public class NewSensor {

    private static double gas_val;
    private static boolean sensor_on;
    private static NewSensor instance;
    private static int counter = 0;

    private static NewSensorFragment fragment;

    // Constructor
    public NewSensor() {
        gas_val = 0.0;
        sensor_on = false;
    }

    public NewSensor(double val, boolean on) {
        gas_val = val;
        sensor_on = on;
    }

    // Static method to provide the Singleton instance
//    public static NewSensor getInstance() {
//        if (instance == null) {
//            synchronized (GasSensor.class) {
//                if (instance == null) {
//                    instance = new NewSensor();
//                }
//            }
//        }
//        return instance;
//    }

    public void createAddSensor(String inputName, String val1, String val2) {
        // Get the length of the array
        String name = inputName + counter;
        counter++;
        fragment = new NewSensorFragment(name, val1, val2);
    }

    public static NewSensorFragment getFragment() {
        return fragment;
    }

    // Method to update gas value
    public void updateGasValue(double newVal) {
        gas_val = newVal;
    }

    // Method to update sensor status
    public void setSensorStatus(boolean status) {
        sensor_on = status;
    }

    // Getter method for gas value
    public double getGasValue() {
        return gas_val;
    }

    // Getter method for sensor status
    public boolean isSensorOn() {
        return sensor_on;
    }
}
