//package Mqtt;
//
////Subscriber
//import Calculator.DangerCalculation;
//import Calculator.DangerDegreeCalc;
//import Calculator.DistanceCalc;
//import Data.dataToSend;
//import Data.received_IoT_data;
//import Data.received_android_data;
//import Data.device_data;
//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.ConcurrentHashMap;
//
//import static Config.Configuration.*;
//
//
////Class Subscriber
//public class Subscriber extends Client implements MqttCallback {
//    private static final long POLL_TIMEOUT = 1; // in seconds
//    private LinkedBlockingQueue<received_android_data> received_android; //edge receives data from android devices
//    private LinkedBlockingQueue<received_IoT_data> received_IoT1; //and also IoT1 device
//    private LinkedBlockingQueue<received_IoT_data> received_IoT2; //and also IoT2 device
//    private LinkedBlockingQueue<device_data> devices_data; //edge receives data from android devices
//
//    private boolean IoT_1 = false;
//    private boolean IoT_2 = false;
//    private boolean android = false;
//    private LinkedBlockingQueue<received_IoT_data> used_IoT1; //and also IoT1 device
//    private LinkedBlockingQueue<received_IoT_data> used_IoT2; //and also IoT2 device
//    private int flag = 0;
//
//    public Subscriber(String broker, String Client_ID) {
//
//        super(broker, Client_ID);
//        System.out.println("Initializing subscriber " + Client_ID);
//        super.client.setCallback(this);
//        received_android = new LinkedBlockingQueue<>();
//        received_IoT1 = new LinkedBlockingQueue<>();
//        received_IoT2 = new LinkedBlockingQueue<>();
//        devices_data = new LinkedBlockingQueue<>();
//        used_IoT1 = new LinkedBlockingQueue<>();
//        used_IoT2 = new LinkedBlockingQueue<>();
//
//    }
//
//    public void Subscribe(String topic) {
//        try {
//            client.subscribe(topic, Client.QOS);
//            System.out.println(Client_ID + ": Subscribed to \"" + topic + "\"");
//        }
//        catch (MqttException exception) {
//            System.out.println("Error " + Client_ID +" could not subscribe");
//            System.out.println(exception);
//        }
//    }
//    public device_data retrieve_device_Data() {
//        try {
//            return devices_data.take(); //Retrieves and removes the head of this queue, waiting up to the specified wait time if necessary for an element to become available.
//        } catch (InterruptedException exception) {
//            return null;
//        }
//    }
//    public received_android_data retrieve_android_Data() {
//        try {
//            return received_android.take(); //Retrieves and removes the head of this queue, waiting up to the specified wait time if necessary for an element to become available.
//        } catch (InterruptedException exception) {
//            return null;
//        }
//    }
//
//    public received_IoT_data retrieve_IoT1_Data() {
//      //  try {
//            return received_IoT1.poll(); //Retrieves and removes the head of this queue, waiting up to the specified wait time if necessary for an element to become available.
//       // }
////        catch (InterruptedException exception) {
////            return null;
////        }
//    }
//    public received_IoT_data retrieve_IoT2_Data() {
//       // try {
//            return received_IoT2.poll(); //Retrieves and removes the head of this queue, waiting up to the specified wait time if necessary for an element to become available.
//      //  }
////        catch (InterruptedException exception) {
////            return null;
////        }
//    }
//    public void connectionLost(Throwable throwable) {
//        System.out.println(Client_ID + ": Connection lost");
//    }
//    public void messageArrived(String topic, MqttMessage mqttMessage) throws InterruptedException, SQLException, IOException {
//
//        String message = new String(mqttMessage.getPayload());
//
//        //figuring out what type of data was sent, android or IoT
//        if (topic.indexOf("EdgeServer/Android/") != -1) {
//            System.out.println(Client_ID + " received android data to Topic: " + topic + ", Message: " + message);//then the message received is from an android device
//            received_android_data data = received_android_data.getData(message);
//            received_android.add(data);
//            device_data d_data = device_data.getData(message);
//            devices_data.add(d_data);
//            android = true;
////            jFrame.setVisible(false);
//            bufferedImage = ImageIO.read(file);
//            g2d = bufferedImage.createGraphics();
//            int x = (int) (data.getLongitude()*10);
//            System.out.println("x: " + x);
//            int y = (int) (data.getLatitude()*10);
//            System.out.println("y: " + y);
//            g2d.drawImage(marker, x, y, null);
//            g2d.dispose();
//
////            jLabel.setIcon(imageIcon);
//            jFrame.add(jLabel);
//
//            jLabel.setIcon(new ImageIcon(bufferedImage));
//            jFrame.setVisible(true);
//
//
//        } else if (topic.indexOf("EdgeServer/IoT/1") != -1) {
//            System.out.println(Client_ID + " received IoT1 data to Topic: " + topic + ", Message: " + message);//then the message received is from an IoT device
//
//            received_IoT_data data = received_IoT_data.getData(message);
//            received_IoT1.add(data);
//            device_data d_data = device_data.getData(message);
//            devices_data.add(d_data);
//            IoT_1 = true;
//
//        } else if (topic.indexOf("EdgeServer/IoT/2") != -1) {
//            System.out.println(Client_ID + " received IoT2 data to Topic: " + topic + ", Message: " + message);//then the message received is from an IoT device
//
//            received_IoT_data data = received_IoT_data.getData(message);
//            received_IoT2.add(data);
//            device_data d_data = device_data.getData(message);
//            devices_data.add(d_data);
//            IoT_2 = true;
//
//        }
//        flag =0;
//        if(android==true) {
//            DangerCalculation calc = new DangerCalculation();
//
//            dataToSend sending_data = new dataToSend();
//            double distance = -2;
//            if (IoT_1 == true && IoT_2 == true) {
//                System.out.println("hohohoho");
//                received_IoT_data IoT1_Data = retrieve_IoT1_Data();
//                received_IoT_data IoT2_Data = retrieve_IoT2_Data();
//
//                if (IoT1_Data == null) {
//                    IoT1_Data = used_IoT1.peek();
//                }
//                if (IoT2_Data == null) {
//                    IoT2_Data = used_IoT2.peek();
//                }
//
//                if (!used_IoT1.isEmpty()) {
//                    used_IoT1.take();
//                    IoT_1 = false;
//                    flag =1;
//                }
//                used_IoT1.add(IoT1_Data);
//
//                if (!used_IoT2.isEmpty()) {
//                    used_IoT2.take();
//                    IoT_2 = false;
//                    flag =1;
//                }
//                used_IoT2.add(IoT2_Data);
//                received_android_data androidData = retrieve_android_Data();
//                if(androidData!=null) {
//                    sending_data = calc.DangerFromTwoDevices(androidData, IoT1_Data, IoT2_Data,flag);
//                }
//            } else{
//
//            if (IoT_1 == true) {
////                if (IoT_2 == false) {
//                System.out.println("hehehe");
//
//                received_IoT_data IoT1_Data = retrieve_IoT1_Data();
//                if (IoT1_Data == null) {
//                    IoT1_Data = used_IoT1.peek();
//                    IoT_1 = false;
//                }
//
//                if (!used_IoT1.isEmpty()) {
//                    used_IoT1.take();
////                    IoT_1 = false;
//                    flag =1;
//
//                }
//                used_IoT1.add(IoT1_Data);
//                //  }
//                System.out.println("hehehe");
//                received_android_data androidData = retrieve_android_Data();
//                if(androidData!=null) {
//                    sending_data = calc.DangerFromOneDevice(androidData, IoT1_Data,flag);
//                }
//
//            } else if (IoT_2 == true) {
//                System.out.println("hahahaha");
//
//
//                received_IoT_data IoT2_Data = retrieve_IoT2_Data();
//                if (IoT2_Data == null) {
//                    IoT2_Data = used_IoT2.peek();
//                    IoT_2 = false;
//                }
//
//                if (!used_IoT2.isEmpty()) {
//                    used_IoT2.take();
////                    IoT_2 = false;
//                    flag =1;
//                }
//                used_IoT2.add(IoT2_Data);
//                //  }
//                received_android_data androidData = retrieve_android_Data();
//                if(androidData!=null) {
//                    sending_data = calc.DangerFromOneDevice(androidData, IoT2_Data,flag);
//                }
//
//            }
//            }
//            System.out.println("hahahaha");
//            if (sending_data.getAndroid_Id() != null) {
//
//                publisher.insertData(sending_data);
//            }
//
//        }
//
//
//    }
//
//    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {}
//
//
//}
