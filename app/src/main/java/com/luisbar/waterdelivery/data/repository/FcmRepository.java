package com.luisbar.waterdelivery.data.repository;

import android.util.Log;

import com.google.common.collect.ImmutableMap;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.luisbar.waterdelivery.common.eventbus.EventBusMask;
import com.luisbar.waterdelivery.common.eventbus.RequestLeftP;
import com.luisbar.waterdelivery.data.model.cloud.Fcm;
import com.luisbar.waterdelivery.data.model.local.Employee;
import com.luisbar.waterdelivery.data.model.local.RequestHeader;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.VoidCallback;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class FcmRepository implements FcmRepositoryI {

    private RestAdapter mRestAdapter;
    private HomeLeftRepository mHomeLeftRepository;

    /**
     * It save the fcm id at the cloud
     * @param id FcmId
     * @param object RestAdapter
     */
    @Override
    public void saveFcmId(String id, Object object) {
        setmRestAdapter(object);
        Fcm fcm = mRestAdapter.createRepository(Fcm.class);

        Fcm.FcmNested fcmNested = fcm.createObject(ImmutableMap.of("cknumi", getUser().getCbnumi()));
        fcmNested.setCkidfsm(id);

        fcmNested.save(new VoidCallback() {
            @Override
            public void onSuccess() {
                Log.e("onSuccess in FcmRepository", "success");
            }

            @Override
            public void onError(Throwable t) {
                Log.e("onError in FcmRepository", t.getMessage());
            }
        });
    }

    /**
     * It save the new request done at local database
     * @param json RequestHeader object in JsonArray
     * @param id RequestHeader id
     */
    @Override
    public void saveNewRequest(final JSONArray json, final int id) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateAllFromJson(RequestHeader.class, json);
                RequestHeader requestHeader = realm.where(RequestHeader.class)
                                                    .equalTo("oanumi", id)
                                                    .findFirst();

                requestHeader.setOaest(2);
                getAllForRecyclerView(realm);
            }
        });

        realm.close();
        updateStateAtCloud(id);
    }

    /**
     * It update the recycler view for showing the new request, it method is synchronous
     * @param realm Realm object
     */
    public void getAllForRecyclerView(final Realm realm) {
        mHomeLeftRepository = new HomeLeftRepository();
        final RealmResults<RequestHeader> requestHeadersTwo = getRequestHedersByState(realm, 2);

        Iterator<RequestHeader> iterator = requestHeadersTwo.iterator();
        List list = mHomeLeftRepository.getListWithAllData(iterator);

        EventBusMask.post(new RequestLeftP(list));
    }

    /**
     * It gets the requestheader by a specific state
     * @param realm Realm object
     * @param state State
     * @return RealmResults<RequestHeader>
     */
    public RealmResults<com.luisbar.waterdelivery.data.model.local.RequestHeader> getRequestHedersByState(Realm realm, int state) {
        RealmResults<com.luisbar.waterdelivery.data.model.local.RequestHeader> requestHeaders;
        requestHeaders = realm.where(com.luisbar.waterdelivery.data.model.local.RequestHeader.class)
                .equalTo("oaest", state)
                .findAll();

        return  requestHeaders;
    }

    /**
     * It update the state about the new request at the cloud
     * @param id RequestHeader id
     */
    public void updateStateAtCloud(int id) {
        SyncHttpClient mSyncHttpClient = new SyncHttpClient();

        RequestParams requestParams = new RequestParams();
        requestParams.add("id", id+"");

        mSyncHttpClient.post("https://water-delivery.herokuapp.com/api/To001s/updateStateOneToTwo", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
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
     * It get the Employee object
     * @return Employee object
     */
    @Override
    public Employee getUser() {
        Realm realm = Realm.getDefaultInstance();

        Employee employee= new Employee();
        Employee aux = realm.where(Employee.class).findFirst();

        employee.setCbnumi(aux.getCbnumi());
        employee.setCopied(aux.isCopied());
        employee.setCbdesc(aux.getCbdesc());

        realm.close();

        return employee;
    }
}
