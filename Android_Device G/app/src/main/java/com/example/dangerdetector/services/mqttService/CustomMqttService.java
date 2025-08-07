package com.example.dangerdetector.services.mqttService;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface CustomMqttService {

    void onMessageArrived(String topic, MqttMessage message);
}
