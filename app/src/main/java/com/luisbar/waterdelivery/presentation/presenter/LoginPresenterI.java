package com.luisbar.waterdelivery.presentation.presenter;

import com.luisbar.waterdelivery.common.eventbus.SignedInFailedP;
import com.luisbar.waterdelivery.common.eventbus.SignedInSucceededP;
import com.luisbar.waterdelivery.common.eventbus.UserLoggedP;

import java.util.List;

/**
 * Interface that define all methods and listener from LoginPresenter
 */
public interface LoginPresenterI {

    void signIn(String code, String identityCard, Object object);
    void getCurrentUser();
    boolean thereIsAEmptyField(List<String> fields);

    void onStart();
    void onDestroy();

    void signedSucceeded(SignedInSucceededP signedInSucceededP);
    void signeedFailed(SignedInFailedP signedInFailedP);
    void therIsUserLogged(UserLoggedP userLoggedP);
}
