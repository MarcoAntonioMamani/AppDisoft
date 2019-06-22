package com.luisbar.waterdelivery.presentation.presenter;

/**
 * Interface that define all methods and listener from FcmPresenter
 */
public interface FcmPresenterI {

    void saveFcmId(String id, Object object);
    void saveNewRequest(String json, Object object);
}
