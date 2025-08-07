package com.example.iot_ui.utils.monitorNetworkStatus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetworkMonitor {
    private Context context;
    private ConnectivityManager connectivityManager;

    private NetworkRequest networkRequest;

    public NetworkMonitor(Context context) {
        this.context = context;
        // get Connection System Service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        }
        // Configure a network request in order to specify what type of internet we will need
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkRequest = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .build();
        }
    }

    public void registerNetworkCallBack(ConnectivityManager.NetworkCallback networkCallback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
        }
    }

    public void unregisterNetworkCallBack(ConnectivityManager.NetworkCallback networkCallback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }
}
