package com.example.iot_ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iot_ui.databinding.IpPortBinding;
import com.example.iot_ui.services.mqttService.CustomMqttService;
import com.example.iot_ui.utils.Constants;

public class IpPortFragment extends Fragment {

    private IpPortBinding binding;
    private String ip, port;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        CustomMqttService.getInstance(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = IpPortBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupClickListeners();
    }

    private void setupClickListeners() {

        binding.recomended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip = "broker.emqx.io";
                port = "1883";
                String URI = "tcp://" + ip + ":" + port;
                Constants.HOST_IP = ip;
                Constants.PORT = port;
                CustomMqttService.SERVER_URI = URI;
                Toast.makeText(requireContext(), "URI Broker: " + "tcp://broker.emqx.io:1883", Toast.LENGTH_SHORT).show();
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip = binding.ip.getEditText().getText().toString();
                port = binding.port.getEditText().getText().toString();

                String URI = "tcp://" + ip + ":" + port;
                Constants.HOST_IP = ip;
                Constants.PORT = port;
                CustomMqttService.SERVER_URI = URI;
                Toast.makeText(requireContext(), "New URI Broker: " + URI, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
