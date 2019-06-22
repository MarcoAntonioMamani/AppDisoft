package com.luisbar.waterdelivery.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.common.config.Config;
import com.luisbar.waterdelivery.presentation.view.listener.CustomOnBackPressed;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMapFragment extends BaseFragment implements OnMapReadyCallback, MyMapFragmentI, CustomOnBackPressed {

    @BindView(R.id.map_view)
    MapView mapView;

    private LatLng latLng;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        View view = layoutInflater.inflate(R.layout.fragment_map, viewGroup, false);
        configFragment(view, bundle);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    /**
     * Async method for showing the map
     * @param googleMap Map object for customizing it
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.mapa));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 13);

        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(cameraUpdate);
    }

    /**
     * It initialize butterknife, get the latlng and create the mapview
     * @param view View for initializing butterknife
     * @param bundle Bundle for initializing the mapview
     */
    @Override
    public void configFragment(View view, Bundle bundle) {
        ButterKnife.bind(this, view);

        Bundle args = getArguments();
        latLng = args.getParcelable(Config.LAT_LNG);

        MapsInitializer.initialize(this.getActivity());
        mapView.onCreate(bundle);
        mapView.getMapAsync(this);
    }

    @Override
    public void onBackPressed() {
        removeCurrentFragment();
    }
}
