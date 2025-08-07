package Processes.Calculator;

public class Distance_calc {

    //calculating the distance between two points, using the code from the link above
    public double DistanceBetweenTwoPoints(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;

            dist = dist * 1.609344;

            return (dist);
        }
    }

    // functions in order to find the midpoint(mid-longitude, mid-latitude) of the distance between two IoT devices that cause an event simultaneously
    public double CenterOfDistanceLongitude(double lon1, double lon2){
        return (lon1 + lon2)/2;
    }

    public double CenterOfDistanceLatitude(double lat1, double lat2){
        return (lat1 + lat2)/2;
    }
}
