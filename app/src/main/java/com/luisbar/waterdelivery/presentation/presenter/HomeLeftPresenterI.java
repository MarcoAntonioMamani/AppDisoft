package com.luisbar.waterdelivery.presentation.presenter;

import com.luisbar.waterdelivery.common.eventbus.AllDataFailedLeftP;
import com.luisbar.waterdelivery.common.eventbus.LogOutFailedP;
import com.luisbar.waterdelivery.common.eventbus.LogOutSucceededP;
import com.luisbar.waterdelivery.common.eventbus.RequestLeftP;

/**
 * Interface that define all methods and listener from HomeLeftPresenter
 */
public interface HomeLeftPresenterI {

    void logOut();
    void getAllData(Object object);

    void logOutSucceeded(LogOutSucceededP logOutSucceededP);
    void logOutFailed(LogOutFailedP logOutFailedP);
    void loadRecyclerView(RequestLeftP requestLeftP);
    void getAllDataFailed(AllDataFailedLeftP allDataFailedLeftP);

    void onStart();
    void onDestroy();
}
