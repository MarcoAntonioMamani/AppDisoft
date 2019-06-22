package com.luisbar.waterdelivery.presentation.presenter.zebra;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.WaterDeliveryApplication;
import com.luisbar.waterdelivery.common.eventbus.EventBusMask;


import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.graphics.ZebraImageFactory;
import com.zebra.sdk.graphics.ZebraImageI;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;
import com.zebra.sdk.util.internal.Base64;
public class PrinterManager implements DiscoveryResult.DiscoveryResultI,
        PrinterConnectorResult.PrinterConnectorResultI {

    @BindView(R.id.text_invoice_number)
    TextView textInvoiceNumber;

    private Context mContext;
    private  Connection mZq110;
  private ZebraPrinter zebraPrinter;
    private Connection connection;
    private View mView;
    private int items;

    private PrinterConnectorResult mPrinterConnectorResult;

    public PrinterManager(Context mContext) {
        this.mContext = mContext;
    }

    public void searchPrinters(View view, int items) {
        this.mView = view;
        this.items = items;

      //  mZq110 = new (mContext, new Handler(new DiscoveryResult(this)), null);
        //mZq110.findBluetoothPrinters();

        ButterKnife.bind(this, mView);
    }

    @Override
    public void foundPrinter(BluetoothDevice device) {
        mPrinterConnectorResult = new PrinterConnectorResult(this);
      //  mZq110 = new ZQ110(mContext, new Handler(mPrinterConnectorResult), null);
       // mZq110.connect(device.getAddress().toString());
    }

    @Override
    public void discoveryFinished() {

    }

    @Override
    public void discoveryError(String error) {
        EventBusMask.post(error);
    }

    @Override
    public void isBeingConnected() {
        Log.d("isBeingConnected: ", "ok");
    }

    @Override
    public void isConnected(String name) {
        Log.d("isConnected: ", name);
    }

    @Override
    public void communicationEnabled() {
        final Bitmap bm = createClusterBitmap();

        new Thread(new Runnable() {
            @Override
            public void run() {
            final Bitmap bm = createClusterBitmap();
            FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();


            StorageReference mReference = mFirebaseStorage.getReference();
            StorageReference mImageReference = mReference.child(textInvoiceNumber.getText().toString() + ".png");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = mImageReference.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    mPrinterConnectorResult.setFinished(2);
                 //   mZq110.disconnect();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                 //   mZq110.printBitmap(bm, ZQ110.ALIGNMENT_CENTER, ZQ110.BITMAP_WIDTH_FULL, 40, true);
                }
            });

            }
        }).start();
    }

    @Override
    public void advice(String msg) {
        Log.d("advice: ", msg);
    }

    @Override
    public void isNotConnected(String msg) {
        Log.e("isNotConnected: ", "ok");
        EventBusMask.post(msg);
    }

    @Override
    public void printingCompleted() {
        Log.e("printingCompleted: ", "ok");
        mPrinterConnectorResult.setFinished(1);
       // mZq110.disconnect();
    }

    private Bitmap createClusterBitmap() {
        int width = (int) getWidthAndHeightByDensity().get(0);
        int height = (int) getWidthAndHeightByDensity().get(1);

        mView.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height + items, View.MeasureSpec.EXACTLY));
        mView.layout(0, 0, mView.getMeasuredWidth(),mView.getMeasuredHeight());

        final Bitmap clusterBitmap = Bitmap.createBitmap(mView.getMeasuredWidth(),
                mView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(clusterBitmap);
        mView.draw(canvas);

        return clusterBitmap;
    }

    private List getWidthAndHeightByDensity() {
        float density = WaterDeliveryApplication.resources.getDisplayMetrics().density;
        int width = 0, height = 0;
        Log.d("createClusterBitmap: ", density+"");

        if (density >= 3.0) {// xxhdpi
            width = 676;
            /*height = 2280;*/
            height = 1254;
        } else if (density >= 2.0) {// xhdpi
            width = 450;
            /*height = 1512;*/
            height = 831;
        } else if (density >= 1.5) {// hdpi
            width = 338;
            /*height = 1149;*/
            height = 631;
        } else if (density >= 1.0) {// mdpi
            width = 300;
            /*height = 1020;*/
            height = 561;
        }

        return Arrays.asList(width, height);
    }
}
