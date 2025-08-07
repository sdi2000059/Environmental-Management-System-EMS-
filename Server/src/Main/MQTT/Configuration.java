package MQTT;

import DB.*;
import MQTT.Publisher;
import MQTT.Subscriber;

public class Configuration {
    public static String broker_IP = "broker.emqx.io";


    public static String broker_Port = "1883";
    public static String broker="tcp://" + broker_IP + ":" + broker_Port;
    public static Publisher publisher=new Publisher(broker, "EdgeServerPublisher");
    public static Subscriber subscriber=new Subscriber(broker, "EdgeServerSubscriber");
    public static ConnectionToDB database=new ConnectionToDB("127.0.0.1", "Events_db");


    }