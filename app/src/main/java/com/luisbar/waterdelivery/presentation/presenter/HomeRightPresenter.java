package com.luisbar.waterdelivery.presentation.presenter;

import com.luisbar.waterdelivery.common.eventbus.EventBusMask;
import com.luisbar.waterdelivery.common.eventbus.RequestRightP;
import com.luisbar.waterdelivery.common.eventbus.RequestRightV;

import org.greenrobot.eventbus.Subscribe;

public class HomeRightPresenter implements HomeRightPresenterI {

    /**
     * Listener when the getAllData method has been succeeded
     * @param requestRightP Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void loadRecyclerView(RequestRightP requestRightP) {
        EventBusMask.post(new RequestRightV(requestRightP.getObject()));
    }

    /**
     * It is triggering when the fragment owner has been started
     * and it subscribe all the listener method
     */
    @Override
    public void onStart() {
        EventBusMask.subscribe(this);
    }

    /**
     * It is triggering when the fragment owner has been destroyed
     * and it unsubscribe all the listener method
     */
    @Override
    public void onDestroy() {
        EventBusMask.unsubscribe(this);
    }
}
