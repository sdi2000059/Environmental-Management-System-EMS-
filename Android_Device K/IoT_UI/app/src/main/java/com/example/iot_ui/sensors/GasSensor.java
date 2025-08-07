package com.example.iot_ui.sensors;

import androidx.annotation.NonNull;

public class GasSensor {

    private static double gas_val;
    private static boolean sensor_on;
    private static GasSensor instance;

    // Constructor
    public GasSensor() {
        gas_val = 0.0;
        sensor_on = false;
    }

    public GasSensor(double val, boolean on) {
        gas_val = val;
        sensor_on = on;
    }

    // Static method to provide the Singleton instance
//    public static GasSensor getInstance() {
//        if (instance == null) {
//            synchronized (GasSensor.class) {
//                if (instance == null) {
//                    instance = new GasSensor();
//                }
//            }
//        }
//        return instance;
//    }

    // Method to update gas value
    public void updateGasValue(double newVal) {
        gas_val = newVal;
    }

    // Method to update sensor status
    public void setSensorStatus(boolean status) {
        sensor_on = status;
    }

    // Getter method for gas value
    public static double getGasValue() {
        return gas_val;
    }

    // Getter method for sensor status
    public static boolean isSensorOn() {
        return sensor_on;
    }

}
