package com.example.iot_ui.activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.iot_ui.adapter.CoordinatesOptionAdapter;
import com.example.iot_ui.databinding.BasicActivityBinding;
import com.example.iot_ui.fragments.NewSensorFragment;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class NewSensorActivity extends Activity {

    private BasicActivityBinding binding;
    private CoordinatesOptionAdapter adapter;
    public static List<Fragment> newSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = BasicActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize fragments lists
        newSensors = new ArrayList<>();

        setupAdapter();

    }

    public static void addFragment(NewSensorFragment fragment) {
        newSensors.add(fragment);
    }

    public List<Fragment> getFragmentList() {
        return newSensors;
    }

    private void setupAdapter() {

//        adapter = new CoordinatesOptionAdapter(getSupportFragmentManager(), getLifecycle(), newSensors);
//        binding.viewPager2.setAdapter(adapter);
//
//        new TabLayoutMediator(binding.tabLayout2, binding.viewPager2, true, ((tab, position) -> {
//            switch (position) {
//                case 0:
//                    tab.setText("Automatic Coordinates");
//                    break;
//                case 1:
//                    tab.setText("Manual Coordinates");
//                    break;
//            }
//        })).attach();
    }
}
