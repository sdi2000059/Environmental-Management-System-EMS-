package com.example.dangerdetector.fragments;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dangerdetector.data.TimeStep;
import com.example.dangerdetector.data.XMLFeed;
import com.example.dangerdetector.databinding.FragmentManualCoordinatesBinding;
import com.example.dangerdetector.services.mqttService.CustomMqttServiceImpl;
import com.example.dangerdetector.utils.xmlParser.CustomXMLParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ManualCoordinatesFragment extends Fragment {

    private CustomMqttServiceImpl customMqttServiceImpl;
    private FragmentManualCoordinatesBinding binding;

    private String ip, port;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("IP_DATA_PORT", this, (requestKey, bundle) -> {
            ip = bundle.getString("IP");
            port = bundle.getString("PORT");
        });

        customMqttServiceImpl = new CustomMqttServiceImpl(requireContext());
        customMqttServiceImpl.setupMqttClient();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentManualCoordinatesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupClickListeners();
        customMqttServiceImpl.customMqttService = (topic, message) ->
                Toast.makeText(ManualCoordinatesFragment.this.requireContext(), message.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        customMqttServiceImpl.disconnect();
    }

    private void setupClickListeners() {
        binding.sendCoordinatesButton.setOnClickListener(v -> {
            disableStartButton();
            enableStopButton();
            try {
                parseXmlFile();
            } catch (XmlPullParserException e) {
                throw new RuntimeException(e);
            }
        });

        binding.stopSendingCoordinatesButton.setOnClickListener(v -> {
            disableStopButton();
            enableStartButton();
            customMqttServiceImpl.stopPublishing();
        });
    }

    private void parseXmlFile() throws XmlPullParserException {
        ExecutorService executorService = Executors.newSingleThreadExecutor(); // execute asynchronously/in background the xml parsing of file

        AssetManager assetManager = requireContext().getAssets(); // get file from assets

        executorService.execute(() -> {
            try {
                InputStream inputStream = assetManager.open("android_1.xml");
                CustomXMLParser customXmlParser = new CustomXMLParser();
                XMLFeed xmlFeed = customXmlParser.parse(inputStream);
                List<TimeStep> timeStepList = xmlFeed.getTimeStepList();
                String deviceId = UUID.randomUUID().toString();
                for (TimeStep timeStep : timeStepList) {
                    customMqttServiceImpl.publishCoordinates("ID:"+deviceId + "/" + "Y:" + timeStep.getVehicle().getLatitude() + "/" + "X:" + timeStep.getVehicle().getLongitude());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (XmlPullParserException e) {
                throw new RuntimeException(e);
            }
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