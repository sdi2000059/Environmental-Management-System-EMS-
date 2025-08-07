package com.example.iot_ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.iot_ui.services.mqttService.CustomMqttService;

import com.example.iot_ui.R;
import com.example.iot_ui.databinding.ManualCoordinatesBinding;
import com.example.iot_ui.utils.Constants;


public class ManualCoordinatesFragment extends Fragment {

    private ManualCoordinatesBinding binding;
    CustomMqttService customMqttService;
    String coordinates;

    CountDownTimer countDownTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customMqttService = new CustomMqttService(requireContext());
        customMqttService.setupMqttClient(); // Create connection to the mqtt broker

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ManualCoordinatesBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupClickListeners();
    }

    public void onDestroy() {
        super.onDestroy();
        customMqttService.disconnect();
    }

    private void setupClickListeners() {

        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonPOS_1) {
                coordinates = Constants.getPosition(1);
                Log.d("Coordinates", ": " + coordinates );
            } else if (checkedId == R.id.radioButtonPOS_2) {
                coordinates = Constants.getPosition(2);
                Log.d("Coordinates", ": " + coordinates );
            } else if (checkedId == R.id.radioButtonPOS_3) {
                coordinates = Constants.getPosition(3);
                Log.d("Coordinates", ": " + coordinates );
            } else if (checkedId == R.id.radioButtonPOS_4) {
                coordinates = Constants.getPosition(4);
                Log.d("Coordinates", ": " + coordinates );
            }
        });

        binding.sendCoordinatesButton.setOnClickListener(v -> {
            disableStartButton();
            enableStopButton();

            countDownTimer = new CountDownTimer(Long.MAX_VALUE, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    customMqttService.publishCoordinates(coordinates);    // Upload the data based on the sensor
                }

                @Override
                public void onFinish() {
                    // Not in use
                }

            };
            // Start the timer
            countDownTimer.start();
        });


        binding.stopSendingCoordinatesButton.setOnClickListener(v -> {
            disableStopButton();
            enableStartButton();

            countDownTimer.cancel();    // Stop the timer / break the loop
            customMqttService.stopPublishing();
        });
    }

    private void enableStartButton() {
        binding.sendCoordinatesButton.setEnabled(true);
    }

    private void disableStartButton() {
        binding.sendCoordinatesButton.setEnabled(false);
    }

    private void enableStopButton() {
        binding.stopSendingCoordinatesButton.setEnabled(true);
    }

    private void disableStopButton() {
        binding.stopSendingCoordinatesButton.setEnabled(false);
    }

}