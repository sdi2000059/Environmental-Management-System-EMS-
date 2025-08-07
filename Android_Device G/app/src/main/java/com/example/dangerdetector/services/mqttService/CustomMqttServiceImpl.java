package com.example.dangerdetector.services.mqttService;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.dangerdetector.utils.Constants;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Set MQTT Service
 */
public class CustomMqttServiceImpl {
    private static String TAG = "MQTT Service";
    private Context context;
    private MqttAndroidClient mqttAndroidClient;
    private final String SERVER_URI = "tcp://broker.emqx.io:1883";

    private String IP, port;

    public CustomMqttService customMqttService;

    public CustomMqttServiceImpl(Context context) {
        this.context = context;
    }

    public void setupMqttClient() {
        mqttAndroidClient = new MqttAndroidClient(context, SERVER_URI, "android_app_client");
        try {
            IMqttToken token = mqttAndroidClient.connect();

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG, "MQTT IS CONNECTED!");
                    subscribe();
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

        mqttAndroidClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                customMqttService.onMessageArrived(topic, message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
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

    private void subscribe() {
        try {
            mqttAndroidClient.subscribe(Constants.SUBSCRIBE_TOPIC, 1, null, new IMqttActionListener() {
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


    public void publishCoordinates(String mqttMessage) {
        try {
            mqttAndroidClient.publish(Constants.TOPIC, mqttMessage.getBytes(), 1, false);
            Log.d(TAG, "Coordinates:" + " " + mqttMessage + "" + " are published in topic" + Constants.TOPIC);
        } catch (MqttException e) {
            Log.d(TAG, "Message is not published");
            e.printStackTrace();
        }
    }

    public void stopPublishing() {
        try {
            mqttAndroidClient.unsubscribe(Constants.TOPIC);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
