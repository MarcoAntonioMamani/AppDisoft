package com.luisbar.waterdelivery.presentation.view.activity;

import com.luisbar.waterdelivery.common.eventbus.LogOutFailedV;
import com.luisbar.waterdelivery.common.eventbus.LogOutSucceededV;

/**
 * Interface that define all methods and listener from MainActivity
 */
public interface MainActivityI {

    void configActivity();

    void logOutSucceded(LogOutSucceededV logOutSucceededV);
    void logOutFailed(LogOutFailedV logOutFailedV);
}
