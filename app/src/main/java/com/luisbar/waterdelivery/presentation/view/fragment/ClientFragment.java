package com.luisbar.waterdelivery.presentation.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.WaterDeliveryApplication;
import com.luisbar.waterdelivery.common.config.Config;
import com.luisbar.waterdelivery.common.eventbus.ClientAddedFailedV;
import com.luisbar.waterdelivery.common.eventbus.ClientAddedSucceededV;
import com.luisbar.waterdelivery.common.eventbus.EventBusMask;
import com.luisbar.waterdelivery.presentation.presenter.ClientPresenter;
import com.luisbar.waterdelivery.presentation.view.dialog.ProgressDialogFragment;
import com.luisbar.waterdelivery.presentation.view.listener.CustomOnBackPressed;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClientFragment extends BaseFragment implements ClientFragmentI, CustomOnBackPressed,
        OnMapReadyCallback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_reason)
    EditText inputReason;
    @BindView(R.id.input_nit_client)
    EditText inputNitClient;
    @BindView(R.id.input_address)
    EditText inputAddress;
    @BindView(R.id.input_phone)
    EditText inputPhone;
    @BindView(R.id.mapview_client)
    MapView mapviewClient;
    @BindView(R.id.input_nit_reason)
    EditText inputNitReason;
    @BindView(R.id.fragment_client)
    LinearLayout fragmentClient;

    private ClientPresenter mClientPresenter;
    private LatLng mLatLng;
    private GoogleMap mGoogleMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_client, container, false);
        configFragment(view, savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(getString(R.string.txt_9));

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        ClientFragmentI clientFragmentI = (ClientFragmentI) activity;
        clientFragmentI.hideAppBar();

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(inputName, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapviewClient.onStart();
        mClientPresenter.onStart();
        EventBusMask.subscribe(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapviewClient.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapviewClient.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapviewClient.onDestroy();
        mClientPresenter.onDestroy();
        EventBusMask.unsubscribe(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapviewClient.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapviewClient.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ClientFragmentI clientFragmentI = (ClientFragmentI) getActivity();
        clientFragmentI.setToolbar();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.client_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.action_check:
                showDialog(Config.PROGRESS_DIALOG_FRAGMENT, ProgressDialogFragment.newInstance(getString(R.string.txt_47),
                        getString(R.string.txt_77)));

                WaterDeliveryApplication application = (WaterDeliveryApplication) getActivity().getApplicationContext();

                mClientPresenter.addClient(inputName.getText().toString(),
                        inputNitClient.getText().toString(),
                        inputReason.getText().toString(),
                        inputNitReason.getText().toString(),
                        inputAddress.getText().toString(),
                        inputPhone.getText().toString(),
                        mLatLng,
                        application.createRestAdpater());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        removeCurrentFragment();
    }

    /**
     * It initializes the butterknife and google map
     *
     * @param view View object
     */
    @Override
    public void configFragment(View view, Bundle bundle) {
        ButterKnife.bind(this, view);

        MapsInitializer.initialize(this.getActivity());
        mapviewClient.onCreate(bundle);
        mapviewClient.getMapAsync(this);

        mClientPresenter = new ClientPresenter();
    }

    /**
     * Listener that is triggered when the clien has been saved successfully
     * @param clientAddedSucceededV Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void clientAddedSucceeded(ClientAddedSucceededV clientAddedSucceededV) {
        hideDialog(Config.PROGRESS_DIALOG_FRAGMENT);
        showSnackBar(clientAddedSucceededV.getmObject().toString());

        inputName.getText().clear();
        inputNitClient.getText().clear();
        inputReason.getText().clear();
        inputNitReason.getText().clear();
        inputAddress.getText().clear();
        inputPhone.getText().clear();
        mGoogleMap.clear();
        mLatLng = null;
        inputName.requestFocus();
    }

    /**
     * Listener that is triggered when the clien has been not saved successfully
     *
     * @param clientAddedFailedV Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void clientAddedFailed(ClientAddedFailedV clientAddedFailedV) {
        hideDialog(Config.PROGRESS_DIALOG_FRAGMENT);
        showSnackBar(clientAddedFailedV.getmObject().toString());
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng));
                ClientFragment.this.mLatLng = latLng;
            }
        });
    }

    /**
     * Interface for hide the appbar in MainActivity and set the toolbar
     */
    public interface ClientFragmentI {

        void setToolbar();
        void hideAppBar();
    }
}
