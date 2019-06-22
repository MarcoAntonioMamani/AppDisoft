package com.luisbar.waterdelivery.presentation.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.common.config.Config;
import com.luisbar.waterdelivery.common.eventbus.EventBusMask;
import com.luisbar.waterdelivery.presentation.presenter.InvoicePresenter;
import com.luisbar.waterdelivery.presentation.view.adapter.InvoiceRecyclerAdapter;
import com.luisbar.waterdelivery.presentation.view.dialog.ProgressDialogFragment;
import com.luisbar.waterdelivery.presentation.view.listener.CustomOnBackPressed;

import net.glxn.qrgen.android.QRCode;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InvoiceFragment extends BaseFragment implements InvoiceFragmentI, CustomOnBackPressed {

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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_invoice)
    LinearLayout llInvoice;

    private InvoicePresenter mInvoicePresenter;
    private List lstDetail;
    private List<String> lstInf;
    private Double mTotal;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.pdf_invoice, container, false);

        Bundle args = getArguments();

        lstDetail = (List) args.getSerializable(Config.DETAIL);
        lstInf = (List<String>) args.getSerializable(Config.LIST);
        mTotal = args.getDouble(Config.TOTAL);

        configFragment(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBusMask.subscribe(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBusMask.unsubscribe(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        InvoiceFragmentI invoiceFragmentI = (InvoiceFragmentI) getActivity();
        invoiceFragmentI.setToolbar();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.setGroupVisible(R.id.group_order, false);
        menu.setGroupVisible(R.id.group_home, false);

        inflater.inflate(R.menu.invoice_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.action_check:
                showDialog(Config.PROGRESS_DIALOG_FRAGMENT, ProgressDialogFragment.newInstance(getString(R.string.txt_47),
                        getString(R.string.txt_90)));

                LayoutInflater inflater = getLayoutInflater(getArguments());
                View mView = inflater.inflate(R.layout.pdf_invoice, null);

                mInvoicePresenter.print(mView.findViewById(R.id.ll_invoice), lstDetail, lstInf, mTotal, lstDetail.size());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void configFragment(View view) {
        ButterKnife.bind(this, view);
        setData();

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(getString(R.string.txt_61));

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        InvoiceFragmentI invoiceFragmentI = (InvoiceFragmentI) getActivity();
        invoiceFragmentI.hideAppBar();

        mInvoicePresenter = new InvoicePresenter(getActivity());
    }

    @Subscribe
    @Override
    public void printingFinished(String msg) {
        hideDialog(Config.PROGRESS_DIALOG_FRAGMENT);
        showSnackBar(msg);
    }

    public void setData() {
        InvoiceRecyclerAdapter invoiceRecyclerAdapter = new InvoiceRecyclerAdapter(lstDetail, mTotal);

        recyclerView.setAdapter(invoiceRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

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

    @Override
    public void onBackPressed() {
        removeCurrentFragment();
    }

    /**
     * Interface for hide the appbar in MainActivity and set the toolbar
     */
    public interface InvoiceFragmentI {

        void setToolbar();
        void hideAppBar();
    }
}
