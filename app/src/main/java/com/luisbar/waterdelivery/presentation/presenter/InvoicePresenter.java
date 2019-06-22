package com.luisbar.waterdelivery.presentation.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.presentation.presenter.zebra.PrinterManager;
import com.luisbar.waterdelivery.presentation.view.adapter.InvoiceRecyclerAdapter;

import net.glxn.qrgen.android.QRCode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InvoicePresenter {

    @BindView(R.id.rv_invoice)
    RecyclerView recyclerView;
    @BindView(R.id.iv_qr)
    ImageView ivQr;
    @BindView(R.id.text_name_enterprise)
    TextView textNameEnterprise;
    @BindView(R.id.text_owner)
    TextView textOwner;
    @BindView(R.id.text_office)
    TextView textOffice;
    @BindView(R.id.text_address)
    TextView textAddress;
    @BindView(R.id.text_cellphone)
    TextView textCellphone;
    @BindView(R.id.text_country)
    TextView textCountry;
    @BindView(R.id.text_sfc)
    TextView textSfc;
    @BindView(R.id.text_nit)
    TextView textNit;
    @BindView(R.id.text_invoice_number)
    TextView textInvoiceNumber;
    @BindView(R.id.text_autorization_number)
    TextView textAutorizationNumber;
    @BindView(R.id.text_category)
    TextView textCategory;
    @BindView(R.id.text_date)
    TextView textDate;
    @BindView(R.id.text_hour)
    TextView textHour;
    @BindView(R.id.text_client_name)
    TextView textClientName;
    @BindView(R.id.text_client_nit)
    TextView textClientNit;
    @BindView(R.id.text_code_control)
    TextView textCodeControl;
    @BindView(R.id.text_limit_date)
    TextView textLimitDate;
    @BindView(R.id.text_note_one)
    TextView textNoteOne;
    @BindView(R.id.text_note_two)
    TextView textNoteTwo;

    private Context mContext;
    private List lstDetail;
    private List<String> lstInf;
    private Double mTotal;

    private PrinterManager mPrinterManager;

    public InvoicePresenter(Context context) {
        mPrinterManager = new PrinterManager(context);
        this.mContext = context;
    }

    public void print(View view, List lstDetail, List<String> lstInf, Double mTotal, int items) {
        --items;
        this.lstDetail = lstDetail;
        this.lstInf = lstInf;
        this.mTotal = mTotal;

        ButterKnife.bind(this, view);
        setData();
        mPrinterManager.searchPrinters(view, items * 80);
    }

    public void setData() {
        InvoiceRecyclerAdapter invoiceRecyclerAdapter = new InvoiceRecyclerAdapter(lstDetail, mTotal);

        recyclerView.setAdapter(invoiceRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        textNameEnterprise.setText(lstInf.get(0));
        textOwner.setText(lstInf.get(1));
        textOffice.setText(lstInf.get(2));
        textAddress.setText(lstInf.get(3));
        textCellphone.setText(lstInf.get(4));
        textCountry.setText(lstInf.get(5));
        textSfc.setText("Sfc: " + lstInf.get(6));
        textNit.setText("Nit: " + lstInf.get(7));
        textInvoiceNumber.setText("N°: " + lstInf.get(8));
        textAutorizationNumber.setText("Autorizacion Nro.: " + lstInf.get(9));
        textCategory.setText(lstInf.get(10));
        textDate.setText("Fecha: " + lstInf.get(11));
        textHour.setText("Hora: " + lstInf.get(12));
        textClientName.setText("Señor(es): " + lstInf.get(13));
        textClientNit.setText("Nit: " + lstInf.get(14));
        textCodeControl.setText("Codigo de control: " + lstInf.get(15));
        textLimitDate.setText("Fecha limite de emision: " + lstInf.get(16));
        textNoteOne.setText(lstInf.get(18));
        textNoteTwo.setText(lstInf.get(19));

        Bitmap bm = QRCode.from(lstInf.get(17)).withSize(230, 230).bitmap();
        ivQr.setImageBitmap(bm);
    }
}
