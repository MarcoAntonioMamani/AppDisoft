package com.luisbar.waterdelivery.domain.interactor;

import com.luisbar.waterdelivery.data.repository.FcmRepository;

public class SaveFcmId implements UseCase {

    private FcmRepository mFcmRepository;

    public SaveFcmId() {
        this.mFcmRepository = new FcmRepository();
    }

    @Override
    public void execute(Object obj1, Object obj2) {
        mFcmRepository.saveFcmId(obj1.toString(), obj2);
    }
}
