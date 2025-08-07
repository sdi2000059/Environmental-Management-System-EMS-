package com.example.iot_ui.utils;

public class Constants {
    public static String HOST_IP = "";
    public static String PORT = "1883";
    public static final String TOPIC = "EdgeServer/IoT/1";
    public static String tempTopic = "EdgeServer/IoT/1";
    public static String newTOPIC = "";
    public static String DeviceId = "";
    public static double BatteryLevel = 0.0;

    // From down bellow use for coordinates
    static class Coordinate {
        private static double x = 0.0;
        private static double y = 0.0;

        public Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public static double getX() {
            return x;
        }

        public static double getY() {
            return y;
        }
    }

    enum Position {
        POSITION_1(37.96809452684323, 23.76630586399502),
        POSITION_2(37.96799937191987, 23.766603589104385),
        POSITION_3(37.967779456380754, 23.767174897611685),
        POSITION_4(37.96790421900921, 23.76626294807113);

        private final double x;
        private final double y;

        Position(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    // Modify the method to return Coordinate
    public static String getPosition(int positionNumber) {
        switch (positionNumber) {
            case 1:
                new Coordinate(Position.POSITION_1.getX(), Position.POSITION_1.getY());
                return Coordinate.getX() +" "+  Coordinate.getY();
            case 2:
                new Coordinate(Position.POSITION_2.getX(), Position.POSITION_2.getY());
                return Coordinate.getX() +" "+ Coordinate.getY();
            case 3:
                new Coordinate(Position.POSITION_3.getX(), Position.POSITION_3.getY());
                return Coordinate.getX() +" "+ Coordinate.getY();
            case 4:
                new Coordinate(Position.POSITION_4.getX(), Position.POSITION_4.getY());
                return Coordinate.getX() +" "+ Coordinate.getY();
            default:
                throw new IllegalArgumentException("Invalid position number");
        }
    }

    public static void setTopic(int i) {
        newTOPIC = tempTopic + "/" + i;
    }
}
