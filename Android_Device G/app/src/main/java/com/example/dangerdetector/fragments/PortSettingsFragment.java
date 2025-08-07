package com.example.dangerdetector.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dangerdetector.databinding.FragmentPortSettingsBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * Fragment where it shows the user to change IP/PORT settings
 */
public class PortSettingsFragment extends BottomSheetDialogFragment {
    private FragmentPortSettingsBinding binding;

    public static PortSettingsFragment newInstance() {
        return new PortSettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPortSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupClickListeners();
    }

    private void setupClickListeners() {
        binding.changeIpDataPortButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("IP", binding.ipEdit.getText().toString().trim());
            bundle.putString("PORT", binding.portEdit.getText().toString().trim());
            getParentFragmentManager().setFragmentResult("IP_DATA_PORT", bundle);
            dismiss();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}