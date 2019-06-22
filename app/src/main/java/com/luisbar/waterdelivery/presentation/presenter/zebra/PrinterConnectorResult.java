package com.luisbar.waterdelivery.presentation.presenter.zebra;

import android.os.Handler;
import android.os.Message;

import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.WaterDeliveryApplication;


public class PrinterConnectorResult implements Handler.Callback {

    private PrinterManager mPrinterManager;
    private int isFinished = 0;

    private final int PRINTER_NOT_CONNECTED = 0;
    private final int PRINTER_SUCCESSFUl = 1;
    private final int IMAGE_NOT_SAVED = 3;

    public PrinterConnectorResult(PrinterManager mPrinterManager) {
        this.mPrinterManager = mPrinterManager;
    }

    @Override
    public boolean handleMessage(Message msg) {
        PrinterConnectorResultI printerConnectorResultI = mPrinterManager;
/*
        switch (msg.what) {
            case ZQ110.MESSAGE_STATE_CHANGE:
                switch (msg.arg1) {
                    case ZQ110.STATE_CONNECTING:
                        printerConnectorResultI.isBeingConnected();
                        break;

                    case ZQ110.STATE_CONNECTED:
                        printerConnectorResultI.communicationEnabled();
                        break;

                    case ZQ110.STATE_NONE:
                        switch (isFinished()) {
                            case PRINTER_NOT_CONNECTED:
                                printerConnectorResultI.isNotConnected(WaterDeliveryApplication.resources.getString(R.string.txt_85));
                                break;

                            case PRINTER_SUCCESSFUl:
                                printerConnectorResultI.isNotConnected(WaterDeliveryApplication.resources.getString(R.string.txt_86));
                                setFinished(0);
                                break;

                            case IMAGE_NOT_SAVED:
                                printerConnectorResultI.isNotConnected(WaterDeliveryApplication.resources.getString(R.string.txt_91));
                                setFinished(0);
                                break;
                        }
                        break;
                }
                break;

            case ZQ110.MESSAGE_DEVICE_NAME:
                String connectedDeviceName = msg.getData().getString(ZQ110.KEY_STRING_DEVICE_NAME);
                printerConnectorResultI.isConnected(connectedDeviceName);
                break;

            case ZQ110.MESSAGE_TOAST:
                printerConnectorResultI.advice(ZQ110.KEY_STRING_TOAST);
                break;

            case ZQ110.MESSAGE_PRINT_COMPLETE:
                printerConnectorResultI.printingCompleted();
                break;
        }
*/
        return true;
    }

    public int isFinished() {
        return isFinished;
    }

    public void setFinished(int finished) {
        isFinished = finished;
    }

    public interface PrinterConnectorResultI {

        void isBeingConnected();
        void isConnected(String name);
        void communicationEnabled();
        void advice(String msg);
        void isNotConnected(String msg);

        void printingCompleted();
    }
}
