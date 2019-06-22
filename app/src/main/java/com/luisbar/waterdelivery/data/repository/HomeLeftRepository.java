package com.luisbar.waterdelivery.data.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.WaterDeliveryApplication;
import com.luisbar.waterdelivery.common.eventbus.AllDataFailedLeftP;
import com.luisbar.waterdelivery.common.eventbus.EventBusMask;
import com.luisbar.waterdelivery.common.eventbus.LogOutFailedP;
import com.luisbar.waterdelivery.common.eventbus.LogOutSucceededP;
import com.luisbar.waterdelivery.common.eventbus.RequestLeftP;
import com.luisbar.waterdelivery.common.eventbus.RequestRightP;
import com.luisbar.waterdelivery.data.model.cloud.Client;
import com.luisbar.waterdelivery.data.model.cloud.Product;
import com.luisbar.waterdelivery.data.model.cloud.RequestDetail;
import com.luisbar.waterdelivery.data.model.cloud.VrMoGetpedidos;
import com.luisbar.waterdelivery.data.model.local.CreditPayment;
import com.luisbar.waterdelivery.data.model.local.CreditPaymentInt;
import com.luisbar.waterdelivery.data.model.local.Employee;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.remoting.adapters.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class HomeLeftRepository implements HomeLeftRepositoryI {

    private RestAdapter mRestAdapter;

    /**
     * Method for logging out, it delete the only Employee object from realm database
     * and send an advice to the MainActivity if it has failed or succeeded
     */
    @Override
    public void logOut() {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        },
        new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                EventBusMask.post(new LogOutSucceededP());
                realm.close();
            }
        },
        new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                EventBusMask.post(new LogOutFailedP());
                realm.close();
            }
        });
    }

    /**
     * It save all the information about user logged from the cloud in realm database
     * if it has not been saved
     * @param object
     */
    @Override
    public void getAllData(Object object) {
        if (!getUser().isCopied()) {
            setmRestAdapter(object);
            getRequestHeader(getUser().getCbnumi());
        } else
            getAllForRecyclerView(Realm.getDefaultInstance());
    }

    /**
     * Method for getting from the cloud the request headers by employee id
     * @param id Employee id
     */
    @Override
    public void getRequestHeader(int id) {
        VrMoGetpedidos vrMoGetpedidos = mRestAdapter.createRepository(VrMoGetpedidos.class);

        vrMoGetpedidos.getRequestHeaderByEmployeId(id, new Adapter.JsonObjectCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONObject result = response.getJSONObject("result");
                    JSONArray requestHeaders = result.getJSONArray("modelInstance");
                    JSONArray requestHeadersIds = result.getJSONArray("ids");

                    Gson gson = new Gson();
                    Type type = new TypeToken<List>(){}.getType();
                    List ids = gson.fromJson(String.valueOf(requestHeadersIds), type);

                    insertRequestHeader(requestHeaders, ids);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {
                EventBusMask.post(new AllDataFailedLeftP(WaterDeliveryApplication.resources.getString(R.string.txt_37)));
            }
        });
    }

    /**
     * Method for saving the request headers that has been gotten from the cloud
     * @param response JsonArray for saving with realm
     * @param ids Request headers ids for requesting the details about them
     */
    @Override
    public void insertRequestHeader(final JSONArray response, final List ids) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createAllFromJson(com.luisbar.waterdelivery.data.model.local.RequestHeader.class
                        , response);
            }
        },
        new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                getRequestDetail(ids);
                realm.close();
            }
        },
        new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                EventBusMask.post(new AllDataFailedLeftP(WaterDeliveryApplication.resources.getString(R.string.txt_38)));
                realm.close();
            }
        });
    }

    /**
     * It delete the data about RequestHeader
     */
    public void deleteRequestHeader() {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
              realm.where(com.luisbar.waterdelivery.data.model.local.RequestHeader.class)
                      .findAll()
                      .deleteAllFromRealm();
            }
        },
        new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                realm.close();
            }
        },
        new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                realm.close();
            }
        });
    }

    /**
     * Method for getting from the cloud the request details by request id
     * @param idsRequestHeader RequestHeaders ids for requesting the details about them
     */
    @Override
    public void getRequestDetail(List idsRequestHeader) {
        RequestDetail requestDetail = mRestAdapter.createRepository(RequestDetail.class);

        requestDetail.getDetailRequestByIds(idsRequestHeader, new Adapter.JsonObjectCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    insertRequestDetail(response.getJSONArray("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {
                EventBusMask.post(new AllDataFailedLeftP(WaterDeliveryApplication.resources.getString(R.string.txt_39)));
                deleteRequestHeader();
            }
        });
    }

    /**
     * Method for saving the request detail that has been gotten from the cloud
     * @param requestDetails JsonArray for saving the request details with realm
     */
    @Override
    public void insertRequestDetail(final JSONArray requestDetails) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createAllFromJson(com.luisbar.waterdelivery.data.model.local.RequestDetail.class
                      , requestDetails);

                saveDetailsInHeader(realm);
            }
        },
        new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                getProducts();
                realm.close();
            }
        },
        new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                EventBusMask.post(new AllDataFailedLeftP(WaterDeliveryApplication.resources.getString(R.string.txt_40)));
                deleteRequestHeader();
                realm.close();
            }
        });
    }

    /**
     * It link each detail with its header
     * @param realm Realm instance
     */
    @Override
    public void saveDetailsInHeader(Realm realm) {
        RealmResults<com.luisbar.waterdelivery.data.model.local.RequestHeader> requestHeaders;
        requestHeaders = realm.where(com.luisbar.waterdelivery.data.model.local.RequestHeader.class)
                .findAll();

        for (com.luisbar.waterdelivery.data.model.local.RequestHeader requestHeader : requestHeaders) {
            RealmResults<com.luisbar.waterdelivery.data.model.local.RequestDetail> results;
            RealmList<com.luisbar.waterdelivery.data.model.local.RequestDetail> realmList = null;
            realmList = new RealmList<com.luisbar.waterdelivery.data.model.local.RequestDetail>();

            results = realm.where(com.luisbar.waterdelivery.data.model.local.RequestDetail.class)
                    .equalTo("obnumi", requestHeader.getOanumi())
                    .findAll();

            for (com.luisbar.waterdelivery.data.model.local.RequestDetail requestDetail : results) {

                realmList.add(requestDetail);
            }

            requestHeader.setDetails(realmList);
        }
    }

    /**
     * It delete the data about RequestDetail
     */
    @Override
    public void deleteRequestDetail() {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
              realm.where(com.luisbar.waterdelivery.data.model.local.RequestDetail.class)
                      .findAll()
                      .deleteAllFromRealm();
            }
        },
        new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                realm.close();
            }
        },
        new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                realm.close();
            }
        });
    }

    /**
     * Method for getting from the cloud the products
     */
    @Override
    public void getProducts() {
        Product product = mRestAdapter.createRepository(Product.class);

        product.findAndMatchPrice(new Adapter.JsonObjectCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    insertProducts(response.getJSONArray("result"));
                } catch (JSONException e) {
                    EventBusMask.post(new AllDataFailedLeftP(WaterDeliveryApplication.resources.getString(R.string.txt_41)));
                }
            }

            @Override
            public void onError(Throwable t) {
                EventBusMask.post(new AllDataFailedLeftP(WaterDeliveryApplication.resources.getString(R.string.txt_41)));
                deleteRequestHeader();
                deleteRequestDetail();
            }
        });
    }

    /**
     * It save the products in realm database
     * @param products JsonArray about products for saving with realm
     */
    @Override
    public void insertProducts(final JSONArray products) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createAllFromJson(com.luisbar.waterdelivery.data.model.local.Product.class,
                        products);

                saveProductInDetail(realm);
            }
        },
        new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                RealmQuery<Employee> query = realm.where(com.luisbar.waterdelivery.data.model.local.Employee.class);
                Employee employee = query.findFirst();

                getClient(employee.getZones());
                realm.close();
            }
        },
        new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                EventBusMask.post(new AllDataFailedLeftP(WaterDeliveryApplication.resources.getString(R.string.txt_42)));
                deleteRequestHeader();
                deleteRequestDetail();
                realm.close();
            }
        });
    }

    /**
     * It link each Product with its Detail
     * @param realm Realm instance
     */
    @Override
    public void saveProductInDetail(Realm realm) {
        RealmResults<com.luisbar.waterdelivery.data.model.local.RequestDetail> requestDetails;
        requestDetails = realm.where(com.luisbar.waterdelivery.data.model.local.RequestDetail.class)
                .findAll();

        for (com.luisbar.waterdelivery.data.model.local.RequestDetail requestDetail : requestDetails) {
            com.luisbar.waterdelivery.data.model.local.Product product = realm
                    .where(com.luisbar.waterdelivery.data.model.local.Product.class)
                    .equalTo("canumi", Integer.valueOf(requestDetail.getObcprod()))
                    .findFirst();

            requestDetail.setProduct(product);
        }
    }

    /**
     * It delete the data about Product
     */
    @Override
    public void deleteProduct() {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
              realm.where(com.luisbar.waterdelivery.data.model.local.Product.class)
                      .findAll()
                      .deleteAllFromRealm();
            }
        },
        new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                realm.close();
            }
        },
        new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                realm.close();
            }
        });
    }

    /**
     * Method for getting from the cloud the clients by their zone
     * @param zones Zones ids
     */
    @Override
    public void getClient(List zones) {
        Client client = mRestAdapter.createRepository(Client.class);

        client.getClientsByZones(zones, new Adapter.JsonObjectCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    insertClient(response.getJSONArray("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {
                EventBusMask.post(new AllDataFailedLeftP(WaterDeliveryApplication.resources.getString(R.string.txt_43)));
                deleteRequestHeader();
                deleteRequestDetail();
                deleteProduct();
            }
        });
    }

    /**
     * It save the clients in real database
     * @param clients JsonArray for saving the clients with realm
     */
    @Override
    public void insertClient(final JSONArray clients) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createAllFromJson(com.luisbar.waterdelivery.data.model.local.Client.class,
                        clients);

                saveClientInHeader(realm);
            }
        },
        new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                updateUserState(realm);
            }
        },
        new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                EventBusMask.post(new AllDataFailedLeftP(WaterDeliveryApplication.resources.getString(R.string.txt_44)));
                deleteRequestHeader();
                deleteRequestDetail();
                deleteProduct();
                realm.close();
            }
        });
    }

    /**
     * It link the Client with its Header
     * @param realm Realm instance
     */
    @Override
    public void saveClientInHeader(Realm realm) {
        RealmResults<com.luisbar.waterdelivery.data.model.local.RequestHeader> requestHeaders;
        requestHeaders = realm.where(com.luisbar.waterdelivery.data.model.local.RequestHeader.class)
                .findAll();

        for (com.luisbar.waterdelivery.data.model.local.RequestHeader requestHeader : requestHeaders) {
            com.luisbar.waterdelivery.data.model.local.Client client = realm
                    .where(com.luisbar.waterdelivery.data.model.local.Client.class)
                    .equalTo("ccnumi", requestHeader.getOaccli())
                    .findFirst();

            requestHeader.setClient(client);
        }
    }

    /**
     * It delete the data about Client
     */
    @Override
    public void deleteClient() {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
              realm.where(com.luisbar.waterdelivery.data.model.local.Client.class)
                      .findAll()
                      .deleteAllFromRealm();
            }
        },
        new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                realm.close();
            }
        },
        new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                realm.close();
            }
        });
    }

    /**
     * It update the copied field from Employee object
     * @param realm Realm instance
     */
    @Override
    public void updateUserState(final Realm realm) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Employee.class).findFirst().setCopied(true);
            }
        },
        new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                getAllForRecyclerView(realm);
            }
        },
        new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                EventBusMask.post(new AllDataFailedLeftP(WaterDeliveryApplication.resources.getString(R.string.txt_46)));
                deleteRequestHeader();
                deleteRequestDetail();
                deleteProduct();
                deleteClient();
                realm.close();
            }
        });
    }

    /**
     * It return the list with data for loading the recycler view
     * in HomeLeftFragment and HomeRightFragment
     * @param realm Realm instance
     * @return ArrayList like this [[oaest, ccdesc, ccdirec, cctelf1, cctelf2,
     *                             [oanumi, ccdesc, oafdoc, oaobs, cclat, cclongi,
     *                             [[cadesc, obpcant, obpbase, obptot, canumi]], total, ccnit]]]
     */
    @Override
    public void getAllForRecyclerView(final Realm realm) {
        realm.close();// It should be solved. Delete the realm parameter

        new Thread(new Runnable() {
            @Override
            public void run() {
                Realm realmT = Realm.getDefaultInstance();
                RealmResults<com.luisbar.waterdelivery.data.model.local.RequestHeader> requestHeadersTwo = getRequestHedersByState(realmT, 2);
                RealmResults<com.luisbar.waterdelivery.data.model.local.RequestHeader> requestHeadersThree = getRequestHedersByState(realmT, 3);

                Iterator<com.luisbar.waterdelivery.data.model.local.RequestHeader> iterator = requestHeadersTwo.iterator();
                List listLeft = getListWithAllData(iterator);

                iterator = requestHeadersThree.iterator();
                List listRight = getListWithAllData(iterator);
                listRight.add(getCreditAndCashAmount(realmT));

                EventBusMask.post(new RequestLeftP(listLeft));
                EventBusMask.post(new RequestRightP(listRight));

                realmT.close();
            }
        }).start();
    }

    /**
     * Return the amount accumulated in cash and credit
     * @param realm
     * @return List like this [cashAmount, creditAmount]
     */
    @Override
    public List getCreditAndCashAmount(Realm realm) {
        float credit = realm.where(CreditPayment.class)
                            .notEqualTo("ogcred", 0.0)
                            .findAll()
                            .sum("ogcred")
                            .floatValue();

        float cash = realm.where(CreditPayment.class)
                            .findAll()
                            .sum("total")
                            .floatValue();

        return Arrays.asList(cash-credit, credit);
    }

    /**
     * It return the data about the query
     * @param realm Realm instance
     * @param state State about request header
     * @return RealmResult<RequestHeader> with data from database
     */
    @Override
    public RealmResults<com.luisbar.waterdelivery.data.model.local.RequestHeader> getRequestHedersByState(Realm realm, int state) {
        RealmResults<com.luisbar.waterdelivery.data.model.local.RequestHeader> requestHeaders;
        requestHeaders = realm.where(com.luisbar.waterdelivery.data.model.local.RequestHeader.class)
                .equalTo("oaest", state)
                .findAll();

        return  requestHeaders;
    }

    /**
     * It return a list with data about requestheader
     * @param requestHeader RequestHeader object
     * @param details Array with object Details
     * @return List
     */
    @Override
    public List loadListHeader(com.luisbar.waterdelivery.data.model.local.RequestHeader requestHeader, List details) {

        return Arrays.asList(requestHeader.getOaest(),
                requestHeader.getClient().getCcdesc(),
                requestHeader.getClient().getCcdirec().toLowerCase(),
                requestHeader.getClient().getCctelf1(),
                requestHeader.getClient().getCctelf2(),
                details);
    }

    /**
     * It return a list with data about request header, products and total
     * @param requestHeader RequestHeader object
     * @param products List about products
     * @param total Total about the products prices
     * @return List
     */
    @Override
    public List loadListDetail(com.luisbar.waterdelivery.data.model.local.RequestHeader requestHeader,
                               List products, Double total) {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = null;

        try {
            date = format.format(format.parse(requestHeader.getOafdoc().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Arrays.asList(requestHeader.getOanumi(),
                requestHeader.getClient().getCcdesc().toLowerCase(),
                date,
                requestHeader.getOaobs(),
                requestHeader.getClient().getCclat(),
                requestHeader.getClient().getCclongi(),
                products,
                total,
                requestHeader.getClient().getCcnit());
    }

    /**
     * It return a list with data about products
     * @param requestDetail RequestDetail object
     * @return List
     */
    @Override
    public List loadListProduct(com.luisbar.waterdelivery.data.model.local.RequestDetail requestDetail) {
        return Arrays.asList(
                requestDetail.getProduct().getCadesc().toLowerCase(),
                requestDetail.getObpcant(),
                requestDetail.getObpbase(),
                requestDetail.getObptot(),
                requestDetail.getProduct().getCanumi());
    }

    /**
     * It return the done list for the recycler view
     * @param iterator Iterator with RequestHeader objects
     * @return List
     */
    @Override
    public List getListWithAllData(Iterator<com.luisbar.waterdelivery.data.model.local.RequestHeader> iterator) {
        List list = new ArrayList();

        while (iterator.hasNext()) {
            ArrayList products = new ArrayList();
            Double total = 0.0;

            com.luisbar.waterdelivery.data.model.local.RequestHeader requestHeader;
            requestHeader = iterator.next();

            for (com.luisbar.waterdelivery.data.model.local.RequestDetail requestDetail : requestHeader.getDetails()) {

                List product = loadListProduct(requestDetail);
                products.add(product);
                total += requestDetail.getObptot();
            }

            List details = loadListDetail(requestHeader, products, total);
            List header = loadListHeader(requestHeader, details);

            list.add(header);
        }

        return list;
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

    /**
     * It set the RestAdapter object
     * @param object RestAdapter object
     */
    @Override
    public void setmRestAdapter(Object object) {
        if (mRestAdapter == null)
            mRestAdapter = (RestAdapter) object;
    }
}
