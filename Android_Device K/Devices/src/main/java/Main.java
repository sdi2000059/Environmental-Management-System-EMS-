import main.IoT.IoTData;
import main.IoT.JavaToXml;
import java.io.File;
import java.util.logging.ErrorManager;
import java.util.Random;
import main.IoT.XmlToJava;
//import main.MQTT.mqttConnection;
//import main.MQTT.mqttConnection;

public class Main {
    private static final ErrorManager LOGGER = null;
    public static void main(String[] args) {
        String topic = "RoomA/Sensor01";
        String broker = "tcp://localhost:1883/";
        String ad1 = "android_1.xml";
        String ad2 = "android_2.xml";

        //Create connection
//        mqttConnection con01 = new mqttConnection();
//        con01.mqttConnection(broker, IoT_id);
//        //con01.mqttMessage(topic, con01.toString(IoT_id, latitude, longitude));
//        //connect
//        con01.connect();
//
//        con01.run();
//
//        //disconnect
//        con01.disconnect();

        Random rand = new Random();
        int randomNumber = rand.nextInt(2);
        String xmlfile = (randomNumber == 0) ? ad1 : ad2;
        System.out.println("The randomly selected file to get coordinates from is: " + xmlfile);

        IoTData iotdata = new IoTData();

//        iotdata.set_temperature(SensorValuesGenerator.generateRandomTemperature());
//        iotdata.set_gasLevel(SensorValuesGenerator.generateRandomGasLevel());
//        iotdata.set_smokelevel(SensorValuesGenerator.generateRandomSmokeLevel());
//        iotdata.set_UVlevel(SensorValuesGenerator.generateRandomUVLevel());

        //Read the randomly picked xml file(android_.xml or android_2.xml) and take x, y and id or create random x and y
        XmlToJava Xmlconvertion = new XmlToJava();
        Xmlconvertion.FileRead(xmlfile);

        //Create the xml file that needs to be send to the broker
        JavaToXml Javaconvertion = new JavaToXml();
        Javaconvertion.ConvertionJavatoXml(xmlfile);
        //Finding errors
        xmlFind();
    }

    public static void xmlFind() {
        File file = new File("src/main/resources/IoTData.xml");
        if (file.isFile()) {
            System.out.println("File was received!");
        } else {
            System.out.println("Something went wrong !");
             //LOGGER.error("File does not exist or it cannot be received. File: sensorsData.xml");
        }
    }

}