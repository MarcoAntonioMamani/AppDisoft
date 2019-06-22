package com.luisbar.waterdelivery.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.WaterDeliveryApplication;
import com.luisbar.waterdelivery.common.config.Config;
import com.luisbar.waterdelivery.common.eventbus.AllDataFailedLeftV;
import com.luisbar.waterdelivery.common.eventbus.EventBusMask;
import com.luisbar.waterdelivery.common.eventbus.RequestLeftV;
import com.luisbar.waterdelivery.presentation.presenter.HomeLeftPresenter;
import com.luisbar.waterdelivery.presentation.view.adapter.HomeRecyclerAdapter;
import com.luisbar.waterdelivery.presentation.view.dialog.ProgressDialogFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeLeftFragment extends BaseFragment implements HomeRecyclerAdapter.HomeRecyclerAdapterI
        , HomeLeftFragmentI {

    @BindView(R.id.rv_left_home)
    RecyclerView rvHome;
    @BindView(R.id.fab_left_home)
    FloatingActionsMenu fabLeftHome;
    @BindView(R.id.fragment_left_home)
    CoordinatorLayout fragmentLeftHome;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;

    private HomeLeftPresenter mHomeLeftPresenter;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_left_home, container, false);
        configFragment(view);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * It subscribe the listeners and get data from the database or the cloud
     */
    @Override
    public void onStart() {
        super.onStart();

        EventBusMask.subscribe(this);
        mHomeLeftPresenter.onStart();
        showDialog(Config.PROGRESS_DIALOG_FRAGMENT, ProgressDialogFragment.newInstance(getString(R.string.txt_47), getString(R.string.txt_48)));

        WaterDeliveryApplication waterDeliveryApplication = (WaterDeliveryApplication)
                getActivity().getApplicationContext();mHomeLeftPresenter.getAllData(waterDeliveryApplication.createRestAdpater());
    }

    /**
     * It unsubscribe the listeners
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusMask.unsubscribe(this);
        mHomeLeftPresenter.onDestroy();
    }

    @OnClick({R.id.fab_add_order, R.id.fab_add_client})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add_order:
                OrderFragment orderFragment = new OrderFragment();
                Bundle args = new Bundle();

                args.putInt(Config.FLAG_FOR_ORDER, 2);
                orderFragment.setArguments(args);
                changeFragmentAndSaveTransition(R.id.fragment_container, orderFragment, Config.ORDER_FRAGMENT);
                break;

            case R.id.fab_add_client:
                ClientFragment clientFragment = new ClientFragment();
                changeFragmentAndSaveTransition(R.id.fragment_container, clientFragment, Config.CLIENT_FRAGMENT);
                break;
        }
        fabLeftHome.collapse();
    }

    /**
     * Show detail about selected item
     */
    @Override
    public void showDetail(List detail) {
        if (fabLeftHome.isExpanded())
            fabLeftHome.collapse();
        else {
            OrderFragment orderFragment = new OrderFragment();
            Bundle args = new Bundle();

            args.putSerializable(Config.DETAIL, (Serializable) detail);
            orderFragment.setArguments(args);

            changeFragmentAndSaveTransition(R.id.fragment_container, orderFragment, Config.ORDER_FRAGMENT);
        }
    }

    /**
     * It initialize butterknife and initialize the global variables
     *
     * @param view
     */
    @Override
    public void configFragment(View view) {
        ButterKnife.bind(this, view);
        this.mHomeLeftPresenter = new HomeLeftPresenter();

        fabLikeEvernote();
    }

    private void fabLikeEvernote() {
        frameLayout.getBackground().setAlpha(0);

        fabLeftHome.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                frameLayout.getBackground().setAlpha(240);
                frameLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        fabLeftHome.collapse();
                        return true;
                    }
                });
            }

            @Override
            public void onMenuCollapsed() {
                frameLayout.getBackground().setAlpha(0);
                frameLayout.setOnTouchListener(null);
            }
        });
    }

    /**
     * Listener when the getAllData method has been succeeded
     *
     * @param requestLeftV Pojo for reconizing the listener
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void loadRecyclerView(RequestLeftV requestLeftV) {
        List list = (List) requestLeftV.getObject();
        HomeRecyclerAdapter homeRecyclerAdapter = new HomeRecyclerAdapter(list, this);

        rvHome.setAdapter(homeRecyclerAdapter);
        rvHome.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        hideDialog(Config.PROGRESS_DIALOG_FRAGMENT);
    }

    /**
     * Listener when the getAllData method has been failed
     *
     * @param allDataFailedLeftV Pojo for recognizing the listener
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void getAllDataFailed(AllDataFailedLeftV allDataFailedLeftV) {
        showSnackBar(allDataFailedLeftV.getObject().toString());
        hideDialog(Config.PROGRESS_DIALOG_FRAGMENT);
    }
}
