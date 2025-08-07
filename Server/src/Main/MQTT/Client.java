package MQTT;
//Client
//Resource: eclipse.org

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

//Class Client
public class Client {
    private static int WAITING_TIME = 10;
    private static int TIMEOUT = 0;
    protected static int QOS = 0;       //Qos=2 for no duplicates
    protected String broker;
    protected String Client_ID;
    protected MqttClient client;
    protected boolean verification;

    public Client(String broker, String Client_ID) {
        this.broker = broker;
        this.Client_ID = Client_ID;
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            client = new MqttClient(broker, Client_ID,persistence);
            verification = true;
        }
        catch (MqttException exception) {
            verification = false;
            System.out.println("Error " +this.Client_ID +" could not initialize");
        }
    }

    public void connection() {

        if (client.isConnected()) {
            System.out.println(this.Client_ID + " is already connected");
            return;
        }

        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(true); //cleaning the session
        connectOptions.setKeepAliveInterval(WAITING_TIME);

        try {
            System.out.println(this.Client_ID + " connecting to "+ this.broker);
            client.connect(connectOptions);

            System.out.println(this.Client_ID + " connected to "+ this.broker);
        }
        catch (MqttException exception) {
            System.out.println("Error " +this.Client_ID +" could not connect");
            System.out.println(exception);
        }
    }
    public void disconnect() {
        if (!client.isConnected()) {
            System.out.println(this.Client_ID + " is already disconnected");
            return;
        }
        try {
            client.disconnect(TIMEOUT);
            System.out.println(this.Client_ID + " disconnected from "+ this.broker);
        }
        catch (MqttException exception) {
            System.out.println("Error " +this.Client_ID +" could not disconnect");
            System.out.println(exception);
        }
    }

    public void close() {
        try {
            client.close(true);
            System.out.println(this.Client_ID+ ": Closed");
        } catch (MqttException exception) {
            System.out.println("Error "+ this.Client_ID+ "could not close");
            System.out.println(exception);
        }
    }

    public boolean isConnected() {
        return client.isConnected();
    }

    public boolean isVerified() {
        return verification;
    }
}
