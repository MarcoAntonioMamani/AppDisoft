package com.luisbar.waterdelivery.presentation.presenter;

import com.luisbar.waterdelivery.common.eventbus.DataForInvoiceFailedP;
import com.luisbar.waterdelivery.common.eventbus.DataForInvoiceSucceededP;
import com.luisbar.waterdelivery.common.eventbus.ClientsAndProductsSucceededP;
import com.luisbar.waterdelivery.common.eventbus.SaveOrderFailedP;
import com.luisbar.waterdelivery.common.eventbus.SaveOrderSucceededP;
import com.luisbar.waterdelivery.common.eventbus.UpdateStateFailedP;
import com.luisbar.waterdelivery.common.eventbus.UpdateStateSucceededP;

import java.util.List;

/**
 * Interface that define all methods and listener from OrderPresenter
 */
public interface OrderPresenterI {

    void updateState(int id, Double total, String amount, String idrepa, Object object);
    void getDataForTheInvoice(int id, String identityCard, String amount, String clientName, List products, Object object);
    void onStart();
    void onStop();
    void getClientsAndProducts();
    void saveOrder(String clientName, String date, String observation, List products, Double total, Object object);

    void updateStateSucceeded(UpdateStateSucceededP updateStateSucceededP);
    void updateStateFailed(UpdateStateFailedP updateStateFailedP);
    void getDataForTheInvoiceSucceeded(DataForInvoiceSucceededP dataForInvoiceSucceededP);
    void getDataForTheInvoiceFailed(DataForInvoiceFailedP dataForInvoiceFailedP);
    void clientsAndProductsSucceeded(ClientsAndProductsSucceededP clientsAndProductsSucceededP);
    void saveNewOrderSucceeded(SaveOrderSucceededP saveOrderSucceededP);
    void saveNewOrderFailed(SaveOrderFailedP saveOrderFailedP);
}
