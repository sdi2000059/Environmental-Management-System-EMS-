package com.example.iot_ui.fragments;

import static android.os.Build.VERSION_CODES.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iot_ui.databinding.NewSensorBinding;
import com.example.iot_ui.sensors.GasSensor;
import com.example.iot_ui.services.mqttService.CustomMqttService;
import com.google.android.material.slider.Slider;

public class NewSensorFragment extends Fragment {

    private NewSensorBinding binding;
    private String name, val1, val2;
    private CustomMqttService customMqttService;
    private CountDownTimer countDownTimer;

    public NewSensorFragment(String name, String val1, String val2) {
        this.name = name;
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customMqttService = new CustomMqttService(requireContext());
        customMqttService.setupMqttClient(); // Create connection to the mqtt broker
    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_layout, container, false);
//
//        // Find the TextView by its ID
//        TextView textView = view.findViewById(R.id.textView);
//
//        // Set the text to the TextView
//        textView.setText("Your text here");
//
//        return view;
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        TextView textView = view.findViewById(R.id.new_sensor);
//        textView.setText(name);
//
//        TextView t = this.getView().findViewById(R.id.new_sensor);
//        t.setText(name);

        // Your onViewCreated code
        binding.newSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                binding.mainSliderValNew.setText(String.format("%.2f", value));
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

            // Send every one second the data with the use of mqtt
            countDownTimer = new CountDownTimer(Long.MAX_VALUE, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
//                    customMqttService.publishData(String.format("%.2f", sensor_val), 1);    // Upload the data based on the sensor
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
