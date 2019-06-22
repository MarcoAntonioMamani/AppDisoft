package com.luisbar.waterdelivery.presentation.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.WaterDeliveryApplication;
import com.luisbar.waterdelivery.common.config.Config;
import com.luisbar.waterdelivery.common.eventbus.EventBusMask;
import com.luisbar.waterdelivery.common.eventbus.SignedInFailedV;
import com.luisbar.waterdelivery.common.eventbus.SignedInSucceededP;
import com.luisbar.waterdelivery.common.eventbus.UserLoggedV;
import com.luisbar.waterdelivery.data.Utilitario.GlobalUtil;
import com.luisbar.waterdelivery.presentation.presenter.FcmPresenter;
import com.luisbar.waterdelivery.presentation.presenter.LoginPresenter;
import com.luisbar.waterdelivery.presentation.view.activity.ConfigActivity;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment implements LoginFragmentI {

    @BindView(R.id.input_code)
    TextInputEditText inputCode;
    @BindView(R.id.input_identity_card)
    TextInputEditText inputIdentityCard;
    @BindView(R.id.btn_enter)
    Button btnEnter;
    @BindView(R.id.id_login_lbl_Service)
    TextView mService;
    @BindView(R.id.progress_login)
    ProgressBar progressLogin;

    private LoginPresenter mLoginPresenter;
    private FcmPresenter mFcmPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        configFragment(view);
        mService.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        mService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ConfigActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * It subscribe the listener and get the logged user
     */
    @Override
    public void onStart() {
        super.onStart();
        EventBusMask.subscribe(this);
        mLoginPresenter.onStart();
        mLoginPresenter.getCurrentUser();
    }

    /**
     * It unsubscribe the listener
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusMask.unsubscribe(this);
        mLoginPresenter.onDestroy();
    }

    /**
     * Listener for onClick about all controls
     */
    @OnClick(R.id.btn_enter)
    public void onClick() {
        String code = inputCode.getText().toString();
        GlobalUtil.CodeRepartidor=inputCode.getText().toString();
        String identityCard = inputIdentityCard.getText().toString();
        sigIn(code, identityCard);
        Config.CODE_REPARTIDOR=code;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("CODE", code);
        editor.commit();
    }

    /**
     * It initialize butterknife and initialize the global vars
     * @param view View for initializing butterknife
     */
    @Override
    public void configFragment(View view) {
        ButterKnife.bind(this, view);
        mLoginPresenter = new LoginPresenter();
        mFcmPresenter = new FcmPresenter();
    }

    /**
     * Method for sign in with the code and identity card
     * @param code Employee id
     * @param identityCard Identity Card from employee
     */
    @Override
    public void sigIn(String code, String identityCard) {
        enableOrDisableControls(false);
        WaterDeliveryApplication application = (WaterDeliveryApplication) getActivity().getApplicationContext();
        mLoginPresenter.signIn(code, identityCard, application.createRestAdpater());
    }

    /**
     * Enable or disable controls by according to the parameter
     * @param flag Boolean param for enabling or disabling the controls
     */
    @Override
    public void enableOrDisableControls(boolean flag) {
        inputCode.setEnabled(flag);
        inputIdentityCard.setEnabled(flag);
        btnEnter.setEnabled(flag);

        progressLogin.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        progressLogin.setVisibility(flag ? View.GONE : View.VISIBLE);
    }

    /**
     * Listener for signIn method when it has succeeded
     * @param signedInSucceededV Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void signedSucceeded(SignedInSucceededP signedInSucceededV) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        WaterDeliveryApplication application = (WaterDeliveryApplication) getActivity().getApplicationContext();

        String fcmId = settings.getString(Config.FCM_ID, null);

        mFcmPresenter.saveFcmId(fcmId, application.createRestAdpater());
        goToHome(signedInSucceededV.getResult().toString());
    }

    /**
     * Listener for signIn method when it has failed
     * @param signedInFailedV Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void signeedFailed(SignedInFailedV signedInFailedV) {
        showSnackBar(signedInFailedV.getError().toString());
        enableOrDisableControls(true);
    }

    /**
     * Listener for the getCurrentUser method in LoginPresenter
     * @param userLoggedV Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void therIsUserLogged(UserLoggedV userLoggedV) {
        if (userLoggedV.getName() != null)
            goToHome(userLoggedV.getName());
    }

    /**
     * Go to HomeLefFragment and send the name to it
     * about the current logged user
     * @param name Name from logged user
     */
    @Override
    public void goToHome(String name) {
        removeCurrentFragment();

        LoginFragmentNestedI loginFragmentNestedI = (LoginFragmentNestedI) getActivity();
        loginFragmentNestedI.setViewPager(name);
    }

    /**
     * Interface for communicating with the MainActivity
     */
    public interface LoginFragmentNestedI {
        void setViewPager(String name);
    }
}
