package com.example.iot_ui.sensors;

public class TempSensor {

    private static double temp_val = 0.0;
    private static boolean sensor_on = false;

    private static TempSensor instance;

    // Constructor
    public TempSensor() {
        temp_val = 0.0;
        sensor_on = false;
    }

    // Static method to provide the Singleton instance
//    public static TempSensor getInstance() {
//        if (instance == null) {
//            synchronized (GasSensor.class) {
//                if (instance == null) {
//                    instance = new TempSensor();
//                }
//            }
//        }
//        return instance;
//    }

    public TempSensor(double val, boolean on) {
        temp_val = val;
        sensor_on = on;
    }

    // Method to update gas value
    public void updateTempValue(double newVal) {
        temp_val = newVal;
    }

    // Method to update sensor status
    public void setSensorStatus(boolean status) {
        sensor_on = status;
    }

    // Getter method for gas value
    public static double getTempValue() {
        return temp_val;
    }

    // Getter method for sensor status
    public static boolean isSensorOn() {
        return sensor_on;
    }
}
