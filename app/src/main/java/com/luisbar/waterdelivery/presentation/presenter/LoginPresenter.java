package com.luisbar.waterdelivery.presentation.presenter;

import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.WaterDeliveryApplication;
import com.luisbar.waterdelivery.common.eventbus.EventBusMask;
import com.luisbar.waterdelivery.common.eventbus.SignedInFailedP;
import com.luisbar.waterdelivery.common.eventbus.SignedInSucceededV;
import com.luisbar.waterdelivery.common.eventbus.UserLoggedP;
import com.luisbar.waterdelivery.common.eventbus.UserLoggedV;
import com.luisbar.waterdelivery.domain.interactor.CurrentUserInteractor;
import com.luisbar.waterdelivery.domain.interactor.SignInInteractor;
import com.luisbar.waterdelivery.domain.interactor.UseCase;
import com.luisbar.waterdelivery.common.eventbus.SignedInFailedV;
import com.luisbar.waterdelivery.common.eventbus.SignedInSucceededP;
import com.luisbar.waterdelivery.presentation.model.UserModel;

import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.List;

public class LoginPresenter implements LoginPresenterI {

    private UseCase mUseCase;

    /**
     * Method for sign in with the code and identity card,
     * in adition it receive the RestAdapter in its last parameter
     * @param code Employee id
     * @param identityCard Identity Card from employee
     * @param object RestAdapter
     */
    @Override
    public void signIn(String code, String identityCard, Object object) {
        List<String> fields = Arrays.asList(code, identityCard);

        if (!thereIsAEmptyField(fields)) {
            mUseCase = new SignInInteractor();
            mUseCase.execute(new UserModel(code, identityCard), object);
        } else
            EventBusMask.post(new SignedInFailedV(WaterDeliveryApplication.resources.getString(R.string.txt_31)));
    }

    /**
     * Get the current user logged, if is there?
     */
    @Override
    public void getCurrentUser() {
        mUseCase = new CurrentUserInteractor();
        mUseCase.execute(null, null);
    }

    /**
     * It verify if the data is correct
     * @param fields Data for validating
     * @return boolean Boolean equal true if there is an empty field and false if there is not
     */
    @Override
    public boolean thereIsAEmptyField(List<String> fields) {
        boolean flag = false;

        for (String field : fields) {
            if (field.toString().isEmpty())
                flag = true;
        }

        return flag;
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

    /**
     * Listener for the signIn method when it has succeeded
     * @param signedInSucceededP Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void signedSucceeded(SignedInSucceededP signedInSucceededP) {
        EventBusMask.post(new SignedInSucceededV(signedInSucceededP.getResult()));
    }

    /**
     * Listener for the signIn method when it has failed
     * @param signedInFailedP Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void signeedFailed(SignedInFailedP signedInFailedP) {
        EventBusMask.post(new SignedInFailedV(signedInFailedP.getError()));
    }

    /**
     * Listener for getCurrentUser method in LoginRepository
     * @param userLoggedP Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void therIsUserLogged(UserLoggedP userLoggedP) {
        EventBusMask.post(new UserLoggedV(userLoggedP.getName()));
    }
}
