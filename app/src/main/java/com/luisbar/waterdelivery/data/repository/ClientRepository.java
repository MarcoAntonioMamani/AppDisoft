package com.luisbar.waterdelivery.data.repository;

import android.util.Log;

import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.WaterDeliveryApplication;
import com.luisbar.waterdelivery.common.eventbus.ClientAddedFailedP;
import com.luisbar.waterdelivery.common.eventbus.ClientAddedSucceededP;
import com.luisbar.waterdelivery.common.eventbus.EventBusMask;
import com.luisbar.waterdelivery.data.model.cloud.Client;
import com.luisbar.waterdelivery.data.model.local.Employee;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.remoting.adapters.Adapter;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

public class ClientRepository implements ClientRepositoryI {

    private RestAdapter mRestAdapter;

    /**
     * It saves the new client in the cloud
     * @param data JsonObject
     * @param object RestAdapter
     * @throws JSONException
     */
    @Override
    public void saveClientInCloud(JSONObject data, Object object) throws JSONException {
        setmRestAdapter(object);
        Client client = mRestAdapter.createRepository(Client.class);
        data.put("zone", getZone());

        client.saveClient(data.toString(), new Adapter.JsonObjectCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.e("onSuccess", response.toString());
                try {
                    if (response.get("result")  instanceof Boolean)
                        EventBusMask.post(new ClientAddedSucceededP(WaterDeliveryApplication.resources.getString(R.string.txt_81)));
                    else
                        EventBusMask.post(new ClientAddedFailedP(WaterDeliveryApplication.resources.getString(R.string.txt_76)));
                } catch (JSONException e) {
                    EventBusMask.post(new ClientAddedFailedP(WaterDeliveryApplication.resources.getString(R.string.txt_76)));
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.e("onError: ", t.getMessage());
                EventBusMask.post(new ClientAddedFailedP(WaterDeliveryApplication.resources.getString(R.string.txt_76)));
            }
        });
    }

    /**
     * It get the first zone
     */
    @Override
    public int getZone() {
        Realm realm = Realm.getDefaultInstance();

        int zone;
        Employee aux = realm.where(Employee.class).findFirst();
        zone = aux.getZones().get(0).getZone();

        realm.close();

        return zone;
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
}
