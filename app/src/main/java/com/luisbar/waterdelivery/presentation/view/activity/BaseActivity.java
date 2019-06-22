package com.luisbar.waterdelivery.presentation.view.activity;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BaseActivity extends AppCompatActivity {

    protected void loadFragment(int container, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(container, fragment)
                .commitAllowingStateLoss();
    }

    /**
     * It method remove the specified fragment
     */
    protected void removeCurrentFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .remove(fragment)
                .commit();

        fragmentManager.popBackStack();
    }

    protected void showSnackBar(String message) {
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }
}
