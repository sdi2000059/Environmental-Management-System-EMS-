package com.example.iot_ui.services.locationService;

import android.os.Binder;

public class LocationServiceBinder extends Binder {
    public LocationServiceBinder getService() {
        return this;
    }
}
