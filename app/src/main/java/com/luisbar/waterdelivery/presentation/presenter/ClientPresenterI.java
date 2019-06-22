package com.luisbar.waterdelivery.presentation.presenter;

import com.google.android.gms.maps.model.LatLng;
import com.luisbar.waterdelivery.common.eventbus.ClientAddedFailedP;
import com.luisbar.waterdelivery.common.eventbus.ClientAddedSucceededP;

/**
 * Interface that define all methods and listener from ClientPresenter
 */

public interface ClientPresenterI {

    void addClient(String name, String nitClient, String reason, String nit, String email, String cellphone,
                   LatLng location, Object object);

    void onStart();
    void onDestroy();

    void clientAddedSucceeded(ClientAddedSucceededP clientAddedSucceededP);
    void clientAddedFailed(ClientAddedFailedP clientAddedFailedP);
}
