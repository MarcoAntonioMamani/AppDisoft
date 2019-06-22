package com.luisbar.waterdelivery.presentation.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        Intent i=new Intent(SplashActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }


}
