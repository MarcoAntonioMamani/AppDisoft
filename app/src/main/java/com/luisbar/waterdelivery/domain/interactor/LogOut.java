package com.luisbar.waterdelivery.domain.interactor;

import com.luisbar.waterdelivery.data.repository.HomeLeftRepository;

public class LogOut implements UseCase {

    private HomeLeftRepository mHomeLeftRepository;

    public LogOut() {
        this.mHomeLeftRepository = new HomeLeftRepository();
    }

    @Override
    public void execute(Object obj1, Object obj2) {
        mHomeLeftRepository.logOut();
    }
}
