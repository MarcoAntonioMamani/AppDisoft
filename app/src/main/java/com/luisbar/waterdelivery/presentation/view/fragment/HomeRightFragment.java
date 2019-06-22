package com.luisbar.waterdelivery.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.common.config.Config;
import com.luisbar.waterdelivery.common.eventbus.EventBusMask;
import com.luisbar.waterdelivery.common.eventbus.RequestRightV;
import com.luisbar.waterdelivery.presentation.presenter.HomeRightPresenter;
import com.luisbar.waterdelivery.presentation.view.adapter.HomeRecyclerAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeRightFragment extends BaseFragment implements HomeRecyclerAdapter.HomeRecyclerAdapterI, HomeRightFragmentI {

    @BindView(R.id.rv_right_home)
    RecyclerView rvHome;
    @BindView(R.id.text_cash)
    TextView textCash;
    @BindView(R.id.text_credit)
    TextView textCredit;

    private HomeRightPresenter mHomeRightPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right_home, container, false);
        configFragment(view);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * It subscribe the listener
     */
    @Override
    public void onStart() {
        super.onStart();
        EventBusMask.subscribe(this);
        mHomeRightPresenter.onStart();
    }

    /**
     * It unsubscribe the listener
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mHomeRightPresenter.onDestroy();
    }

    /**
     * Show detail about selected item
     */
    @Override
    public void showDetail(List detail) {
        OrderFragment orderFragment = new OrderFragment();
        Bundle args = new Bundle();

        args.putSerializable(Config.DETAIL, (Serializable) detail);
        args.putInt(Config.FLAG_FOR_ORDER, 1);
        orderFragment.setArguments(args);

        changeFragmentAndSaveTransition(R.id.fragment_container, orderFragment, Config.ORDER_FRAGMENT);
    }

    /**
     * It initialize butterknife and the global variables
     *
     * @param view
     */
    @Override
    public void configFragment(View view) {
        ButterKnife.bind(this, view);
        mHomeRightPresenter = new HomeRightPresenter();
    }

    /**
     * Listener for loading the recyclerview
     *
     * @param requestRightV Pojo for recognizing the listener
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void loadRecyclerView(RequestRightV requestRightV) {
        List historical = new ArrayList();
        historical.addAll((Collection) requestRightV.getObject());
        List amounts = null;

        if (!historical.isEmpty()) amounts = (List) historical.remove(historical.size()-1);

        HomeRecyclerAdapter homeRecyclerAdapter = new HomeRecyclerAdapter(historical, this);

        rvHome.setAdapter(homeRecyclerAdapter);
        rvHome.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        textCash.setText(amounts != null ? amounts.get(0).toString() : "0");
        textCredit.setText(amounts != null ? amounts.get(1).toString() : "0");
    }
}
