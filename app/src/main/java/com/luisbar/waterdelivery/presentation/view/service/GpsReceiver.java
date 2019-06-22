package com.luisbar.waterdelivery.presentation.view.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Receiver that listen when the gps is enable or disable
 */
public class GpsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mIntent = new Intent(context, TrackingService.class);
        mIntent.setAction("GPS");
        context.startService(mIntent);
    }
}
