package com.example.iot_ui.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.iot_ui.R;
import com.example.iot_ui.adapter.CoordinatesOptionAdapter;
import com.example.iot_ui.databinding.ActivityMainBinding;
import com.example.iot_ui.fragments.GasSensorFragment;
import com.example.iot_ui.fragments.SmokeSensorFragment;
import com.example.iot_ui.fragments.TempSensorFragment;
import com.example.iot_ui.fragments.UVSensorFragment;
import com.example.iot_ui.services.mqttService.CustomMqttService;
import com.example.iot_ui.utils.Constants;
import com.example.iot_ui.utils.monitorNetworkStatus.NetworkMonitor;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private CoordinatesOptionAdapter adapter;
    NetworkMonitor networkMonitor;
    List<Fragment> fragments;
//    CustomMqttService mqttService;
    private static String TAG = "Battery Action";

    private final BroadcastReceiver BatteryLevelReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            float batteryPct = level * 100 / (float)scale;
            Log.d(TAG, "Battery Level = " + batteryPct);
            Constants.BatteryLevel = batteryPct;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

//        CustomMqttService.getInstance(this).setupMqttClient();

        // Initialize fragments lists
        fragments = new ArrayList<>();

        setNetworkCallBack();
        setupAdapter();

        registerReceiver(this.BatteryLevelReciever, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        Constants.DeviceId = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
        Log.d("Android","Android ID : "+Constants.DeviceId);
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
            Toast.makeText(getApplicationContext(), "Connection is lost", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
        }
    };

    private void setNetworkCallBack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
        fragments.add(new GasSensorFragment());
        fragments.add(new SmokeSensorFragment());
        fragments.add(new TempSensorFragment());
        fragments.add(new UVSensorFragment());

        adapter = new CoordinatesOptionAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        activityMainBinding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(activityMainBinding.tabLayout, activityMainBinding.viewPager, true, ((tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Gas Sensor");
                    break;
                case 1:
                    tab.setText("Smoke Sensor");
                    break;
                case 2:
                    tab.setText("Temp Sensor");
                    break;
                case 3:
                    tab.setText("UV Sensor");
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
        menuInflater.inflate(R.menu.menu_opt, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handling the click for menu options
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.ip_and_port_option) {
            showIpPortFragment();
        } else if (item.getItemId() == R.id.create_sensor) {
            showCreateSensorFragment();
        } else if (item.getItemId() == R.id.location) {
            showLocationFragment();
        } else if (item.getItemId() == R.id.topic) {
            topicSelectionDialog();
        } else {
            showPopUpDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showIpPortFragment() {
        Intent sendIntent = new Intent(MainActivity.this, IpPortActivity.class);
        // Start the activity
        startActivity(sendIntent);
    }

    // Process for creating the sensor
    private void showCreateSensorFragment() {
        Intent sendIntent = new Intent(MainActivity.this, CreateSensorActivity.class);
        // Start the activity
        startActivity(sendIntent);
    }

    private void showLocationFragment() {
        Intent sendIntent = new Intent(MainActivity.this, CoordinatesActivity.class);
        // Start the activity
        startActivity(sendIntent);
    }

    // Function to show a dialog for selecting a topic
    private void topicSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Topic");
        builder.setItems(new CharSequence[]{"1", "2"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedNumber = which + 1;
                Constants.setTopic(selectedNumber);

                Log.d("Topic", "Topic selected: " + selectedNumber + " New Topic: " + Constants.newTOPIC);
                Toast.makeText(MainActivity.this, "You selected the Topic: " + Constants.newTOPIC, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false); // Prevent dialog from being dismissed by touching outside
        builder.show();
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