package com.luisbar.waterdelivery.presentation.view.fragment;

import android.view.View;

import com.luisbar.waterdelivery.common.eventbus.SignedInFailedV;
import com.luisbar.waterdelivery.common.eventbus.SignedInSucceededP;
import com.luisbar.waterdelivery.common.eventbus.UserLoggedV;

/**
 * Interface that define all methods and listener from LoginFragment
 */
public interface LoginFragmentI {

    void configFragment(View view);
    void sigIn(String code, String identityCard);
    void enableOrDisableControls(boolean flag);
    void goToHome(String name);

    void signedSucceeded(SignedInSucceededP signedInSucceededV);
    void signeedFailed(SignedInFailedV signedInFailedV);
    void therIsUserLogged(UserLoggedV userLoggedV);
}
