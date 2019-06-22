package com.luisbar.waterdelivery.presentation.presenter;

import com.luisbar.waterdelivery.domain.interactor.SaveFcmId;
import com.luisbar.waterdelivery.domain.interactor.SaveNewRequest;
import com.luisbar.waterdelivery.domain.interactor.UseCase;


public class FcmPresenter implements FcmPresenterI {

    private UseCase mUseCase;

    @Override
    public void saveFcmId(String id, Object object) {
        mUseCase = new SaveFcmId();
        mUseCase.execute(id, object);
    }

    @Override
    public void saveNewRequest(String json, Object object) {
        mUseCase = new SaveNewRequest();
        mUseCase.execute(json, null);
    }
}
