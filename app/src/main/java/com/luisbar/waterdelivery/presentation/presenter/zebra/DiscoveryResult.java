package com.luisbar.waterdelivery.presentation.presenter.zebra;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Message;

import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.WaterDeliveryApplication;
import com.luisbar.waterdelivery.common.eventbus.EventBusMask;
import com.zebra.sdk.*;

import java.util.Set;

public class DiscoveryResult implements Handler.Callback {

    private PrinterManager mPrinterManager;
private com.zebra.sdk.settings.SettingsProvider settingsProvider;
    public DiscoveryResult(PrinterManager mPrinterManager) {
        this.mPrinterManager = mPrinterManager;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 7:
                Set<BluetoothDevice> bluetoothDeviceSet = (Set<BluetoothDevice>) msg.obj;

                if (bluetoothDeviceSet != null) {
                    for (BluetoothDevice device : bluetoothDeviceSet) {
                        DiscoveryResultI discoveryResultI = mPrinterManager;
                        discoveryResultI.foundPrinter(device);
                        break;
                    }
                } else {
                    EventBusMask.post(WaterDeliveryApplication.resources.getString(R.string.txt_88));
                }
                break;

            case 1:
                DiscoveryResultI discoveryResultI = mPrinterManager;
                discoveryResultI.discoveryError(WaterDeliveryApplication.resources.getString(R.string.txt_84));
                break;
        }

        return true;
    }

    public interface DiscoveryResultI {

        void foundPrinter(BluetoothDevice device);
        void discoveryFinished();
        void discoveryError(String error);
    }
}
