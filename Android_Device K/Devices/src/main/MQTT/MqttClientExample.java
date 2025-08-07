package main.MQTT;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttClientExample {
    public static void main(String[] args) {
        //String broker = "tcp://mqtt.eclipse.org:1883";
        String broker = "tcp://192.168.1.4:1883"; // Localhost IP
        String clientId = "JavaClient";
        String topic = "data/xml";
        String username = "";
        String password = "";

        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient mqttClient = new MqttClient(broker, clientId, persistence);

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            // Set username and password
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());

            System.out.println("Connecting to broker: " + broker);
            mqttClient.connect(connOpts);
            System.out.println("Connected");

            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Message received:\n\tTopic: " + topic + "\n\tMessage: " + new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // Not used in this example
                }
            });

            mqttClient.subscribe(topic);
            System.out.println("Subscribed to topic: " + topic);

            // Publish a test message
            String content = "Hello, MQTT!";
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(2); // QoS level: 0, 1, or 2
            mqttClient.publish(topic, message);

            // Disconnect
            mqttClient.disconnect();
            System.out.println("Disconnected");
        } catch (MqttException me) {
            System.out.println("Error: " + me.getMessage());
            me.printStackTrace();
        }
    }
}
