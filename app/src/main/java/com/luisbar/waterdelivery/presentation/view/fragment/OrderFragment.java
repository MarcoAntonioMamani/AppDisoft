package com.luisbar.waterdelivery.presentation.view.fragment;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.WaterDeliveryApplication;
import com.luisbar.waterdelivery.common.config.Config;
import com.luisbar.waterdelivery.common.eventbus.DataForInvoiceFailedV;
import com.luisbar.waterdelivery.common.eventbus.DataForInvoiceSucceededV;
import com.luisbar.waterdelivery.common.eventbus.EventBusMask;
import com.luisbar.waterdelivery.common.eventbus.ClientsAndProductsSucceededV;
import com.luisbar.waterdelivery.common.eventbus.SaveOrderFailedV;
import com.luisbar.waterdelivery.common.eventbus.SaveOrderSucceededV;
import com.luisbar.waterdelivery.common.eventbus.UpdateStateFailedV;
import com.luisbar.waterdelivery.common.eventbus.UpdateStateSucceededV;
import com.luisbar.waterdelivery.data.Utilitario.GlobalUtil;
import com.luisbar.waterdelivery.data.Utilitario.ListProduct;

import com.luisbar.waterdelivery.data.Utilitario.producto;
import com.luisbar.waterdelivery.presentation.presenter.OrderPresenter;
import com.luisbar.waterdelivery.presentation.view.adapter.ClientAutoCompleteAdapter;
import com.luisbar.waterdelivery.presentation.view.adapter.OrderRecyclerAdapter;
import com.luisbar.waterdelivery.presentation.view.dialog.CalendarDialogFragment;
import com.luisbar.waterdelivery.presentation.view.dialog.ConfirmationDialogFragment;
import com.luisbar.waterdelivery.presentation.view.dialog.OptionsOrderDialogFragment;
import com.luisbar.waterdelivery.presentation.view.dialog.ProductDialogFragment;
import com.luisbar.waterdelivery.presentation.view.dialog.ProgressDialogFragment;
import com.luisbar.waterdelivery.presentation.view.dialog.QuantityDialogFragment;
import com.luisbar.waterdelivery.presentation.view.listener.CustomOnBackPressed;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderFragment extends BaseFragment implements OrderFragmentI,
        ConfirmationDialogFragment.ConfirmationDialogFragmentI,
        CustomOnBackPressed, OrderRecyclerAdapter.OrderRecyclerAdapterNestedI,
        AdapterView.OnItemClickListener, CalendarDialogFragment.CalendarDialogFragmentI,
        FloatingActionsMenu.OnFloatingActionsMenuUpdateListener,
        ProductDialogFragment.ProductDialogFragmentI,
        OptionsOrderDialogFragment.OptionsOrderDialogFragmentI,
        QuantityDialogFragment.QuantityDialogFragmentI {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
    @BindView(R.id.et_order_payment_type)
    EditText etOrderPaymentType;
    @BindView(R.id.et_order_client_name)
    AutoCompleteTextView etOrderClientName;
    @BindView(R.id.et_order_date)
    EditText etOrderDate;
    @BindView(R.id.et_order_state)
    EditText etOrderState;
    @BindView(R.id.text_payment_kind)
    TextView textPaymentKind;
    @BindView(R.id.ll_payment_kind)
    LinearLayout llPaymentKind;
    @BindView(R.id.fragment_order)
    CoordinatorLayout fragmentOrder;
    @BindView(R.id.rb_cash)
    RadioButton rbCash;
    @BindView(R.id.rb_credit)
    RadioButton rbCredit;
    @BindView(R.id.fab_new_order)
    FloatingActionsMenu fabNewOrder;


    private List detail;
    private LatLng latLng;
    private OrderPresenter mOrderPresenter;
    private List lstProduct;
    // It can be delivered or new
    private int flag;
    private OrderRecyclerAdapter mOrderRecyclerAdapter;
    private String clientId;

    private final int NOT_DELIVERED = 0;
    private final int DELIVERED = 1;
    private final int NEW = 2;

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
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        ButterKnife.bind(this, view);
        Calendar fecha = new GregorianCalendar();
       etOrderDate.setText(fecha.get(Calendar.YEAR)+ "-" +
               (fecha.get(Calendar.MONTH)+1) + "-" +
               fecha.get(Calendar.DAY_OF_MONTH));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configFragment(view);
    }

    /**
     * It subscribe the listener
     */
    @Override
    public void onStart() {
        super.onStart();
        EventBusMask.subscribe(this);
        mOrderPresenter.onStart();
        if (flag != NEW) loadViewWithData();
        if (flag == NEW) loadClientsAndProducts();
    }

    /**
     * It unsubscribe the listener
     */
    @Override
    public void onStop() {
        super.onStop();
        EventBusMask.unsubscribe(this);
        mOrderPresenter.onStop();
    }

    /**
     * It trigger setToolBar event in MainActivity
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OrderFragmentI orderFragmentI = (OrderFragmentI) getActivity();
        orderFragmentI.setToolbar();
        hideDialog(Config.CONFIRMATION_DIALOG_FRAGMENT);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        switch (flag) {

            case NOT_DELIVERED:
                inflater.inflate(R.menu.order_menu, menu);
                break;

            case NEW:
                inflater.inflate(R.menu.new_order_menu, menu);
                break;
        }
    }

    /**
     * It hides the menu when the OrderFragment has been called from HomeRightFragment
     *
     * @param menu Menu object
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        switch (flag) {

            case DELIVERED:
                menu.setGroupVisible(R.id.group_order, false);
                break;

            case NEW:
                menu.setGroupVisible(R.id.group_order, false);
                menu.setGroupVisible(R.id.new_group_order, true);
                break;
        }
        menu.setGroupVisible(R.id.group_home, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.action_location:
                MyMapFragment myMapFragment = new MyMapFragment();
                Bundle args = new Bundle();

                args.putParcelable(Config.LAT_LNG, latLng);
                myMapFragment.setArguments(args);

                changeFragmentAndSaveTransition(R.id.fragment_order, myMapFragment, Config.MY_MAP_FRAGMENT);
                break;

            case R.id.action_check:
                showDialog(Config.CONFIRMATION_DIALOG_FRAGMENT, ConfirmationDialogFragment.newInstance(getString(R.string.txt_51), getString(R.string.txt_52), this));
                break;

            case R.id.action_printer:
                WaterDeliveryApplication application = (WaterDeliveryApplication) getActivity().getApplicationContext();
                getDataForTheInvoice(detail.get(8).toString(), detail.get(7).toString(),
                        etOrderClientName.getText().toString(), (List) detail.get(6), application.createRestAdpater());
                break;

            case R.id.action_new_order:
                WaterDeliveryApplication app = (WaterDeliveryApplication) getActivity().getApplicationContext();

                showDialog(Config.PROGRESS_DIALOG_FRAGMENT, ProgressDialogFragment.newInstance(getString(R.string.txt_47),
                        getString(R.string.txt_80)));
                mOrderPresenter.saveOrder(clientId,
                        etOrderDate.getText().toString(),
                        etOrderState.getText().toString(),
                        mOrderRecyclerAdapter != null ? mOrderRecyclerAdapter.getRequest() : null,
                        mOrderRecyclerAdapter != null ? mOrderRecyclerAdapter.getTotal(): 0,
                        app.createRestAdpater());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.rb_cash, R.id.rb_credit, R.id.et_order_date, R.id.fab_new_order})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rb_cash:
                etOrderPaymentType.setFocusableInTouchMode(false);
                etOrderPaymentType.setFocusable(false);
                etOrderPaymentType.getText().clear();
                break;

            case R.id.rb_credit:
                etOrderPaymentType.setFocusableInTouchMode(true);
                etOrderPaymentType.requestFocus();
                ///Marco
                etOrderPaymentType .setText(""+mOrderRecyclerAdapter.getTotal());
                break;

            case R.id.et_order_date:
                if (flag == NEW)
                    showDialog(Config.CALENDAR_DIALOG_FRAGMENT, CalendarDialogFragment.newInstance(getString(R.string.txt_70), this));
                break;
        }
    }

    /**
     * It initialize butterknife, set the toolbar and trigger the event hideAppBar in MainActivity
     *
     * @param view View for initializing butterknife
     */
    @Override
    public void configFragment(View view) {
        ButterKnife.bind(this, view);
        //
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(getString(R.string.txt_8));

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        OrderFragmentI orderFragmentI = (OrderFragmentI) activity;
        orderFragmentI.hideAppBar();

        mOrderPresenter = new OrderPresenter();
        //
        flag = getArguments().getInt(Config.FLAG_FOR_ORDER);

        if (flag == NEW) {
            etOrderClientName.setFocusableInTouchMode(true);
            etOrderState.setFocusableInTouchMode(true);
            llPaymentKind.setVisibility(View.GONE);
            textPaymentKind.setVisibility(View.GONE);
            fabNewOrder.setVisibility(View.VISIBLE);
            fabNewOrder.setOnFloatingActionsMenuUpdateListener(this);

        } else if (flag == DELIVERED) {
            llPaymentKind.setVisibility(View.GONE);
            textPaymentKind.setVisibility(View.GONE);

        }
    }

    /**
     * It load the view with data, the data has been sending by another fragment trough a bundle
     */
    @Override
    public void loadViewWithData() {
        Bundle args = getArguments();
        detail = (List) args.getSerializable(Config.DETAIL);

        etOrderClientName.setText(detail.get(1).toString());
        etOrderDate.setText(detail.get(2).toString());
        etOrderState.setText(detail.get(3).toString());
        latLng = new LatLng(Double.valueOf(detail.get(4).toString()), Double.valueOf(detail.get(5).toString()));

        mOrderRecyclerAdapter = new OrderRecyclerAdapter((List) detail.get(6), (Double) detail.get(7), flag, this);
        rvOrder.setAdapter(mOrderRecyclerAdapter);
        rvOrder.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    /**
     * It request the clients and products from local database
     */
    @Override
    public void loadClientsAndProducts() {
        mOrderPresenter.getClientsAndProducts();
    }

    /**
     * Listener that is triggered when the state about the current request has been changed
     */
    @Subscribe
    @Override
    public void updateStateSucceeded(UpdateStateSucceededV updateStateSucceededV) {
        hideDialog(Config.PROGRESS_DIALOG_FRAGMENT);
        removeCurrentFragment();
        showSnackBar(updateStateSucceededV.getObject().toString());
    }

    /**
     * Listener that is triggered when the state about the current request has not been changed
     */
    @Subscribe
    @Override
    public void updateStateFailed(UpdateStateFailedV updateStateFailedV) {
        hideDialog(Config.PROGRESS_DIALOG_FRAGMENT);
        showSnackBar(updateStateFailedV.getObject().toString());
    }

    /**
     * Listener that is triggered when the data for the invoice has been recovered from the cloud
     *
     * @param dataForInvoiceSucceededV Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void getDataForTheInvoiceSucceeded(DataForInvoiceSucceededV dataForInvoiceSucceededV) {
        InvoiceFragment invoiceFragment = new InvoiceFragment();
        Bundle args = new Bundle();
        List<String> inf = (List<String>) dataForInvoiceSucceededV.getmObject();

        args.putSerializable(Config.DETAIL, (Serializable) detail.get(6));
        args.putSerializable(Config.LIST, (Serializable) inf);
        args.putDouble(Config.TOTAL, (Double) detail.get(7));

        invoiceFragment.setArguments(args);

        hideDialog(Config.PROGRESS_DIALOG_FRAGMENT);
        changeFragmentAndSaveTransition(R.id.fragment_container, invoiceFragment, Config.INVOICE_FRAGMENT);
    }

    /**
     * Listener that is triggered when the data for the invoice has not been recovered from the cloud
     * @param dataForInvoiceFailedV Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void getDataForTheInvoiceFailed(DataForInvoiceFailedV dataForInvoiceFailedV) {
        hideDialog(Config.PROGRESS_DIALOG_FRAGMENT);
        showSnackBar(getString(R.string.txt_65));
    }

    /**
     * It catches the clients and products that was sending by the order presenter
     * @param clientsAndProductsSucceededV Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void productsAndClientsSucceeded(ClientsAndProductsSucceededV clientsAndProductsSucceededV) {
        List clients = (List) ((List) clientsAndProductsSucceededV.getObject()).get(0);
  String CodeRepartidor=   GlobalUtil.CodeRepartidor;
        lstProduct = (List) ((List) clientsAndProductsSucceededV.getObject()).get(1);

        ArrayAdapter clientAdapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                new ClientAutoCompleteAdapter(clients));

        etOrderClientName.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
        etOrderClientName.setAdapter(clientAdapter);
        etOrderClientName.setOnItemClickListener(this);
    }

    /**
     * Listener that is triggered when the new order has been saved successfully
     * @param saveOrderSucceededV Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void saveNewOrderSucceeded(SaveOrderSucceededV saveOrderSucceededV) {
        hideDialog(Config.PROGRESS_DIALOG_FRAGMENT);
        showSnackBar(saveOrderSucceededV.getmObject().toString());

        clientId = null;
        etOrderClientName.getText().clear();
        etOrderDate.getText().clear();
        etOrderState.getText().clear();
        //Marco
        Calendar fecha = new GregorianCalendar();
        etOrderDate.setText(fecha.get(Calendar.YEAR)+ "-" +
                (fecha.get(Calendar.MONTH)+1) + "-" +
                fecha.get(Calendar.DAY_OF_MONTH));
        mOrderRecyclerAdapter.removeAll();
    }

    /**
     * * Listener that is triggered when the new order has been not saved successfully
     * @param saveOrderFailedV Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void saveNewOrderFailed(SaveOrderFailedV saveOrderFailedV) {
        hideDialog(Config.PROGRESS_DIALOG_FRAGMENT);
        showSnackBar(saveOrderFailedV.getmObject().toString());
    }

    /**
     * Listener for CalendarDialogFragment accept button
     * @param date Date selected for the order
     */
    @Override
    public void accept(String date) {
        etOrderDate.setText(date);
    }

    /**
     * Listener for CalendarDialogFragment cancel button
     */
    @Override
    public void cancelCalendar() {
        hideDialog(Config.CALENDAR_DIALOG_FRAGMENT);
    }

    /**
     * Listener for ProductDialogFragment accept button
     * @param code Id about the product selected
     * @param name Name about the product selected
     * @param price Price about the product selected
     * @param quantity Quantity about the product selected
     */
    @Override
    public void accept(int code, String name, Double price, Double quantity) {
        if (name != null && price != null && quantity != 0) {

            Double total = (Double) (price * quantity);
            List request = Arrays.asList(name, quantity, price, total, code);

            if (mOrderRecyclerAdapter == null) {
                ArrayList list = new ArrayList();
                list.add(request);
                this.mOrderRecyclerAdapter = new OrderRecyclerAdapter(list, total, flag, this);
            } else
                this.mOrderRecyclerAdapter.addElementAndUpdateTotal(name, quantity, price, total, code);

            rvOrder.setAdapter(mOrderRecyclerAdapter);
            rvOrder.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            fabNewOrder.collapse();
            hideDialog(Config.PRODUCT_DIALOG_FRAGMENT);
        } else
            showSnackBar(getString(R.string.txt_72));
    }

    /**
     * Listener for ProductDialogFragment cancel button
     */
    @Override
    public void cancelProduct() {
        hideDialog(Config.PRODUCT_DIALOG_FRAGMENT);
        fabNewOrder.collapse();
    }

    /**
     * Listener for ConfirmationDialogFragment accept button
     */
    @Override
    public void accept() {
        //////Aqui hace el Accept
       hideDialog(Config.CONFIRMATION_DIALOG_FRAGMENT);
        showDialog(Config.PROGRESS_DIALOG_FRAGMENT, ProgressDialogFragment.newInstance(getString(R.string.txt_47), getString(R.string.txt_53)));
      String amount = etOrderPaymentType.getText().toString().isEmpty() ? "0" : etOrderPaymentType.getText().toString();
        String idrepa = Config.CODE_REPARTIDOR;
        ValidarPedidos(Integer.valueOf(detail.get(0).toString()), (Double) detail.get(7), amount, idrepa);
      //
    }

    public void ValidarPedidos(int id, Double total, String amount, String idrepa){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        String Ip= settings.getString("IP", null);
        Ip=ExtraerIp(Ip)+":3050";
        ModificarPedido(Ip,id,amount);

    }


    /**
     * Listener for ConfirmationDialogFragment cancel button
     */
    @Override
    public void cancel() {
        hideDialog(Config.CONFIRMATION_DIALOG_FRAGMENT);
    }

    /**
     * It recovers the data about the invoice
     *
     * @param identityCard Identity card or nit
     * @param amount       Total amount
     * @param clientName   Client name
     * @param products     Products list
     * @param object       RestAdapter object
     */
    @Override
    public void getDataForTheInvoice(String identityCard, String amount, String clientName, List products, Object object) {
        showDialog(Config.PROGRESS_DIALOG_FRAGMENT, ProgressDialogFragment.newInstance(getString(R.string.txt_47), getString(R.string.txt_62)));
        mOrderPresenter.getDataForTheInvoice(Integer.valueOf(detail.get(0).toString()), identityCard, amount, clientName,
                products, object);
    }

    @Override
    public void onBackPressed() {
        removeCurrentFragment();
    }

    /**
     * Listener triggered when dots menu has been pressed
     */
    @Override
    public void showDialogInNotDelivered() {
        Log.e("showDialogInNotDelivered: ", "si");
    }

    /**
     * Listener triggered when dots menu has been pressed
     */
    @Override
    public void showDialogInNew() {
        showDialog(Config.OPTIONS_ORDER_DIALOG_FRAGMENT,
                OptionsOrderDialogFragment.newInstance(getString(R.string.txt_73), this));
    }

    /**
     * It listens the events about autocomplete text view
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        clientId = ((ClientAutoCompleteAdapter.Client) parent.getAdapter().getItem(position)).getId()+"";
        GlobalUtil.idcliente=clientId;

    }

    /**
     * Listener for fab button for new order
     */


    public void ModificarPedido(String Ip,final int id,final String credito)  {
        //    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

        AsyncHttpClient client=new AsyncHttpClient();
        //+"&contrasena="+passw+""
        GlobalUtil.IdClientSelect=GlobalUtil.idcliente  ;
        LocationManager locManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }else{
            Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                GlobalUtil.latitud=String.valueOf(loc.getLatitude());
                GlobalUtil.longitud=String.valueOf(loc.getLongitude());
            }else{
                GlobalUtil.latitud="0";
                GlobalUtil.longitud="0";
            }
        }



        client.get(Ip+"/api/orders/"+id+"/"+credito+"/"+GlobalUtil.latitud+"/"+GlobalUtil.longitud, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    try{



                        JSONObject jsonarray=new JSONObject(new String(responseBody));
                        if (jsonarray==null){
                            Snackbar snackbar= Snackbar.make(etOrderPaymentType, "Email o Password no validos", Snackbar.LENGTH_LONG);
                            View snackbar_view=snackbar.getView();
                            TextView snackbar_text=(TextView)snackbar_view.findViewById(android.support.design.R.id.snackbar_text);
                            snackbar_text.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_check,0);
                            snackbar_text.setGravity(Gravity.CENTER);
                            snackbar.show();
                            ;
                            return;
                        }else{

                            String json1 = "" + jsonarray;
                            Gson gson = new Gson();
                            WaterDeliveryApplication application = (WaterDeliveryApplication) getActivity().getApplicationContext();

                            mOrderPresenter.updateState(id, (Double) detail.get(7), credito, "0", application.createRestAdpater());



                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }


    public void verificarDatos(String Ip)  {
                //    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

        AsyncHttpClient client=new AsyncHttpClient();
        //+"&contrasena="+passw+""
        GlobalUtil.IdClientSelect=GlobalUtil.idcliente  ;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());

        String repartidor= settings.getString("CODE", null);

        client.get(Ip+"/api/products/category/"+GlobalUtil.idcliente+"/"+repartidor, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    try{



                        JSONArray jsonarray=new JSONArray(new String(responseBody));
                        if (jsonarray.isNull(0)){
                            Snackbar snackbar= Snackbar.make(etOrderPaymentType, "Email o Password no validos", Snackbar.LENGTH_LONG);
                            View snackbar_view=snackbar.getView();
                            TextView snackbar_text=(TextView)snackbar_view.findViewById(android.support.design.R.id.snackbar_text);
                            snackbar_text.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_check,0);
                            snackbar_text.setGravity(Gravity.CENTER);
                            snackbar.show();
                            ;
                            return;
                        }else{
                            List listp = new ArrayList();
                            GlobalUtil.listProducto=new ArrayList<producto>();
                            for (int i=0;i<jsonarray.length();i++) {
                                String json1 = "" + jsonarray.getJSONObject(i).toString();
                                Gson gson = new Gson();
                                try {
                                    producto p =gson.fromJson(json1, producto.class);
                                    listp.add(Arrays.asList(p.getId(),
                                            p.getName().toLowerCase(),
                                            p.getPrice()));
                                    GlobalUtil.listProducto.add(p);


                                } catch (Exception e) {

                                }
                            }
                            lstProduct=listp;
                            GlobalUtil.listProducts=listp;
                            SendActivity();
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }

    public String ExtraerIp(String ip){

        int cont=0;
        String Cadena="";
        int i=0;
        while (cont<2 && i< ip.length()){
            char c=ip.charAt(i);
            if(c ==':'){
                cont++;
                if(cont!=2){
                    Cadena=Cadena+c;
                }
            }else{
                Cadena=Cadena+c;
            }
            i++;
        }
        return Cadena;
    }
    @Override
    public void onMenuExpanded() {


        ////////////Aquiu Se debe cargar la lista de productos
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());

        String Ip= settings.getString("IP", null);
        Ip=ExtraerIp(Ip)+":3050";

        if(GlobalUtil.IdClientSelect.trim().equals(GlobalUtil.idcliente.trim())){
            lstProduct=GlobalUtil.listProducts;
            SendActivity();
        }else{
            verificarDatos(Ip);
        }


    }
public void SendActivity(){

    ProductDialogFragment productDialogFragment = ProductDialogFragment.newInstance(getString(R.string.txt_71), this, lstProduct);

    showDialog(Config.PRODUCT_DIALOG_FRAGMENT, productDialogFragment);
}
    /**
     * Listener for fab button for new order
     */
    @Override
    public void onMenuCollapsed() {

    }

    /**
     * Listener when the user press an option from Dialog for new order
     * @param position
     */
    @Override
    public void onItemClickOptionsOrder(int position) {
        switch (position) {
            case 0:
                showDialog(Config.QUANTITY_DIALOG_FRAGMENT,
                        QuantityDialogFragment.newInstance(getString(R.string.txt_74), this));
                break;

            case 1:
                mOrderRecyclerAdapter.removeItemSelected();
                break;
        }
    }

    /**
     * Listener for QuantityDialogFragment accept button
     */
    @Override
    public void accept(int quantity) {
        mOrderRecyclerAdapter.updateQuantity(quantity);
        hideDialog(Config.QUANTITY_DIALOG_FRAGMENT);
    }

    /**
     * Interface for hide the appbar in MainActivity and set the toolbar
     */
    public interface OrderFragmentI {

        void setToolbar();
        void hideAppBar();
    }
}
