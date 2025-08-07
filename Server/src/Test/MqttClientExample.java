import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttClientExample {
    public static void main(String[] args) {
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