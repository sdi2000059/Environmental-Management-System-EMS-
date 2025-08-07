package com.example.dangerdetector.services.locationService;

import android.os.Binder;

public class LocationServiceBinder extends Binder {
    public LocationServiceBinder getService() {
        return this;
    }
}
