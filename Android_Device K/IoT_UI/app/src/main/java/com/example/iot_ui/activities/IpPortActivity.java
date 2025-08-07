package com.example.iot_ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.iot_ui.adapter.CoordinatesOptionAdapter;
import com.example.iot_ui.databinding.BasicActivityBinding;
import com.example.iot_ui.fragments.IpPortFragment;

import java.util.ArrayList;
import java.util.List;

public class IpPortActivity extends AppCompatActivity {

    private BasicActivityBinding binding;
    private CoordinatesOptionAdapter adapter;
    List<Fragment> ipPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = BasicActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize fragments lists
        ipPort = new ArrayList<>();

        setupAdapter();

    }

    private void setupAdapter() {
        ipPort.add(new IpPortFragment());

        adapter = new CoordinatesOptionAdapter(getSupportFragmentManager(), getLifecycle(), ipPort);
        binding.viewPager2.setAdapter(adapter);
    }
}
