package com.luisbar.waterdelivery.presentation.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luisbar.waterdelivery.R;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.DetailOrderViewHolder> {

    private List request;
    private Double total;
    private int flag;
    private Fragment fragment;

    private final int HEADER = 0;
    private final int ITEM = 1;
    private final int FOOTER = 2;
    private final int NOT_DELIVERED = 0;
    private final int DELIVERED = 1;
    private final int NEW = 2;

    private int positionSelected;

    public OrderRecyclerAdapter(List request, Double total, int flag, Fragment fragment) {
        this.request = request;
        this.total = total;
        this.flag = flag;
        this.fragment = fragment;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return HEADER;
        } else if (isFooter(position)) {
            return FOOTER;
        }

        return ITEM;
    }

    @Override
    public DetailOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DetailOrderViewHolder detailOrderViewHolder = null;

        switch (viewType) {
            case HEADER:
                View header = LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.item_order_header, parent, false);
                detailOrderViewHolder  = new DetailOrderViewHolder(header);
                break;

            case ITEM:
                View item = LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.item_order, parent, false);
                detailOrderViewHolder = new DetailOrderItemViewHolder(item);
                break;

            case FOOTER:
                View footer = LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.item_order_footer, parent, false);
                detailOrderViewHolder = new DetailOrderFooterViewHolder(footer);
                break;
        }

        return detailOrderViewHolder;
    }

    @Override
    public void onBindViewHolder(DetailOrderViewHolder holder, int position) {
        if (position > 0 && position <= request.size())
            holder.bindOrder((List) request.get(position - 1), position - 1);
        else
            holder.bindOrder(null);
    }

    @Override
    public int getItemCount() {
        return request.size() + 2;
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    public boolean isFooter(int position) {
        return position == request.size() + 1;
    }

    public void addElementAndUpdateTotal(Object name, Object quantity, Object price, Double total, Object code) {
        request.add(Arrays.asList(name, quantity, price, total, code));
        this.total = this.total + total;
        notifyDataSetChanged();
    }

    public void removeItemSelected() {
        total = total - Double.valueOf(((List)request.get(positionSelected)).get(3).toString());
        request.remove(positionSelected);
        notifyDataSetChanged();
    }

    public void updateQuantity(int quantity) {
        List list = (List) request.get(positionSelected);
        int difference = Integer.valueOf(list.get(1).toString()) - quantity;

        total = difference >= 0
                ? total - (difference * Double.valueOf(list.get(2).toString()))
                : total + (Math.abs(difference) * Double.valueOf(list.get(2).toString()));

        list.set(1, quantity);
        list.set(3, quantity * Double.valueOf(list.get(2).toString()));
        notifyDataSetChanged();
    }

    public void removeAll() {
        request.clear();
        total = 0.0;
        notifyDataSetChanged();
    }

    public List getRequest() {
        return request;
    }

    public Double getTotal() {
        return total;
    }

    public class DetailOrderViewHolder extends RecyclerView.ViewHolder {

        public DetailOrderViewHolder(View itemView) {
            super(itemView);
        }

        protected void bindOrder(List data) {

        }

        protected void bindOrder(List data, int position) {

        }
    }

    public class DetailOrderItemViewHolder extends DetailOrderViewHolder {

        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_product_price)
        TextView tvProductPrice;
        @BindView(R.id.tv_product_quantity)
        TextView tvProductQuantity;
        @BindView(R.id.tv_product_total)
        TextView tvProductTotal;
        @BindView(R.id.iv_dots)
        ImageView ivDots;

        public DetailOrderItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindOrder(List data, int position) {
            tvProductName.setText(data.get(0).toString());
            tvProductQuantity.setText(data.get(1).toString());
            tvProductPrice.setText(data.get(2).toString());
            tvProductTotal.setText(data.get(3).toString());
            ivDots.setTag(position);

            if (flag == DELIVERED) ivDots.setImageBitmap(null);
        }

        @OnClick(R.id.iv_dots)
        public void onClick() {
            OrderRecyclerAdapterNestedI listener = (OrderRecyclerAdapterNestedI) fragment;
            positionSelected = (int) ivDots.getTag();

            switch (flag) {
                case NOT_DELIVERED:
                    listener.showDialogInNotDelivered();
                    break;

                case NEW:
                    listener.showDialogInNew();
                    break;
            }
        }
    }

    public class DetailOrderFooterViewHolder extends DetailOrderViewHolder {

        @BindView(R.id.tv_product_total)
        TextView tvProductTotal;

        public DetailOrderFooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindOrder(List data) {
            tvProductTotal.setText(String.valueOf(total));
        }
    }

    /**
     * Interface that is executed when the dots menu is clicked
     */
    public interface OrderRecyclerAdapterNestedI {

        void showDialogInNotDelivered();
        void showDialogInNew();
    }
}
