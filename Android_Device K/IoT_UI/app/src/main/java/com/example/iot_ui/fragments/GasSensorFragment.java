package com.example.iot_ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iot_ui.databinding.GasSensorBinding;
import com.example.iot_ui.sensors.GasSensor;
import com.example.iot_ui.services.mqttService.CustomMqttService;
import com.google.android.material.slider.Slider;
import android.os.CountDownTimer;

import com.example.iot_ui.utils.*;

public class GasSensorFragment extends Fragment {

    private GasSensorBinding binding;
    private GasSensor gasSensor = new GasSensor();
//    private CustomMqttService mqtt = new CustomMqttService(gasSensor);
    private static String TAG = "Button Action";
    private float sensor_val = 0.0f;

    CustomMqttService customMqttService;
    CountDownTimer countDownTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize GasSensor object
        gasSensor = new GasSensor(0.0, false); // Initial value and sensor status
        customMqttService = new CustomMqttService(requireContext());
        customMqttService.setupMqttClient(); // Create connection to the mqtt broker
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GasSensorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Your onViewCreated code
        binding.gasSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                // Update gas value in GasSensor
                gasSensor.updateGasValue(value);
                binding.mainSliderValGas.setText(String.format("%.2f", value));
                sensor_val = value;
                // Print message
                Log.d("GasSensorFragment", "Slider value changed: " + value);
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

    /*
     * Try to create in the top a new connection for the mqtt and to init also the mqtt with the class for the sensors value
     * I try to connect it to the mqtt broker but always says that there is a null
     */
    private void setupClickListeners() {

        binding.Enable.setOnClickListener(v -> {
            disableStartButton();
            enableStopButton();
            gasSensor.setSensorStatus(true); // use of mqtt when button pressed
            Log.d("Enable Button", "Enable button pressed, mqtt status: " + gasSensor.isSensorOn() + " sensor value: " + gasSensor.getGasValue());

            // Send every one second the data with the use of mqtt
            countDownTimer = new CountDownTimer(Long.MAX_VALUE, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    customMqttService.publishData(String.format("%.2f", sensor_val), 1);    // Upload the data based on the sensor
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
            gasSensor.setSensorStatus(false);
            Log.d("Disable Button", "Disable button pressed, mqtt status: " + gasSensor.isSensorOn());

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
