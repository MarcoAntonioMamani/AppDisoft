package com.luisbar.waterdelivery.data.repository;

import com.luisbar.waterdelivery.data.model.local.Employee;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Interface that define all methods and listener from ClientRepository
 */
public interface ClientRepositoryI {

    void saveClientInCloud(JSONObject data, Object object) throws JSONException;
    void setmRestAdapter(Object object);
    int getZone();
}
