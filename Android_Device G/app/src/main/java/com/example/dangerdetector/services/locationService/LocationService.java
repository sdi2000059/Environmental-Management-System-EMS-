package com.example.dangerdetector.services.locationService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dangerdetector.services.mqttService.CustomMqttServiceImpl;

import java.util.UUID;

/**
 * Get location from GPS Sensor
 */
public class LocationService extends Service {
    private LocationManager locationManager;
    private LocationListener locationListener;

    public class LocationServiceBinder extends Binder {
        public LocationService getService() {
            return LocationService.this;
        }
    }

    private final LocationServiceBinder binder = new LocationServiceBinder();

    private CustomMqttServiceImpl customMqttServiceImpl = new CustomMqttServiceImpl(this);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private class LocationListener implements android.location.LocationListener {
        private String TAG = "LocationListener";
        private Location lastLocation;

        public LocationListener(String provider) {
            lastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(@NonNull Location location) {
            lastLocation = location;
            String deviceId = UUID.randomUUID().toString();
            String coordinates = deviceId + "/" + "X:" + location.getLatitude() + "/" + "Y:" + location.getLongitude();
            customMqttServiceImpl.publishCoordinates(coordinates);
        }
    }

    private void initializeLocationManager() {
        if (locationManager == null) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(1234, sendNotification());
    }

    public void startTrackingLocation() {
        initializeLocationManager();
        locationListener = new LocationListener(LocationManager.GPS_PROVIDER);
        customMqttServiceImpl.setupMqttClient();


        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    500,
                    1,
                    locationListener
            );
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void stopLocationTracking() {
        this.onDestroy();
        customMqttServiceImpl.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            try {
                locationManager.removeUpdates(locationListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Notification sendNotification() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("Chanel_1", "GPS Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

            Notification.Builder builder = new Notification.Builder(getApplicationContext(), "Chanel_1").setAutoCancel(true);
            return builder.build();
        }
        return null;
    }
}
