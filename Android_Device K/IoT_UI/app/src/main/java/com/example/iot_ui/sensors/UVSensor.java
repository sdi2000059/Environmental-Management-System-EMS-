package com.example.iot_ui.sensors;

public class UVSensor {

    private static double uv_val = 0.0;
    private static boolean sensor_on = false;
    private static UVSensor instance;

    // Constructor
    public UVSensor() {
        uv_val = 0.0;
        sensor_on = false;
    }

    public UVSensor(double val, boolean on) {
        uv_val = val;
        sensor_on = on;
    }

    // Static method to provide the Singleton instance
//    public static UVSensor getInstance() {
//        if (instance == null) {
//            synchronized (GasSensor.class) {
//                if (instance == null) {
//                    instance = new UVSensor();
//                }
//            }
//        }
//        return instance;
//    }

    // Method to update gas value
    public void updateUvValue(double newVal) {
        uv_val = newVal;
    }

    // Method to update sensor status
    public void setSensorStatus(boolean status) {
        sensor_on = status;
    }

    // Getter method for gas value
    public static double getUvValue() {
        return uv_val;
    }

    // Getter method for sensor status
    public static boolean isSensorOn() {
        return sensor_on;
    }
}
