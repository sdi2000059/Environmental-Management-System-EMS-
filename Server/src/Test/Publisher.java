//package Mqtt;
//
//// publisher
//import Data.dataToSend;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//import java.util.concurrent.LinkedBlockingQueue;
////Class Publisher
//public class Publisher extends Client implements Runnable {
//    private LinkedBlockingQueue<dataToSend> sending_data; //the data that will be sent from the Edge server to the android device are: alert, distance
//    private Thread thread;
//    private volatile boolean thread_status;
//
//    public Publisher(String broker, String Client_ID) {
//        super(broker, Client_ID);
//        sending_data = new LinkedBlockingQueue(); //fifo
//        thread = new Thread(this);
//        thread_status = false;
//    }
//
//    public void Publish(String topic, String message) {
//        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
//        mqttMessage.setQos(Client.QOS);
//
//        try {
//            client.publish(topic, mqttMessage);
//            System.out.println(Client_ID+ " published to topic: "+ topic + ", message:" + message);
//        } catch (MqttException exception) {
//            System.out.println("Error " + Client_ID +" could not publish");
//            System.out.println(exception);
//        }
//    }
//
//    public void insertData(dataToSend data) { //this method inserts the data at the end of the linked blocking queue
//        sending_data.offer(data);
//    }
//
//    public void startThread() {
//        if (thread_status) {
//            System.out.println("Thread is already running for " + Client_ID );
//            return;
//        }
//
//        thread_status = true;
//        thread.start();
//        System.out.println("Thread started running for " + Client_ID );
//    }
//
//    public void stopThread() {
//        if (!thread_status) {
//            System.out.println("Thread has already stopped for " + Client_ID);
//            return;
//        }
//        thread_status = false;
//
//        // Make the thread stop waiting
//        if (thread.getState() == Thread.State.WAITING) {
//            thread.interrupt();
//        }
//
//        System.out.println("Thread stopped for " + Client_ID);
//    }
//
//    public void run() {
//        while (thread_status) {
//            try {
//                dataToSend data = sending_data.take();   // If there is no data, the thread is going to wait, else removing the head of the queue
//                String topic =  "Android/" +  data.getAndroid_Id();
//                String message = data.getAlert_message() + "," + data.getDistance();
//                Publish(topic, message);
//            } catch (InterruptedException exception) {
//                System.out.println(exception);
//                // This is where the thread stops waiting after calling stopThread()
//            }
//        }
//    }
//
//}
//
//
//
