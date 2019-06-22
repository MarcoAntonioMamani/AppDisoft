package com.luisbar.waterdelivery.presentation.view.fragment;

import android.view.View;

import com.luisbar.waterdelivery.common.eventbus.AllDataFailedLeftV;
import com.luisbar.waterdelivery.common.eventbus.RequestLeftV;

/**
 * Interface that define all methods and listener from HomeLeftFragment
 */
public interface HomeLeftFragmentI {

    void configFragment(View view);

    void loadRecyclerView(RequestLeftV requestLeftV);
    void getAllDataFailed(AllDataFailedLeftV allDataFailedLeftV);
}
