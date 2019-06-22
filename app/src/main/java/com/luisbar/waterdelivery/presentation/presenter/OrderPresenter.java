package com.luisbar.waterdelivery.presentation.presenter;


import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.WaterDeliveryApplication;
import com.luisbar.waterdelivery.common.config.Config;
import com.luisbar.waterdelivery.common.eventbus.DataForInvoiceFailedP;
import com.luisbar.waterdelivery.common.eventbus.DataForInvoiceFailedV;
import com.luisbar.waterdelivery.common.eventbus.DataForInvoiceSucceededP;
import com.luisbar.waterdelivery.common.eventbus.DataForInvoiceSucceededV;
import com.luisbar.waterdelivery.common.eventbus.EventBusMask;
import com.luisbar.waterdelivery.common.eventbus.ClientsAndProductsSucceededP;
import com.luisbar.waterdelivery.common.eventbus.ClientsAndProductsSucceededV;
import com.luisbar.waterdelivery.common.eventbus.SaveOrderFailedP;
import com.luisbar.waterdelivery.common.eventbus.SaveOrderFailedV;
import com.luisbar.waterdelivery.common.eventbus.SaveOrderSucceededP;
import com.luisbar.waterdelivery.common.eventbus.SaveOrderSucceededV;
import com.luisbar.waterdelivery.common.eventbus.UpdateStateFailedP;
import com.luisbar.waterdelivery.common.eventbus.UpdateStateFailedV;
import com.luisbar.waterdelivery.common.eventbus.UpdateStateSucceededP;
import com.luisbar.waterdelivery.common.eventbus.UpdateStateSucceededV;
import com.luisbar.waterdelivery.domain.interactor.DataForTheInvoice;
import com.luisbar.waterdelivery.domain.interactor.ProductsAndClients;
import com.luisbar.waterdelivery.domain.interactor.SaveNewOrder;
import com.luisbar.waterdelivery.domain.interactor.UpdateState;
import com.luisbar.waterdelivery.domain.interactor.UseCase;
import com.luisbar.waterdelivery.presentation.model.InvoiceModel;
import com.luisbar.waterdelivery.presentation.model.OrderModel;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class OrderPresenter implements OrderPresenterI {

    private UseCase mUseCase;

    /**
     * Method for updating the state about request through id
     * @param id RequestHeader id
     * @param object RestAdapter
     */
    @Override
    public void updateState(int id, Double total, String amount, String idrepa, Object object) {
        if (Double.parseDouble(amount) <= total) {

            ////Aqui Laamar
            mUseCase = new UpdateState();
            InvoiceModel invoiceModel = new InvoiceModel(id, amount);
            invoiceModel.setIdrepa(idrepa);

            mUseCase.execute(invoiceModel, object);
        } else
            EventBusMask.post(new UpdateStateFailedV(WaterDeliveryApplication.resources.getString(R.string.txt_68)));
    }

    /**
     * It recovers the data about the invoice
     * @param identityCard Identity card or nit
     * @param amount Total amount
     * @param clientName Client name
     * @param products Products list
     */
    @Override
    public void getDataForTheInvoice(int id, String identityCard, String amount, String clientName, List products, Object object) {
        mUseCase = new DataForTheInvoice();
        InvoiceModel invoiceModel = new InvoiceModel(id, identityCard, amount, clientName, Config.CODE_REPARTIDOR, products);

        mUseCase.execute(invoiceModel, object);
    }

    /**
     * It subscribe the listeners
     */
    @Override
    public void onStart() {
        EventBusMask.subscribe(this);
    }

    /**
     * It unsubscribe the listeners
     */
    @Override
    public void onStop() {
        EventBusMask.unsubscribe(this);
    }

    /**
     * It gets the clients and products from local database
     */
    @Override
    public void getClientsAndProducts() {
        mUseCase = new ProductsAndClients();
        mUseCase.execute(null, null);
    }

    @Override
    public void saveOrder(String clientName, String date, String observation, List products, Double total, Object object) {
        if (clientName != null &&
                !clientName.isEmpty()
                && !clientName.equals("0")
                && !date.isEmpty()
                && products != null
                && !products.isEmpty()
                ) {

            mUseCase = new SaveNewOrder();
            OrderModel orderModel = new OrderModel(clientName, date, observation, products, total);

            mUseCase.execute(orderModel, object);
        } else
            EventBusMask.post(new SaveOrderFailedV(WaterDeliveryApplication.resources.getString(R.string.txt_82)));
    }

    /**
     * Listener that is triggered when the updateState method has been succeeded
     * @param updateStateSucceededP Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void updateStateSucceeded(UpdateStateSucceededP updateStateSucceededP) {
        EventBusMask.post(new UpdateStateSucceededV(updateStateSucceededP.getObject()));
    }

    /**
     * Listener that is triggered when the updateState method has been failed
     * @param updateStateFailedP Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void updateStateFailed(UpdateStateFailedP updateStateFailedP) {
        EventBusMask.post(new UpdateStateFailedV(updateStateFailedP.getObject()));
    }

    /**
     * Listener that is triggered when the data for the invoice has been recovered from the cloud
     * @param dataForInvoiceSucceededP Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void getDataForTheInvoiceSucceeded(DataForInvoiceSucceededP dataForInvoiceSucceededP) {
        EventBusMask.post(new DataForInvoiceSucceededV(prepareForInvoice((List<JSONArray>) dataForInvoiceSucceededP.getmObject())));
    }

    /**
     * Listener that is triggered when the data for the invoice has not been recovered from the cloud
     * @param dataForInvoiceFailedP Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void getDataForTheInvoiceFailed(DataForInvoiceFailedP dataForInvoiceFailedP) {
        EventBusMask.post(new DataForInvoiceFailedV(dataForInvoiceFailedP.getmObject()));
    }

    /**
     * It returns all products and clients to the order fragment
     * @param clientsAndProductsSucceededP Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void clientsAndProductsSucceeded(ClientsAndProductsSucceededP clientsAndProductsSucceededP) {
        EventBusMask.post(new ClientsAndProductsSucceededV(clientsAndProductsSucceededP.getObject()));
    }

    /**
     * It receives an advice when the new order has been saved successfully
     * @param saveOrderSucceededP Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void saveNewOrderSucceeded(SaveOrderSucceededP saveOrderSucceededP) {
        EventBusMask.post(new SaveOrderSucceededV(saveOrderSucceededP.getmObject()));
    }

    /**
     * It receives an advice when the new order has been saved successfully
     * @param saveOrderFailedP Pojo for recognizing the listener
     */
    @Subscribe
    @Override
    public void saveNewOrderFailed(SaveOrderFailedP saveOrderFailedP) {
        EventBusMask.post(new SaveOrderFailedV(saveOrderFailedP.getmObject()));
    }

    public List<String> prepareForInvoice(List<JSONArray> list) {
        String owner = null;
        String enterpriseName = null;
        String office = null;
        String address = null;
        String cellphone = null;
        String country = null;
        String sfc = null;
        String nit = null;
        String invoiceNumber = null;
        String autorizationNumber = null;
        String category = null;
        String date = null;
        String hour = null;
        String clientName = null;
        String clientNit = null;
        String codeControl = null;
        String limitDate = null;
        String qr = null;
        String noteOne = null;
        String noteTwo = null;

        try {
            JSONObject header = list.get(0).getJSONObject(0);
            JSONObject inf = list.get(1).getJSONObject(0);
            DateFormat dateFormatCloud = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat dateFormatLocal = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat hourFormat = new SimpleDateFormat("hh:mm");

            owner = inf.getString("scnom");
            enterpriseName = inf.getString("scneg");
            office = inf.getString("scsuc");
            address = inf.getString("scdir");
            cellphone = inf.getString("sctelf");
            country = inf.getString("scciu") +  " - " + inf.getString("scpai");
            sfc = inf.getString("scsfc");
            nit = inf.getString("scnit");
            invoiceNumber = header.getString("fvanfac");
            autorizationNumber = header.getString("fvaautoriz");
            category = inf.getString("scact");
            date = dateFormatLocal.format(dateFormatCloud.parse(header.getString("fvafec")));
            hour = hourFormat.format(dateFormatCloud.parse(header.getString("fvafec")));
            clientName = header.getString("fvadescli1");
            clientNit = header.getString("fvanitcli");
            codeControl = header.getString("fvaccont");
            limitDate = dateFormatLocal.format(dateFormatCloud.parse(header.getString("fvaflim")));
            qr = nit + "|" +
                    invoiceNumber + "|" +
                    autorizationNumber + "|" +
                    date + "|" +
                    header.getString("fvatotal") + "|" +
                    header.getString("fvatotal") + "|" +
                    codeControl + "|" +
                    clientNit + "|" +
                    "0|0|0|0";
            noteOne = inf.getString("scnoteone");
            noteTwo = inf.getString("scnotetwo");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Arrays.asList(
                enterpriseName,
                owner,
                office,
                address,
                cellphone,
                country,
                sfc,
                nit,
                invoiceNumber,
                autorizationNumber,
                category,
                date,
                hour,
                clientName,
                clientNit,
                codeControl,
                limitDate,
                qr,
                noteOne,
                noteTwo
        );
    }
}
