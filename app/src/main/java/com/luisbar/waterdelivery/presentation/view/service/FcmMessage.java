package com.luisbar.waterdelivery.presentation.view.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.common.config.Config;
import com.luisbar.waterdelivery.presentation.presenter.FcmPresenter;
import com.luisbar.waterdelivery.presentation.view.activity.MainActivity;

public class FcmMessage extends FirebaseMessagingService {

    private FcmPresenter mFcmPresenter;

    /**
     * It receives the messages from fcm service
     * @param remoteMessage RemoteMessage object
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        mFcmPresenter = new FcmPresenter();
        String data = remoteMessage.getData().get(Config.NEW_REQUEST);

        mFcmPresenter.saveNewRequest(data, null);
        showNotification();
    }


    private void showNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        TaskStackBuilder mStackBuilder = TaskStackBuilder.create(this);

        Intent mIntent = new Intent(this, MainActivity.class);

        mStackBuilder.addParentStack(MainActivity.class);
        mStackBuilder.addNextIntent(mIntent);

        PendingIntent mPendingIntent =
                mStackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        Notification mNotification = mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                                            .setContentTitle(getString(R.string.txt_63))
                                            .setContentText(getString(R.string.txt_64))
                                            .setContentIntent(mPendingIntent)
                                            .setAutoCancel(true)
                                            .setDefaults(Notification.DEFAULT_SOUND)
                                            .build();

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(123, mNotification);
    }
}
