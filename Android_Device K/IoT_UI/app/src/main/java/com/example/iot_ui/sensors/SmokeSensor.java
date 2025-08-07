package com.example.iot_ui.sensors;

public class SmokeSensor {

    private static double smoke_val = 0.0;
    private static boolean sensor_on = false;
    private static SmokeSensor instance;

    // Constructor
    public SmokeSensor() {
        smoke_val = 0.0;
        sensor_on = false;
    }

    public SmokeSensor(double val, boolean on) {
        smoke_val = val;
        sensor_on = on;
    }

    // Static method to provide the Singleton instance
//    public static SmokeSensor getInstance() {
//        if (instance == null) {
//            synchronized (GasSensor.class) {
//                if (instance == null) {
//                    instance = new SmokeSensor();
//                }
//            }
//        }
//        return instance;
//    }

    // Method to update gas value
    public void updateSmokeValue(double newVal) {
        smoke_val = newVal;
    }

    // Method to update sensor status
    public void setSensorStatus(boolean status) {
        sensor_on = status;
    }

    // Getter method for gas value
    public static double getSmokeValue() {
        return smoke_val;
    }

    // Getter method for sensor status
    public static boolean isSensorOn() {
        return sensor_on;
    }
}
