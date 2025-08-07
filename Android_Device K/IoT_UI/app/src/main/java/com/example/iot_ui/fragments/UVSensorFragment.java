package com.example.iot_ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.CountDownTimer;

import com.example.iot_ui.databinding.UvSensorBinding;
import com.example.iot_ui.sensors.UVSensor;
import com.example.iot_ui.services.mqttService.CustomMqttService;
import com.google.android.material.slider.Slider;

public class UVSensorFragment extends Fragment {

    private UvSensorBinding binding;
    private UVSensor uvSensor;
    private float sensor_val = 0.0f;
    CustomMqttService customMqttService;
    CountDownTimer countDownTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize GasSensor object
        uvSensor = new UVSensor(0.0, false); // Initial value and sensor status
        customMqttService = new CustomMqttService(requireContext());
        customMqttService.setupMqttClient(); // Create connection to the mqtt broker
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = UvSensorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Your onViewCreated code
        binding.uvSliderID.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                // Update gas value in GasSensor
                uvSensor.updateUvValue(value);
                binding.mainSliderValUv.setText(String.format("%.2f", value));
                sensor_val = value;
                // Print message
                Log.d("SmokeSensorFragment", "Slider value changed: " + value);
            }
        });

        // Setup button click listeners
        setupClickListeners();
    }

    // Closes the connection of the mqtt connection
    @Override
    public void onDestroy() {
        super.onDestroy();
        customMqttService.disconnect();
    }

    private void setupClickListeners() {

        binding.Enable.setOnClickListener(v -> {
            disableStartButton();
            enableStopButton();
            uvSensor.setSensorStatus(true);
            Log.d("Enable Button", "Enable button pressed, mqtt status: " + uvSensor.isSensorOn());



            countDownTimer = new CountDownTimer(Long.MAX_VALUE, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    customMqttService.publishData(String.format("%.2f", sensor_val), 4);    // Upload the data based on the sensor
                }

                @Override
                public void onFinish() {
                    // Not in use
                }
            };

            // Start the timer
            countDownTimer.start();

        });

        binding.Disable.setOnClickListener(v -> {
            disableStopButton();
            enableStartButton();
            uvSensor.setSensorStatus(false);
            Log.d("Disable Button", "Disable button pressed, mqtt status: " + uvSensor.isSensorOn());

            countDownTimer.cancel();    // Stop the timer / break the loop
            customMqttService.stopPublishing();

        });
    }

    private void enableStartButton() {
        binding.Enable.setEnabled(true);
    }

    private void disableStartButton() {
        binding.Enable.setEnabled(false);
    }

    private void enableStopButton() {
        binding.Disable.setEnabled(true);
    }

    private void disableStopButton() {
        binding.Disable.setEnabled(false);
    }

}
