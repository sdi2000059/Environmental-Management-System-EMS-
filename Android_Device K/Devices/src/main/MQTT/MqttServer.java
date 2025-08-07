package main.MQTT;

import org.eclipse.paho.client.mqttv3.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MqttServer {

    public static void receiveXml(String broker, String clientId, String username, String password, String topic, String savePath)
            throws MqttException, IOException {
        MqttConnectionManager connectionManager = new MqttConnectionManager(broker, clientId, username, password);
        connectionManager.connect();

        // Subscribe to the topic
        connectionManager.getMqttClient().subscribe(topic, (IMqttMessageListener) new MqttXmlMessageCallback(savePath));

        // Keep the application running to receive messages
        try {
            Thread.sleep(60000); // Adjust as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        connectionManager.disconnect();
    }

    public static void main(String[] args) {
        try {
            //String broker = ""; //use your ip like: tcp://ip_here:1883
            String broker = "tcp://192.168.1.4:1883"; // Localhost IP
            String clientId = "JavaMqttReceiver";
            String username = ""; // Replace with your MQTT broker username if you have
            String password = ""; // Replace with your MQTT broker password if you have
            String topic = "data/xml";
            String savePath = "src/main/java/MQTT";

            receiveXml(broker, clientId, username, password, topic, savePath);

        } catch (MqttException | IOException e) {
            e.printStackTrace();
        }
    }
}

class MqttXmlMessageCallback implements MqttCallback {

    private final String savePath;

    public MqttXmlMessageCallback(String savePath) {
        this.savePath = savePath;
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection to MQTT broker lost!");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {
            // Save the received XML file to the specified path
            Files.write(Paths.get(savePath), message.getPayload());
            System.out.println("XML file received and saved at: " + savePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Not used for a subscriber
    }
}

class MqttConnectionManager {

    private final MqttClient mqttClient;

    public MqttConnectionManager(String broker, String clientId, String username, String password) throws MqttException {
        mqttClient = new MqttClient(broker, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        mqttClient.connect(options);
    }

    public void connect() throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        mqttClient.connect(options);
        System.out.println("Connected to MQTT broker.");
    }

    public void disconnect() throws MqttException {
        mqttClient.disconnect();
        System.out.println("Disconnected from MQTT broker.");
    }

    public MqttClient getMqttClient() {
        return mqttClient;
    }
}
