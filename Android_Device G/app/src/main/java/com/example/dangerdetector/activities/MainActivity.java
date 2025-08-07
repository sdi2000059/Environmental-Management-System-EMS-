package com.example.dangerdetector.activities;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.dangerdetector.R;
import com.example.dangerdetector.adapter.CoordinatesOptionAdapter;
import com.example.dangerdetector.databinding.ActivityMainBinding;
import com.example.dangerdetector.fragments.AutomaticCoordinatesFragment;
import com.example.dangerdetector.fragments.ManualCoordinatesFragment;
import com.example.dangerdetector.fragments.PortSettingsFragment;
import com.example.dangerdetector.utils.monitorNetworkStatus.NetworkMonitor;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private CoordinatesOptionAdapter adapter;
    NetworkMonitor networkMonitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        setNetworkCallBack();
        setupAdapter();
    }

    @SuppressLint("NewApi")
    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            Toast.makeText(getApplicationContext(), "Connection is on", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            Toast.makeText(getApplicationContext(), "Connection is lost, Please enable Internet from your Settings", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
        }
    };

    private void setNetworkCallBack() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            networkMonitor = new NetworkMonitor(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkMonitor.registerNetworkCallBack(networkCallback);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkMonitor.unregisterNetworkCallBack(networkCallback);
        }
    }

    private void setupAdapter() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new AutomaticCoordinatesFragment());
        fragments.add(new ManualCoordinatesFragment());
        adapter = new CoordinatesOptionAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        activityMainBinding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(activityMainBinding.tabLayout, activityMainBinding.viewPager, true, ((tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("AUTOMATIC COORDINATES");
                    break;
                case 1:
                    tab.setText("MANUAL COORDINATES");
                    break;
            }
        })).attach();
    }

    /**
     * Creating the options (pop up menu) for the activity
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handling the click for menu options
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.ip_and_port_option) {
            PortSettingsFragment portSettingsFragment = PortSettingsFragment.newInstance();
            portSettingsFragment.show(getSupportFragmentManager(), "IP PORT FRAGMENT");
        } else if (item.getItemId() == R.id.publish_time_option) {
            // todo : create activity to set a time for publishing data
        } else {
            showPopUpDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPopUpDialog() {
        // create pop up dialog in order to alert user if he exits out of the app
        AlertDialog exitDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.exit_app_dialog_title))
                .setMessage(R.string.dialog_message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.dialog_positive_option), (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                })
                .setNegativeButton(getString(R.string.dialog_negative_option), (dialog, which) -> dialog.dismiss())
                .create();

        exitDialog.show();
    }
}