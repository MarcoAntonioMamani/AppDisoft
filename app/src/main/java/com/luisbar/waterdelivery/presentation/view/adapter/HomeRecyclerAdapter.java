package com.luisbar.waterdelivery.presentation.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luisbar.waterdelivery.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.OrderViewHolder> {

    private List orders;
    private Fragment fragment;

    public HomeRecyclerAdapter(List orders, Fragment fragment) {
        this.orders = orders;
        this.fragment = fragment;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_home, parent, false);

        OrderViewHolder orderViewHolder = new OrderViewHolder(itemView);

        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        holder.bindOrder((List) orders.get(position));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_header)
        TextView tvHeader;
        @BindView(R.id.tv_detail)
        TextView tvDetail;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.iv_state)
        ImageView ivState;

        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindOrder(List order) {
            tvHeader.setText(order.get(1).toString());
            tvDetail.setText(order.get(2).toString());
            tvPhone.setText(order.get(3) + "-" + order.get(4));

            itemView.setTag(order.get(5));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            HomeRecyclerAdapterI homeRecyclerAdapterI = (HomeRecyclerAdapterI) fragment;
            homeRecyclerAdapterI.showDetail((List) v.getTag());
        }
    }

    /**
     * Interface that is executed when an item is clicked
     */
    public interface HomeRecyclerAdapterI {
        void showDetail(List detail);
    }
}
