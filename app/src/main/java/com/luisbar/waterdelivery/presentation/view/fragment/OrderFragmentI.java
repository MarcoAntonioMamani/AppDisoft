package com.luisbar.waterdelivery.presentation.view.fragment;

import android.view.View;

import com.luisbar.waterdelivery.common.eventbus.DataForInvoiceFailedV;
import com.luisbar.waterdelivery.common.eventbus.DataForInvoiceSucceededV;
import com.luisbar.waterdelivery.common.eventbus.ClientsAndProductsSucceededV;
import com.luisbar.waterdelivery.common.eventbus.SaveOrderFailedV;
import com.luisbar.waterdelivery.common.eventbus.SaveOrderSucceededV;
import com.luisbar.waterdelivery.common.eventbus.UpdateStateFailedV;
import com.luisbar.waterdelivery.common.eventbus.UpdateStateSucceededV;
import com.luisbar.waterdelivery.presentation.view.pdf.InvoicePdf;

import java.util.List;

/**
 * Interface that define all methods and listener from OrderFragment
 */
public interface OrderFragmentI {

    void configFragment(View view);
    void loadViewWithData();
    void loadClientsAndProducts();
    void getDataForTheInvoice(String identityCard, String amount, String clientName, List products, Object object);

    void updateStateSucceeded(UpdateStateSucceededV stateSucceededV);
    void updateStateFailed(UpdateStateFailedV updateStateFailedV);
    void getDataForTheInvoiceSucceeded(DataForInvoiceSucceededV dataForInvoiceSucceededV);
    void getDataForTheInvoiceFailed(DataForInvoiceFailedV dataForInvoiceFailedV);
    void productsAndClientsSucceeded(ClientsAndProductsSucceededV clientsAndProductsSucceededV);
    void saveNewOrderSucceeded(SaveOrderSucceededV saveOrderSucceededV);
    void saveNewOrderFailed(SaveOrderFailedV saveOrderFailedV);
}
