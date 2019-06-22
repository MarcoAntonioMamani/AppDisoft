package com.luisbar.waterdelivery.presentation.presenter;

import com.luisbar.waterdelivery.common.eventbus.RequestRightP;

/**
 * Interface that define all methods and listener from HomeRightPresenter
 */
public interface HomeRightPresenterI {

    void loadRecyclerView(RequestRightP requestRightP);

    void onStart();
    void onDestroy();
}
