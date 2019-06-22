package com.luisbar.waterdelivery.presentation.view.service;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.luisbar.waterdelivery.common.config.Config;

public class FcmToken extends FirebaseInstanceIdService {

    /**
     * It receives the token for each user registered in fcm service
     */
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("onTokenRefresh", refreshedToken);
        setFcmId(refreshedToken);
    }

    /**
     * It saves the fcm id for sending later to the cloud
     * @param id Fcm id
     */
    private void setFcmId(String id) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(Config.FCM_ID, id);
        editor.commit();
    }
}
