package com.luisbar.waterdelivery.presentation.view.fragment;

import android.view.View;

import com.luisbar.waterdelivery.common.eventbus.RequestRightV;

/**
 * Interface that define all methods and listener from HomeRightFragment
 */
public interface HomeRightFragmentI {

    void configFragment(View view);

    void loadRecyclerView(RequestRightV requestRightV);
}
