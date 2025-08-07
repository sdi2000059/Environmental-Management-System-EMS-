package main.MQTT;

import main.sensors.SensorValuesGenerator;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class mqttConnection {
    private static MqttClient client;
    private String broker;
    private String client_id;

    private String topic;
    private  String message;
    MemoryPersistence persistence = new MemoryPersistence();

    //constructor
    public void mqttConnection(String broker, String client_id){
        this.broker = broker;
        this.client_id = client_id;
    }

    public void mqttMessage(String topic, String message){
        this.topic = topic;
        this.message = message;
    }
    public void connect(){
        try{
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);
            connectOptions.setKeepAliveInterval(1000);
            //connection with broker
            client.connect(connectOptions);
        }catch(MqttException e){
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try{
            client.disconnect();
        }catch(MqttException e){
            e.printStackTrace();
        }
    }

    public void publish(){
        try{
            // instead of getBytes we need to send xml files !!!
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            client.publish(topic, mqttMessage);
        }catch(MqttException e){
            e.printStackTrace();
        }
    }

    /*    private void sendDataToBroker(double temperature, double gasLevel, double latitude, double longitude) {

        message = "Temperature: " + temperature + "°C, Gas Level: " + gasLevel + "ppm, Latitude: " + latitude + ", Longitude: " + longitude;
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        try {
            client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }*/

    public void run() {
        //String broker = "tcp://localhost:1883";
        //String topicName = "RoomA/sensor01";
        int qos = 3;

        String IoT_id = "IoT_01";
        float temperature = SensorValuesGenerator.generateRandomTemperature();
        float gasLevel = SensorValuesGenerator.generateRandomGasLevel();
        float SmokeLevel = SensorValuesGenerator.generateRandomSmokeLevel();
        float UVLevel = SensorValuesGenerator.generateRandomUVLevel();

        // add coordinates from xml file !!!
        // GPS coordinates
        String latitude = SensorValuesGenerator.getRandomLatitude();
        String longitude = SensorValuesGenerator.getRandomLongitude();

        //sendDataToBroker(temperature, gasLevel, latitude, longitude);

        message = "Report from IoT device with id:"+ IoT_id +" is: Temperature: " + temperature + "°C, Gas Level: " + gasLevel + ", Smoke Level: "+SmokeLevel+ ", UVLevel: "+UVLevel+", Latitude: " + latitude + ", Longitude: " + longitude;
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());

        mqttMessage.setQos(qos);
        mqttMessage.setRetained(true);

        try {
            client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
    public  String toString(String device_id, String latitude, String longitude){
        return device_id+" "+latitude+" "+longitude;
    }

}
