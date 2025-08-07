package com.example.iot_ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.iot_ui.adapter.CoordinatesOptionAdapter;
import com.example.iot_ui.databinding.BasicActivityBinding;
import com.example.iot_ui.fragments.AutomaticCoordinatesFragment;
import com.example.iot_ui.fragments.ManualCoordinatesFragment;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class CoordinatesActivity extends AppCompatActivity {

    private BasicActivityBinding binding;
    private CoordinatesOptionAdapter adapter;
    List<Fragment> cordFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = BasicActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize fragments lists
        cordFragments = new ArrayList<>();

        setupAdapter();

    }

    private void setupAdapter() {
        cordFragments.add(new AutomaticCoordinatesFragment());
        cordFragments.add(new ManualCoordinatesFragment());

        adapter = new CoordinatesOptionAdapter(getSupportFragmentManager(), getLifecycle(), cordFragments);
        binding.viewPager2.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout2, binding.viewPager2, true, ((tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Automatic Coordinates");
                    break;
                case 1:
                    tab.setText("Manual Coordinates");
                    break;
            }
        })).attach();
    }
}
