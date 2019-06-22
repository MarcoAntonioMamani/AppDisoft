package com.luisbar.waterdelivery.presentation.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.presentation.view.adapter.util.NumberToWordsConverter;

import java.util.Arrays;
import java.util.List;

public class InvoiceRecyclerAdapter extends RecyclerView.Adapter<InvoiceRecyclerAdapter.InvoiceViewHolder> {

    private List detail;
    private Double mTotal;

    private final int HEADER = 0;
    private final int ITEM = 1;
    private final int FOOTER = 2;

    public InvoiceRecyclerAdapter(List detail, Double mTotal) {
        this.detail = detail;
        this.mTotal = mTotal;
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
    public InvoiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;

        switch (viewType) {
            case HEADER:
                itemView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_invoice_header, parent, false);
                break;

            case ITEM:
                itemView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_invoice, parent, false);
                break;

            case FOOTER:
                itemView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_invoice_footer, parent, false);
                break;
        }

        InvoiceViewHolder invoiceViewHolder = new InvoiceViewHolder(itemView, viewType);

        return invoiceViewHolder;
    }

    @Override
    public void onBindViewHolder(InvoiceViewHolder holder, int position) {
        if (position > 0 && position <= detail.size())
            holder.bindOrder((List) detail.get(position - 1));
        else
            holder.bindOrder(Arrays.asList(mTotal));
    }

    @Override
    public int getItemCount() {
        return detail.size() + 2;
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    public boolean isFooter(int position) {
        return position == detail.size() + 1;
    }

    public class InvoiceViewHolder extends RecyclerView.ViewHolder {

        private int viewType;

        public InvoiceViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
        }

        private void bindOrder(List data) {
            switch (viewType) {
                case ITEM:
                    TextView textDetail = (TextView) itemView.findViewById(R.id.text_detail);
                    TextView textQuantity = (TextView) itemView.findViewById(R.id.text_quantity);
                    TextView textUnitPrice = (TextView) itemView.findViewById(R.id.text_unit_price);
                    TextView textTotal = (TextView) itemView.findViewById(R.id.text_total);

                    textDetail.setText(data.get(0).toString());
                    textQuantity.setText(data.get(1).toString());
                    textUnitPrice.setText(data.get(2).toString());
                    textTotal.setText(data.get(3).toString());
                    break;

                case FOOTER:
                    TextView textSubTotal = (TextView) itemView.findViewById(R.id.text_sub_total);
                    TextView textTot = (TextView) itemView.findViewById(R.id.text_total);
                    TextView textAmountInWords = (TextView) itemView.findViewById(R.id.text_amount_in_words);

                    textSubTotal.setText(data.get(0).toString());
                    textTot.setText(data.get(0).toString());
                    textAmountInWords.setText("Son: " + new NumberToWordsConverter().convertNumberToLetter(data.get(0).toString()));
                    break;
            }
        }
    }
}
