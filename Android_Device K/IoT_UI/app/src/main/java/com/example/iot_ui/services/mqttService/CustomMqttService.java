package com.example.iot_ui.services.mqttService;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.iot_ui.sensors.GasSensor;
import com.example.iot_ui.sensors.SmokeSensor;
import com.example.iot_ui.sensors.TempSensor;
import com.example.iot_ui.sensors.UVSensor;
import com.example.iot_ui.utils.Constants;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Set MQTT Service
 */
public class CustomMqttService {
    private static String TAG = "MQTT Service";
    private Context context;
    private MqttAndroidClient mqttAndroidClient;
    public static String SERVER_URI = "tcp://localhost:1883";
    public static String BASIC_URI = "tcp://broker.emqx.io:1883";
    private static CustomMqttService instance;

    public CustomMqttService(Context context) {
        this.context = context;
    }

    // Static method to provide the Singleton instance
    public static CustomMqttService getInstance(Context context) {
        if (instance == null) {
            synchronized (CustomMqttService.class) {
                if (instance == null) {
                    instance = new CustomMqttService(context);
                }
            }
        }
        return instance;
    }

    public void setupMqttClient() {
        mqttAndroidClient = new MqttAndroidClient(context, BASIC_URI, "android_app_client");

        try {
            IMqttToken token = mqttAndroidClient.connect();

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG, "MQTT IS CONNECTED!");
                    Toast.makeText(context, "MQTT Connection established", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d(TAG, "MQTT IS NOT CONNECTED!");
                    Toast.makeText(context, "MQTT Connection is NOT established!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (mqttAndroidClient != null && mqttAndroidClient.isConnected()) {
            try {
                mqttAndroidClient.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public void subscribe() {
        try {
            mqttAndroidClient.subscribe(Constants.TOPIC, 1, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG, "Subscribed to topic:" + Constants.TOPIC);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d(TAG, "Error subscribing:" + exception.getMessage());
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // Unsubscribe for the topic but keep the connection
    public void stopPublishing() {
        try {
            mqttAndroidClient.unsubscribe(Constants.TOPIC);
            Log.d(TAG, "Unsubscribe from topic " + Constants.TOPIC);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publishCoordinates(String msg) {
        try {
            String []parts = msg.split(" ");
            String mqttMessage = "ID:" + Constants.DeviceId + "/" + "X:" + parts[0] + "/" + "Y:" + parts[1];
            mqttAndroidClient.publish(Constants.TOPIC, mqttMessage.getBytes(), 0, false);
            Log.d(TAG, "Coordinates:" + " " + mqttMessage + "" + " are published in topic" + Constants.TOPIC);
        } catch (MqttException e) {
            Log.d(TAG, "Message is not published");
            e.printStackTrace();
        }
    }

    /* Send IoT sensors data
     * Sensor Id is used for choosing beetwen the sensors for sending the following data
     * 1: gas sensors | 2: smoke sensor | 3: temp sensor | 4: uv sensor
     */
    public void publishData(String msg, int sensorId) {
        try {
            String mqttMessage;
            String IoT_id = Constants.DeviceId;
            String battery = "Battery:" + Constants.BatteryLevel;
            String tempMessage = "ID:" + IoT_id + "/" + battery + "/";
            Log.d(TAG, "Called to send data to broker");

            // Gas Sensor
            if (sensorId == 1) {
                // Append gas value to the MQTT message
                mqttMessage = tempMessage + "Gas Value:" + msg;
                mqttAndroidClient.publish(Constants.TOPIC, mqttMessage.getBytes(), 0, false);
                Log.d(TAG, "Message: " + mqttMessage + " published in topic " + Constants.TOPIC);
            }

            // Smoke Sensor
            if (sensorId == 2) {
                // Append gas value to the MQTT message
                mqttMessage = tempMessage + "Smoke Value:" + msg;
                mqttAndroidClient.publish(Constants.TOPIC, mqttMessage.getBytes(), 0, false);
                Log.d(TAG, "Message: " + mqttMessage + " published in topic " + Constants.TOPIC);
            }

            // Temp Sensor
            if (sensorId == 3) {
                mqttMessage = tempMessage + "Temp Value:" + msg;
                mqttAndroidClient.publish(Constants.TOPIC, mqttMessage.getBytes(), 0, false);
                Log.d(TAG, "Message: " + mqttMessage + " published in topic " + Constants.TOPIC);
            }

            // UV Sensor
            if (sensorId == 4) {
                mqttMessage = tempMessage + "UV Value:" + msg;
                mqttAndroidClient.publish(Constants.TOPIC, mqttMessage.getBytes(), 0, false);
                Log.d(TAG, "Message: " + mqttMessage + " published in topic " + Constants.TOPIC);
            }
        } catch (MqttException e) {
            Log.d(TAG, "Message is not published");
            e.printStackTrace();
        }
    }

}


