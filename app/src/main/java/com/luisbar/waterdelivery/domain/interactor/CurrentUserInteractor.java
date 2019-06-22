package com.luisbar.waterdelivery.domain.interactor;

import com.luisbar.waterdelivery.data.repository.LoginRepository;

public class CurrentUserInteractor implements UseCase {

    private LoginRepository mLoginRepository;

    public CurrentUserInteractor() {
        this.mLoginRepository = new LoginRepository();
    }

    @Override
    public void execute(Object obj1, Object obj2) {
        mLoginRepository.getCurrentUser();
    }
}
