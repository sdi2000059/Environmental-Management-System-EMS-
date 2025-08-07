import MQTT.Client;
import MQTT.Configuration;
import MQTT.Data.dataToSend;
import MQTT.Data.received_android_data;
import Processes.Calculator.CreateJson;
import Processes.Calculator.DangerDegreeCalc;
import Processes.Calculator.Distance_calc;
import xml.XMLRead;
import com.google.maps.errors.ApiException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ApiException {


        
        // Αρχικοποίηση και σύνδεση
        Configuration.publisher.connection();
        Configuration.subscriber.connection();
        Configuration.database.ConnectToDB("root","firesolaris61093");

        // Εκκίνηση του νήματος του Publisher

        Configuration.subscriber.Subscribe("EdgeServer/IoT/1");
        Configuration.subscriber.Subscribe("android_client/gps");
        Configuration.subscriber.Subscribe("EdgeServer/IoT/2");

        // Εκκίνηση του νήματος δημοσίευσης μία φορά εδώ
        Configuration.publisher.startThread();

        // Συνδρομή σε θέμα
        // Η Publish γίνεται μέσω της run() του Publisher που τρέχει σε ξεχωριστό thread
        // Παράδειγμα προσθήκης δεδομένων στην ουρά του Publisher
        // Configuration.publisher.insertData(new dataToSend(...));

        // Δώστε λίγο χρόνο για επεξεργασία
        try {
            Thread.sleep(500000); // Καθυστέρηση για να επιτρέψετε την παραλαβή μηνυμάτων
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Τερματισμός και κλείσιμο συνδέσεων
        Configuration.publisher.stopThread();
        Configuration.publisher.disconnect();
        Configuration.subscriber.disconnect();


    }
}

