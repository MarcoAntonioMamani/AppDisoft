package com.luisbar.waterdelivery.domain.interactor;

import com.luisbar.waterdelivery.data.repository.HomeLeftRepository;

public class AllData implements UseCase {

    private HomeLeftRepository mHomeLeftRepository;

    public AllData() {
        this.mHomeLeftRepository = new HomeLeftRepository();
    }

    @Override
    public void execute(Object obj1, Object obj2) {
        mHomeLeftRepository.getAllData(obj1);
    }
}
