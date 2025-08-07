package main.sensors;
import java.util.Random;
public class SensorValuesGenerator {
    private static final Random random = new Random();
    static String prefix_lat = "37.96";
    static String prefix_lon = "23.76";
    public static int therm_max_value = 86;
    public static float smoke_max_value = 0.25F;
    public static float gas_max_value = 11F;
    public static int UV_max_value = 12;

    // #############################################################
    // HOW TO take samples every second !!!!!!!
    // #############################################################
    public static String getRandomLatitude() {
        return prefix_lat + random.nextDouble();
    }
    public static String getRandomLongitude() {
        return prefix_lon + random.nextDouble();
    }
    public static float generateRandomTemperature() {
        return random.nextFloat()*therm_max_value ;
    }
    public static float generateRandomSmokeLevel() {
        return random.nextFloat() * smoke_max_value;
    }

    public static float generateRandomGasLevel() {
        return random.nextFloat() * gas_max_value;
    }

    public static float generateRandomUVLevel() {
        return random.nextFloat() * UV_max_value;
    }

}
