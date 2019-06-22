package com.luisbar.waterdelivery.presentation.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.luisbar.waterdelivery.presentation.view.fragment.HomeLeftFragment;
import com.luisbar.waterdelivery.presentation.view.fragment.HomeRightFragment;

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {

    private int quantity = 2;
    private String[] namesFragment = {"Dictados", "Entregados"};

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeLeftFragment();

            case 1:
                return  new HomeRightFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return quantity;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);

        return namesFragment[position];
    }
}
