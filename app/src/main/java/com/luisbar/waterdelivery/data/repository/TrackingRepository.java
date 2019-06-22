package com.luisbar.waterdelivery.data.repository;

import android.util.Log;

import com.luisbar.waterdelivery.data.model.cloud.Tracking;
import com.luisbar.waterdelivery.data.model.local.Employee;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.remoting.adapters.Adapter;

import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmQuery;

public class TrackingRepository implements TrackingRepositoryI {

    private RestAdapter mRestAdapter;

    @Override
    public void saveLocation(String lat, String lng, Object object) {
        setmRestAdapter(object);
        Tracking tracking = mRestAdapter.createRepository(Tracking.class);
        int employeeId = getIdFromEmployee();

        if (employeeId != 0) {
            tracking.saveLocation(employeeId, lat, lng, new Adapter.JsonObjectCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    Log.d("onSuccess: ", response.toString());
                }

                @Override
                public void onError(Throwable t) {
                    Log.d("onError: ", t.getMessage());
                }
            });
        }
    }

    /**
     * It set the RestAdapter
     * @param object RestAdapter
     */
    @Override
    public void setmRestAdapter(Object object) {
        if (mRestAdapter == null)
            mRestAdapter = (RestAdapter) object;
    }

    /**
     * Get the current user id
     */
    @Override
    public int getIdFromEmployee() {

        Realm realm = Realm.getDefaultInstance();

        RealmQuery<Employee> query;
        query = realm.where(com.luisbar.waterdelivery.data.model.local.Employee.class);

        int id = query.findFirst() == null ? 0 : query.findFirst().getCbnumi();
        realm.close();

        return id;
    }
}
