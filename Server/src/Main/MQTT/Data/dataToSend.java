package MQTT.Data;

public class dataToSend {

    public String Alert = "No Danger";
    public double distance = 0;

    public String Android_id ="";

    public String IoT1_Danger = "No Danger";
    public String IoT2_Danger = "No Danger";

    public String getAndroid_Id(){


        return Android_id;

    }
    public String getAlert_message(){

        return Alert;

    }
    public double getDistance(){

        return distance;

    }
}


