package com.luisbar.waterdelivery.data.repository;

import com.luisbar.waterdelivery.data.model.local.Employee;
import com.luisbar.waterdelivery.data.model.local.RequestHeader;

import org.json.JSONArray;

import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Interface that define all methods and listener from HomeLeftRepository
 */
public interface HomeLeftRepositoryI {

    void logOut();
    void getAllData(Object object);

    void getRequestHeader(int id);
    void insertRequestHeader(final JSONArray response, final List ids);
    void deleteRequestHeader();

    void getRequestDetail(List idsRequestHeader);
    void insertRequestDetail(final JSONArray requestDetails);
    void saveDetailsInHeader(Realm realm);
    void deleteRequestDetail();

    void getProducts();
    void insertProducts(final JSONArray products);
    void saveProductInDetail(Realm realm);
    void deleteProduct();

    void getClient(List zones);
    void insertClient(final JSONArray clients);
    void saveClientInHeader(Realm realm);
    void deleteClient();

    void updateUserState(final Realm realm);
    void getAllForRecyclerView(final Realm realm);
    List getCreditAndCashAmount(Realm realm);
    RealmResults<RequestHeader> getRequestHedersByState(Realm realm, int state);
    List loadListHeader(com.luisbar.waterdelivery.data.model.local.RequestHeader requestHeader, List details);
    List loadListDetail(com.luisbar.waterdelivery.data.model.local.RequestHeader requestHeader, List products, Double total);
    List loadListProduct(com.luisbar.waterdelivery.data.model.local.RequestDetail requestDetail);
    List getListWithAllData(Iterator<RequestHeader> iterator);
    Employee getUser();
    void setmRestAdapter(Object object);
}
