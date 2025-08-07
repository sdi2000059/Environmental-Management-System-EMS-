package com.example.iot_ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iot_ui.R;
import com.example.iot_ui.activities.NewSensorActivity;
import com.example.iot_ui.databinding.CreateSensorBinding;
import com.example.iot_ui.sensors.NewSensor;

public class CreateSensorFragment extends Fragment {

    private CreateSensorBinding binding;
    private ArrayAdapter<CharSequence> arrayAdapter;
    String sensor_pos;
    String val1, val2;
    private EditText text1, text2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CreateSensorBinding.inflate(inflater, container, false);

        String[] sensors = getResources().getStringArray(R.array.SensorsType);
        arrayAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.SensorsType, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.autoCompleteTextView.setAdapter(arrayAdapter);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupClickListeners();
    }

    private void setupClickListeners() {

        // Set up click listener for the button
        binding.createSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedSensor = binding.autoCompleteTextView.getText().toString();
                // Get values from TextInputEditText fields
                val1 = binding.lowInput.getEditText().getText().toString();
                val2 = binding.topInput.getEditText().getText().toString();

                //Method for taking the data from the text and from the list and create fragment
                Log.d("New Sensor", "Create new sensor with parameters: " + sensor_pos + " " + val1 + " " + val2);

                if (sensor_pos.equals("Gas Sensor")) {
                    //Create new gas sensor
//                    NewSensor ns = new NewSensor();
//                    ns.createAddSensor("gas", val1, val2);
//                    NewSensorActivity.addFragment(ns.getFragment());
                } else if (sensor_pos.equals("Smoke Sensor")) {
                    //Create new smoke sensor
//                    NewSensor ns = new NewSensor();
//                    ns.createAddSensor("smoke", val1, val2);
//                    NewSensorActivity.addFragment(ns.getFragment());
                } else if (sensor_pos.equals("Temp Sensor")) {
                    //Create new temp sensor
//                    NewSensor ns = new NewSensor();
//                    ns.createAddSensor("temp", val1, val2);
//                    NewSensorActivity.addFragment(ns.getFragment());
                } else if (sensor_pos.equals("UV Sensor")) {
                    //Create new uv sensor
//                    NewSensor ns = new NewSensor();
//                    ns.createAddSensor("uv", val1, val2);
//                    NewSensorActivity.addFragment(ns.getFragment());
                } else {

                }
            }
        });

        binding.autoCompleteTextView.setOnItemClickListener((adapterView, view1, pos, l) -> {
            sensor_pos = adapterView.getItemAtPosition(pos).toString();
            Log.d("Create New Sensor", "New sensor selected in pos: " + sensor_pos);
        });
    }
}
