package com.luisbar.waterdelivery.data.repository;

import com.luisbar.waterdelivery.data.model.local.Employee;
import com.luisbar.waterdelivery.data.model.local.RequestHeader;

import org.json.JSONArray;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Interface that define all methods and listener from FcmRepository
 */
public interface FcmRepositoryI {

    void saveFcmId(String id, Object object);
    void saveNewRequest(JSONArray json, int id);
    void getAllForRecyclerView(final Realm realm);
    RealmResults<RequestHeader> getRequestHedersByState(Realm realm, int state);
    void updateStateAtCloud(int id);
    void setmRestAdapter(Object object);
    Employee getUser();
}
