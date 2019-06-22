package com.luisbar.waterdelivery.domain.interactor;

import com.luisbar.waterdelivery.data.repository.LoginRepository;
import com.luisbar.waterdelivery.domain.Mapper;
import com.luisbar.waterdelivery.domain.model.UserModel;

public class SignInInteractor implements UseCase {

    private LoginRepository mLoginRepository;
    private Mapper mMapper;

    public SignInInteractor() {
        this.mLoginRepository = new LoginRepository();
        this.mMapper = new Mapper();
    }

    @Override
    public void execute(Object obj1, Object obj2) {
        UserModel userModel = mMapper.userModelPToUserModelD((com.luisbar.waterdelivery.presentation.model.UserModel) obj1);

        mLoginRepository.signIn(userModel.getCode(), userModel.getIdentityCard(), obj2);
    }
}
