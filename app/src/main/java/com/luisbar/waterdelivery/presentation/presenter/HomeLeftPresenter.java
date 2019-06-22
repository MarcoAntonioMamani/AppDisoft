package com.luisbar.waterdelivery.presentation.presenter;

import com.luisbar.waterdelivery.common.eventbus.AllDataFailedLeftP;
import com.luisbar.waterdelivery.common.eventbus.AllDataFailedLeftV;
import com.luisbar.waterdelivery.common.eventbus.EventBusMask;
import com.luisbar.waterdelivery.common.eventbus.LogOutFailedP;
import com.luisbar.waterdelivery.common.eventbus.LogOutFailedV;
import com.luisbar.waterdelivery.common.eventbus.LogOutSucceededP;
import com.luisbar.waterdelivery.common.eventbus.LogOutSucceededV;
import com.luisbar.waterdelivery.common.eventbus.RequestLeftP;
import com.luisbar.waterdelivery.common.eventbus.RequestLeftV;
import com.luisbar.waterdelivery.domain.interactor.AllData;
import com.luisbar.waterdelivery.domain.interactor.LogOut;
import com.luisbar.waterdelivery.domain.interactor.UseCase;

import org.greenrobot.eventbus.Subscribe;

public class HomeLeftPresenter implements HomeLeftPresenterI {

    private UseCase mUseCase;

    /**
     * Method for log out
     */
    @Override
    public void logOut() {
        mUseCase = new LogOut();
        mUseCase.execute(null, null);
    }

    /**
     * Method for getting all data from the API
     * @param object RestAdapter
     */
    @Override
    public void getAllData(Object object) {
        mUseCase = new AllData();
        mUseCase.execute(object, null);
    }

    /**
     * Listener for logOut methot when it has succeeded
     * @param logOutSucceededP Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void logOutSucceeded(LogOutSucceededP logOutSucceededP) {
        EventBusMask.post(new LogOutSucceededV());
    }

    /**
     * Listener for logOut methot when it has failed
     * @param logOutFailedP Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void logOutFailed(LogOutFailedP logOutFailedP) {
        EventBusMask.post(new LogOutFailedV());
    }

    /**
     * Listener when the getAllData method has been succeeded
     * @param requestLeftP Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void loadRecyclerView(RequestLeftP requestLeftP) {
        EventBusMask.post(new RequestLeftV(requestLeftP.getObject()));
    }

    /**
     * Listener when the getAllData method has been failed
     * @param allDataFailedLeftP Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void getAllDataFailed(AllDataFailedLeftP allDataFailedLeftP) {
        EventBusMask.post(new AllDataFailedLeftV(allDataFailedLeftP.getObject()));
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
