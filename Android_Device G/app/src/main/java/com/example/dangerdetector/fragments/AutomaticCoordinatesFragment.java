package com.example.dangerdetector.fragments;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.dangerdetector.R;
import com.example.dangerdetector.databinding.FragmentAutomaticCoordinatesBinding;
import com.example.dangerdetector.services.locationService.LocationService;

public class AutomaticCoordinatesFragment extends Fragment {
    private FragmentAutomaticCoordinatesBinding binding;
    private LocationService locationService;
    private ActivityResultLauncher<String[]> locationActivityResultLauncher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Android needs permission from the user in order to use location from gps
        locationActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                    boolean areAllLocationPermissionsGranted = true;
                    for (boolean permissions : result.values()) {
                        areAllLocationPermissionsGranted = areAllLocationPermissionsGranted && permissions;
                    }

                    if (areAllLocationPermissionsGranted) {
                        startLocationService();
                    } else {
                        showAlertDialog();
                    }
                });
    }

    private void showAlertDialog() {
        // create pop up dialog in order to alert user if he exits out of the app
        AlertDialog permissionDialog = new AlertDialog.Builder(requireContext())
                .setTitle(R.string.location_permission_dialog_title)
                .setMessage(R.string.gps_permission_message)
                .setCancelable(true)
                .setPositiveButton(getString(R.string.dialog_positive_option), (dialog, which) -> {
                    dialog.dismiss();
                })
                .create();

        permissionDialog.show();
    }

    private void startLocationService() {
        final Intent locationIntent = new Intent(requireActivity(), LocationService.class);
        requireActivity().startService(locationIntent);
        requireActivity().bindService(locationIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            String componentName = name.getClassName();
            if (componentName.endsWith("LocationService")) {
                locationService = ((LocationService.LocationServiceBinder) service).getService();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (name.getClassName().equals("LocationService")) {
                locationService = null;
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the view
        binding = FragmentAutomaticCoordinatesBinding.inflate(inflater);
        locationActivityResultLauncher.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION});
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupClickListeners();
    }

    private void setupClickListeners() {

        binding.startTrackingButton.setOnClickListener(v -> {
            disableStartButton();
            enableStopButton();

            locationService.startTrackingLocation();
        });

        binding.stopTrackingButton.setOnClickListener(v -> {
            disableStopButton();
            enableStartButton();
            locationService.stopLocationTracking();
        });
    }

    private void enableStartButton() {
        binding.startTrackingButton.setEnabled(true);
    }

    private void disableStartButton() {
        binding.startTrackingButton.setEnabled(false);
    }

    private void enableStopButton() {
        binding.stopTrackingButton.setEnabled(true);
    }

    private void disableStopButton() {
        binding.stopTrackingButton.setEnabled(false);
    }
}