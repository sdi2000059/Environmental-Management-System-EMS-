package MQTT.Data;

import static java.lang.Float.parseFloat;
import static java.lang.Double.parseDouble;

public class received_android_data {

    public String id ="6828a43c-bca5-4595-b306-bfd171699d39";
    public double x;
    public double y;
    public float battery;


    public static received_android_data getData(String message) {

        //spaei to message ID: dd255288aae37675/ x: 37.96790421900921/y: 23.76626294807113
        received_android_data data = new received_android_data();

        if (message.contains("Battery:")){

            String[] SeperatebyID = message.split("Battery:");
            String[] SeperatebyDel = SeperatebyID[1].split("/");
            String Battery = SeperatebyDel[0];
            data.battery = parseFloat(Battery);

        }
        if (message.contains("ID:")){

            String[] Seperate = message.split("ID:");
            String[] SeperatebyDel = Seperate[1].split("/");
            data.id = SeperatebyDel[0];
        }
        if (message.contains("X:")){

            String[] Seperate = message.split("X:");
            String[] SeperatebyDel = Seperate[1].split("/");
            String X = SeperatebyDel[0];
            data.x = parseDouble(X);
        }
        if (message.contains("Y:")){

            String[] Seperate = message.split("Y:");
            String[] SeperatebyDel = Seperate[1].split("/");
            String Y = SeperatebyDel[0];
            data.y = parseDouble(Y);
        }
        return data;
    }
}
