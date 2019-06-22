package com.luisbar.waterdelivery.presentation.presenter;

import com.google.android.gms.maps.model.LatLng;
import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.WaterDeliveryApplication;
import com.luisbar.waterdelivery.common.eventbus.ClientAddedFailedP;
import com.luisbar.waterdelivery.common.eventbus.ClientAddedFailedV;
import com.luisbar.waterdelivery.common.eventbus.ClientAddedSucceededP;
import com.luisbar.waterdelivery.common.eventbus.ClientAddedSucceededV;
import com.luisbar.waterdelivery.common.eventbus.EventBusMask;
import com.luisbar.waterdelivery.domain.interactor.AddClient;
import com.luisbar.waterdelivery.domain.interactor.UseCase;
import com.luisbar.waterdelivery.presentation.model.ClientModel;

import org.greenrobot.eventbus.Subscribe;

public class ClientPresenter implements ClientPresenterI {

    private UseCase mUseCase;

    @Override
    public void addClient(String name, String clientNit, String reason, String nit, String address, String cellphone, LatLng location, Object object) {
        if (!name.isEmpty() && !reason.isEmpty()
                && !nit.isEmpty() && !address.isEmpty()
                && !cellphone.isEmpty() && location != null
                && !clientNit.isEmpty()) {

            ClientModel clientModel = new ClientModel(name, clientNit, reason, nit, address, cellphone, location);
            mUseCase = new AddClient();

            mUseCase.execute(clientModel, object);
        } else
            EventBusMask.post(new ClientAddedFailedV(WaterDeliveryApplication.resources.getString(R.string.txt_82)));
    }

    @Override
    public void onStart() {
        EventBusMask.subscribe(this);
    }

    @Override
    public void onDestroy() {
        EventBusMask.unsubscribe(this);
    }

    /**
     * Listener that is triggered when the clien has been saved successfully
     * @param clientAddedSucceededP Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void clientAddedSucceeded(ClientAddedSucceededP clientAddedSucceededP) {
        EventBusMask.post(new ClientAddedSucceededV(clientAddedSucceededP.getmObject()));
    }

    /**
     * Listener that is triggered when the clien has been not saved successfully
     * @param clientAddedFailedP Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void clientAddedFailed(ClientAddedFailedP clientAddedFailedP) {
        EventBusMask.post(new ClientAddedFailedV(clientAddedFailedP.getmObject()));
    }
}
