package com.luisbar.waterdelivery;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.luisbar.waterdelivery.presentation.view.activity.MainActivity;

public class SplashActivity extends AppCompatActivity {

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
