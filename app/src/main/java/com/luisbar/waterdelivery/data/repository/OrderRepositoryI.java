package com.luisbar.waterdelivery.data.repository;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Interface that define all methods and listener from OrderRepository
 */
public interface OrderRepositoryI {

    void updateState(int id, String amount, String idrepa, Object object);
    void updateStateInRealm(final int id, String amount, String idrepa);
    void getDataForTheInvoice(int id, String identityCard, String amount, String clientName, JSONArray products, Object object);
    boolean invoiceHasNotBeenCreated(int id);
    void getInvoiceFromLocalDatabase(int id);
    void saveInRealDatabase(final JSONObject response);
    void setmRestAdapter(Object object);
    void getClientsAndProducts();
    void saveOrderInCloud(String clientId, String date, String observation, String products, Double total, Object object);
    void saveOrderInLocal(JSONObject response);
}
