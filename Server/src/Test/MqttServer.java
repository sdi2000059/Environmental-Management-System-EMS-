import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MqttServer {
    public static void main(String[] args) throws InterruptedException {

        //        String broker = "mqtt://192.168.1.49:1883";
        String broker = "tcp://127.0.0.1:1883"; // Localhost IP
        String clientId = "JavaClient";
        String topic = "data/xml";

        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient mqttClient = new MqttClient(broker, clientId, persistence);

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

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

                }
            });

            mqttClient.subscribe(topic);
            try{
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Subscribed to topic: " + topic);


            // Disconnect
            mqttClient.disconnect();
            System.out.println("Disconnected");
        } catch (MqttException me) {
            System.out.println("Error: " + me.getMessage());
            me.printStackTrace();
        }
    }
}
