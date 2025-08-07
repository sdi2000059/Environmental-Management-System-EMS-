package MQTT;

//Subscriber

import Processes.Calculator.Calculator;
import MQTT.Data.dataToSend;
import MQTT.Data.received_IoT_data;
import MQTT.Data.received_android_data;
import Processes.Calculator.CreateJson;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.util.concurrent.LinkedBlockingQueue;



//Class Subscriber
public class Subscriber extends Client implements MqttCallback {
    private static final long POLL_TIMEOUT = 1; // in seconds
    private final LinkedBlockingQueue<received_android_data> received_android; //edge receives data from android devices
    private final LinkedBlockingQueue<received_IoT_data> received_IoT1; //and also IoT1 device
    private final LinkedBlockingQueue<received_IoT_data> received_IoT2; //and also IoT2 device
    private boolean IoT_1 = false;
    private boolean IoT_2 = false;
    private boolean android = false;
    private final LinkedBlockingQueue<received_IoT_data> used_IoT1; //and also IoT1 device
    private final LinkedBlockingQueue<received_IoT_data> used_IoT2; //and also IoT2 device
    int flag = 0;

    String IoT1_Final_message ="";
    String IoT2_Final_message ="";
    int IoT1_count =0;
    int IoT2_count =0;

    public Subscriber(String broker, String Client_ID) {

        super(broker, Client_ID);
        System.out.println("Initializing subscriber " + Client_ID);
        super.client.setCallback(this);
        received_android = new LinkedBlockingQueue<>();
        received_IoT1 = new LinkedBlockingQueue<>();
        received_IoT2 = new LinkedBlockingQueue<>();
        used_IoT1 = new LinkedBlockingQueue<>();
        used_IoT2 = new LinkedBlockingQueue<>();

    }

    public void Subscribe(String topic) {
        try {
            client.subscribe(topic, Client.QOS);
            System.out.println(Client_ID + ": Subscribed to \"" + topic + "\"");
        }
        catch (MqttException exception) {
            System.out.println("Error " + Client_ID +" could not subscribe");
            System.out.println(exception);
        }
    }
    public received_android_data retrieve_android_Data() {
        try {
            return received_android.take(); //Retrieves and removes the head of this queue, waiting up to the specified wait time if necessary for an element to become available.
        } catch (InterruptedException exception) {
            return null;
        }
    }

    public received_IoT_data retrieve_IoT1_Data() {
      //  try {
            return received_IoT1.poll(); //Retrieves and removes the head of this queue, waiting up to the specified wait time if necessary for an element to become available.
       // }
//        catch (InterruptedException exception) {
//            return null;
//        }
    }
    public received_IoT_data retrieve_IoT2_Data() {
       // try {
            return received_IoT2.poll(); //Retrieves and removes the head of this queue, waiting up to the specified wait time if necessary for an element to become available.
      //  }
//        catch (InterruptedException exception) {
//            return null;
//        }
    }
    public void connectionLost(Throwable throwable) {
        System.out.println(Client_ID + ": Connection lost");
    }


    public void messageArrived(String topic, MqttMessage mqttMessage) throws InterruptedException {

        String message = new String(mqttMessage.getPayload());

        //figuring out what type of data was sent, android or IoT
//        "android_client/gps";
        if (topic.contains("android_client/gps")) {
//            ID: dd255288aae37675/ x: 37.96790421900921/y: 23.76626294807113
            System.out.println(Client_ID + " received android data to Topic: " + topic + ", Message: " + message);//then the message received is from an android device
            received_android_data data = received_android_data.getData(message);
            received_android.add(data);
            android = true;


        } else if (topic.contains("EdgeServer/IoT/1")) {
            System.out.println(Client_ID + " received IoT1 data to Topic: " + topic + ", Message: " + message);//then the message received is from an IoT device

            IoT1_Final_message = IoT1_Final_message +"/"+ message;
            System.out.println(IoT1_Final_message);
            IoT1_count++;
            received_IoT_data data = received_IoT_data.getData(IoT1_Final_message);
            if (data.smoke_flag && data.gas_flag && data.x_flag && data.y_flag) {
                IoT1_Final_message = "";
                IoT1_count =0;
                received_IoT1.add(data);
                IoT_1 = true;
            }
        } else if (topic.contains("EdgeServer/IoT/2")) {
            System.out.println(Client_ID + " received IoT2 data to Topic: " + topic + ", Message: " + message);//then the message received is from an IoT device

            IoT2_Final_message = IoT2_Final_message +"/"+ message;
            System.out.print(IoT2_Final_message);
            IoT2_count++;
            received_IoT_data data = received_IoT_data.getData(IoT2_Final_message);
            if (data.smoke_flag && data.gas_flag && data.x_flag && data.y_flag) {
                IoT2_Final_message = "";
                IoT2_count =0;
                received_IoT2.add(data);
                IoT_2 = true;
            }
        }
        if (android ) {

            Calculator calc = new Calculator();

            CreateJson json = new CreateJson();
            dataToSend sending_data = new dataToSend();
            double distance = -2;
            if (IoT_1 && IoT_2) {
                System.out.println("Exoume Data kai sta 2 IoT");
                received_IoT_data IoT1_Data = retrieve_IoT1_Data();
                received_IoT_data IoT2_Data = retrieve_IoT2_Data();

                if (IoT1_Data == null) {
                    IoT1_Data = used_IoT1.peek();
                }
                if (IoT2_Data == null) {
                    IoT2_Data = used_IoT2.peek();
                }

                if (!used_IoT1.isEmpty()) {
                    used_IoT1.take();
                    IoT_1 = false;
                    flag = 1;
                }
                used_IoT1.add(IoT1_Data);

                if (!used_IoT2.isEmpty()) {
                    used_IoT2.take();
                    IoT_2 = false;
                    flag = 1;
                }
                used_IoT2.add(IoT2_Data);

                received_android_data androidData = retrieve_android_Data();
                if (androidData != null) {

                    if (IoT1_Data != null && IoT2_Data != null) {
                        android = false;
                        IoT_1 = false;
                        IoT_2 = false;
                        sending_data = calc.DangerFromTwoDevices(androidData, IoT1_Data, IoT2_Data, flag);

                        Configuration.database.InsertEvent(IoT1_Data, Configuration.database.con, sending_data.Alert);
                        Configuration.database.InsertEvent(IoT2_Data, Configuration.database.con, sending_data.Alert);
                        if (sending_data.IoT1_Danger.equals("No Danger") && sending_data.IoT2_Danger.equals("No Danger")){
                            json.json_creation(androidData.x,androidData.y,IoT1_Data.x,IoT1_Data.y,IoT2_Data.x,IoT2_Data.y, 0, 0,1,1);
                        }
                        else if(sending_data.IoT1_Danger.equals("No Danger")){
                            json.json_creation(androidData.x,androidData.y,IoT1_Data.x,IoT1_Data.y,IoT2_Data.x,IoT2_Data.y, 0, 1,1,1);
                        }
                        else{
                            json.json_creation(androidData.x,androidData.y,IoT1_Data.x,IoT1_Data.y,IoT2_Data.x,IoT2_Data.y, 1, 0,1,1);
                        }


                    } else if (IoT1_Data != null) {
                        android = false;
                        IoT_1 = false;
                        sending_data = calc.DangerFromOneDevice(androidData, IoT1_Data, flag);
                        Configuration.database.InsertEvent(IoT1_Data, Configuration.database.con, sending_data.Alert);
                        if(sending_data.IoT1_Danger.equals("No Danger")){
                            json.json_creation(androidData.x,androidData.y,IoT1_Data.x,IoT1_Data.y,37.96749937191987,23.76404589104385, 0, 0,1,0);
                        }
                        else{
                            json.json_creation(androidData.x,androidData.y,IoT1_Data.x,IoT1_Data.y,37.96749937191987,23.76404589104385, 1, 0,1,0);
                        }

                    } else if (IoT2_Data != null) {
                        android = false;
                        IoT_2 = false;
                        sending_data = calc.DangerFromOneDevice(androidData, IoT2_Data, flag);
                        Configuration.database.InsertEvent(IoT2_Data, Configuration.database.con, sending_data.Alert);
                        if(sending_data.IoT2_Danger.equals("No Danger")){
                            json.json_creation(androidData.x,androidData.y,37.96749937191987,23.76404589104385,IoT2_Data.x,IoT2_Data.y, 0, 0,0,1);
                        }
                        else{
                            json.json_creation(androidData.x,androidData.y,37.96749937191987,23.76404589104385,IoT2_Data.x,IoT2_Data.y, 0, 1,0,1);
                        }

                    }
                }

            } else {

                if (IoT_1) {
//                if (IoT_2 == false) {
                    System.out.println("IoT_1 Connected");

                    received_IoT_data IoT1_Data = retrieve_IoT1_Data();
                    if (IoT1_Data == null) {
                        IoT1_Data = used_IoT1.peek();
                        IoT_1 = false;
                    }

                    if (!used_IoT1.isEmpty()) {
                        used_IoT1.take();

                        flag = 1;

                    }
                    used_IoT1.add(IoT1_Data);
                    //  }
                    System.out.println(" ");
                    received_android_data androidData = retrieve_android_Data();
                    if (androidData != null) {
                        if (IoT1_Data != null) {
                            IoT_1 = false;
                            android = false;
                            sending_data = calc.DangerFromOneDevice(androidData, IoT1_Data, flag);
                            Configuration.database.InsertEvent(IoT1_Data, Configuration.database.con, sending_data.Alert);
                            if(sending_data.IoT1_Danger.equals("No Danger")){
                                json.json_creation(androidData.x,androidData.y,IoT1_Data.x,IoT1_Data.y,37.96749937191987,23.76404589104385, 0, 0,1,0);
                            }
                            else{
                                json.json_creation(androidData.x,androidData.y,IoT1_Data.x,IoT1_Data.y,37.96749937191987,23.76404589104385, 1, 0,1,0);
                            }
                        }
                    }

                } else if (IoT_2) {
                    System.out.println("IoT2_connected");


                    received_IoT_data IoT2_Data = retrieve_IoT2_Data();
                    if (IoT2_Data == null) {
                        IoT2_Data = used_IoT2.peek();
                        IoT_2 = false;
                    }

                    if (!used_IoT2.isEmpty()) {
                        used_IoT2.take();
                        flag = 1;
                    }
                    used_IoT2.add(IoT2_Data);
                    //  }
                    received_android_data androidData = retrieve_android_Data();
                    if (androidData != null) {
                        if (IoT2_Data != null) {
                            IoT_2 = false;
                            android = false;
                            sending_data = calc.DangerFromOneDevice(androidData, IoT2_Data, flag);
                            Configuration.database.InsertEvent(IoT2_Data, Configuration.database.con, sending_data.Alert);
                            if(sending_data.IoT2_Danger.equals("No Danger")){
                                json.json_creation(androidData.x,androidData.y,37.96749937191987,23.76404589104385,IoT2_Data.x,IoT2_Data.y, 0, 0,0,1);
                            }
                            else{
                                json.json_creation(androidData.x,androidData.y,37.96749937191987,23.76404589104385,IoT2_Data.x,IoT2_Data.y, 0, 1,0,1);
                            }
                        }

                    }
                }


            }
            System.out.println("Check");

            if (!sending_data.getAndroid_Id().isEmpty()) {

                Configuration.publisher.insertData(sending_data);
            }


        }


    }
    public void deliveryComplete (IMqttDeliveryToken iMqttDeliveryToken){
    }



}
