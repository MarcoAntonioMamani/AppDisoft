package com.luisbar.waterdelivery.data.repository;

import android.util.Log;

import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.WaterDeliveryApplication;
import com.luisbar.waterdelivery.common.eventbus.DataForInvoiceFailedP;
import com.luisbar.waterdelivery.common.eventbus.DataForInvoiceSucceededP;
import com.luisbar.waterdelivery.common.eventbus.EventBusMask;
import com.luisbar.waterdelivery.common.eventbus.ClientsAndProductsSucceededP;
import com.luisbar.waterdelivery.common.eventbus.SaveOrderFailedP;
import com.luisbar.waterdelivery.common.eventbus.SaveOrderSucceededP;
import com.luisbar.waterdelivery.common.eventbus.UpdateStateFailedP;
import com.luisbar.waterdelivery.common.eventbus.UpdateStateSucceededP;
import com.luisbar.waterdelivery.data.model.cloud.Invoice;
import com.luisbar.waterdelivery.data.model.local.Client;
import com.luisbar.waterdelivery.data.model.local.CreditPayment;
import com.luisbar.waterdelivery.data.model.local.InvoiceDetail;
import com.luisbar.waterdelivery.data.model.local.InvoiceHeader;
import com.luisbar.waterdelivery.data.model.local.InvoiceInformation;
import com.luisbar.waterdelivery.data.model.local.Product;
import com.luisbar.waterdelivery.data.model.local.RequestDetail;
import com.luisbar.waterdelivery.data.model.local.RequestHeader;
import com.luisbar.waterdelivery.common.config.Config;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.remoting.adapters.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class OrderRepository implements OrderRepositoryI {

    private HomeLeftRepositoryI mHomeLeftRepository;
    private RestAdapter mRestAdapter;

    public OrderRepository() {
        this.mHomeLeftRepository = new HomeLeftRepository();
    }

    /**
     * It update the state about an specific request in the cloud
     * @param id RequestHeader id
     * @param object RestAdpter
     */
    @Override
    public void updateState(final int id, final String amount, final String idrepa, Object object) {
        setmRestAdapter(object);
        com.luisbar.waterdelivery.data.model.cloud.RequestHeader requestHeader;
        requestHeader = mRestAdapter.createRepository(com.luisbar.waterdelivery.data.model.cloud.RequestHeader.class);

        requestHeader.updateStateTwoToThree(id, amount, idrepa, new Adapter.JsonObjectCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {

                    if (response.getBoolean("result"))//areglar esto
                        updateStateInRealm(id, amount, idrepa);
                    else
                        EventBusMask.post(new UpdateStateFailedP(WaterDeliveryApplication.resources.getString(R.string.txt_54)));
                } catch (JSONException e) {

                    EventBusMask.post(new UpdateStateFailedP(WaterDeliveryApplication.resources.getString(R.string.txt_54)));
                }
            }

            @Override
            public void onError(Throwable t) {
                EventBusMask.post(new UpdateStateFailedP(WaterDeliveryApplication.resources.getString(R.string.txt_54)));
            }
        });
    }

    /**
     * It update the state about an specific request in the realm database
     * @param id RequestHeader id
     */
    @Override
    public void updateStateInRealm(final int id, final String amount, final String idrepa) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RequestHeader requestHeader = realm.where(RequestHeader.class)
                      .equalTo("oanumi", id)
                      .findFirst();

                requestHeader.setOarepa(Integer.parseInt(idrepa));
                requestHeader.setOaest(3);

                //insert in credit payment table
                ////MArco
                CreditPayment credit = realm.createObject(CreditPayment.class);
                double total = 0;

                credit.setOgnumi(id);
                credit.setOgcred(Double.parseDouble(amount));

                for (RequestDetail detail : requestHeader.getDetails()) total = total + detail.getObptot();

                credit.setTotal(total);
            }
        },
        new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                mHomeLeftRepository.getAllForRecyclerView(realm);
                EventBusMask.post(new UpdateStateSucceededP(WaterDeliveryApplication.resources.getString(R.string.txt_56)));
            }
        },
        new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                EventBusMask.post(new UpdateStateFailedP(WaterDeliveryApplication.resources.getString(R.string.txt_55)));
                realm.close();
            }
        });
    }

    /**
     * It recovers the data about the invoice from the cloud
     * @param identityCard Identity card or nit
     * @param amount Total amount
     * @param clientName Client name
     * @param products Products list
     * @param  object RestAdapter object
     */
    @Override
    public void getDataForTheInvoice(int id, String identityCard, String amount, String clientName, JSONArray products, Object object) {
        if (invoiceHasNotBeenCreated(id)) {

            setmRestAdapter(object);
            Invoice invoice = mRestAdapter.createRepository(Invoice.class);

            invoice.getDataForTheInvoice(id, identityCard, amount, clientName, products, new Adapter.JsonObjectCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    Log.e("onSuccess: ", response.toString());
                    try {
                        if (!response.getJSONObject("result").has("error"))
                            saveInRealDatabase(response);
                        else
                            EventBusMask.post(new DataForInvoiceFailedP(WaterDeliveryApplication
                                    .resources
                                    .getString(R.string.txt_65)));

                    } catch (JSONException e) {
                        EventBusMask.post(new DataForInvoiceFailedP(WaterDeliveryApplication
                                .resources
                                .getString(R.string.txt_65)));
                    }
                }

                @Override
                public void onError(Throwable t) {
                    EventBusMask.post(new DataForInvoiceFailedP(WaterDeliveryApplication
                            .resources
                            .getString(R.string.txt_65)));
                }
            });
        } else
            getInvoiceFromLocalDatabase(id);
    }

    /**
     * Verify if the invoice has been created
     * @param id Id about of request header
     * @return True if the invoice has been created and false if has not been yet
     */
    @Override
    public boolean invoiceHasNotBeenCreated(int id) {
        Realm realm = Realm.getDefaultInstance();
        boolean flag;

        RealmResults<InvoiceHeader> invoices = realm.where(InvoiceHeader.class)
                .equalTo("fvanumi", id)
                .findAll();
        flag = invoices.isEmpty();

        realm.close();

        return flag;
    }

    /**
     * Return the data about the invoice specified
     * @param id Id about request header
     */
    @Override
    public void getInvoiceFromLocalDatabase(int id) {
        Realm realm = Realm.getDefaultInstance();

        InvoiceHeader invoice = realm.where(InvoiceHeader.class)
                .equalTo("fvanumi", id)
                .findFirst();
        InvoiceInformation invoiceInformation = realm.where(InvoiceInformation.class)
                .equalTo("scid", id + "")
                .findFirst();

        JSONArray header = new JSONArray();
        JSONArray inf = new JSONArray();

        try {
            inf.put(new JSONObject().put("scnom", invoiceInformation.getScnom())
                                    .put("scneg", invoiceInformation.getScneg())
                                    .put("scsuc", invoiceInformation.getScsuc())
                                    .put("scdir", invoiceInformation.getScdir())
                                    .put("sctelf", invoiceInformation.getSctelf())
                                    .put("scciu", invoiceInformation.getScciu())
                                    .put("scpai", invoiceInformation.getScpai())
                                    .put("scsfc", invoiceInformation.getScsfc())
                                    .put("scnit", invoiceInformation.getScnit())
                                    .put("scact", invoiceInformation.getScact())
                                    .put("scnoteone", invoiceInformation.getScnoteone())
                                    .put("scnotetwo", invoiceInformation.getScnotetwo()));

            header.put(new JSONObject().put("fvanfac", invoice.getFvanfac())
                                        .put("fvaautoriz", invoice.getFvaautoriz())
                                        .put("fvafec", invoice.getFvafec())
                                        .put("fvadescli1", invoice.getFvadescli1())
                                        .put("fvanitcli", invoice.getFvanitcli())
                                        .put("fvaccont", invoice.getFvaccont())
                                        .put("fvaflim", invoice.getFvaflim())
                                        .put("fvatotal", invoice.getFvatotal()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<JSONArray> list = Arrays.asList(header, inf);
        realm.close();
        EventBusMask.post(new DataForInvoiceSucceededP(list));
    }

    /**
     * It saves the data about the invoice
     * @param response Json object
     */
    @Override
    public void saveInRealDatabase(final JSONObject response) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    JSONArray header = response.getJSONObject("result").getJSONArray("header");
                    JSONArray detail = response.getJSONObject("result").getJSONArray("detail");
                    JSONArray inf = response.getJSONObject("result").getJSONArray("inf");

                    realm.createAllFromJson(InvoiceHeader.class, header);
                    realm.createAllFromJson(InvoiceDetail.class, detail);
                    realm.createAllFromJson(InvoiceInformation.class, inf);
                } catch (JSONException e) {

                    EventBusMask.post(new DataForInvoiceFailedP(WaterDeliveryApplication
                            .resources
                            .getString(R.string.txt_66)));
                }
            }
        },
        new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                JSONArray header = null;
                JSONArray inf = null;

                try {
                    header = response.getJSONObject("result").getJSONArray("header");
                    inf = response.getJSONObject("result").getJSONArray("inf");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                EventBusMask.post(new DataForInvoiceSucceededP(Arrays.asList(
                    header, inf
                )));
                realm.close();
            }
        },
        new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                EventBusMask.post(new DataForInvoiceFailedP(WaterDeliveryApplication
                        .resources
                        .getString(R.string.txt_66)));
                realm.close();
            }
        });
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

    /**
     * It gets the clients and products from local database and sent them to the OrderPresenter
     */
    @Override
    public void getClientsAndProducts() {
        Realm realm = Realm.getDefaultInstance();
        List lstClients = new ArrayList();
        List lstProducts = new ArrayList();

        RealmResults<Client> clients = realm.where(Client.class)
                .findAll();
        RealmResults<Product> products =  realm.where(Product.class)
                .findAll();

        for (Client client : clients) lstClients.add(Arrays.asList(client.getCcnumi(),
                                                    client.getCcdesc().toLowerCase()));
        for (Product product : products) lstProducts.add(Arrays.asList(product.getCanumi(),
                                                    product.getCadesc().toLowerCase(),
                                                    product.getPrecio()));

        EventBusMask.post(new ClientsAndProductsSucceededP(Arrays.asList(lstClients, lstProducts)));
    }

    /**
     * It saves the new order in the cloud
     * @param clientId Client selected
     * @param date Date about new order
     * @param observation Some information about th order
     * @param products List of products about new order
     * @param total Total price about new order
     * @param object RestAdapter object
     */
    @Override
    public void saveOrderInCloud(String clientId, String date, String observation, String products, Double total, Object object) {
        setmRestAdapter(object);
        com.luisbar.waterdelivery.data.model.cloud.RequestHeader requestHeader;
        requestHeader = mRestAdapter.createRepository(com.luisbar.waterdelivery.data.model.cloud.RequestHeader.class);

        requestHeader.saveOrder(clientId, date, observation, products, new Adapter.JsonObjectCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.e("onSuccess: ", response.toString());
                try {
                    if (response.get("result") instanceof JSONArray)
                        saveOrderInLocal(response);
                    else
                        EventBusMask.post(new SaveOrderFailedP(WaterDeliveryApplication.resources.getString(R.string.txt_79)));
                } catch (JSONException e) {
                    EventBusMask.post(new SaveOrderFailedP(WaterDeliveryApplication.resources.getString(R.string.txt_79)));
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.e("onError: ", t.getMessage());
                EventBusMask.post(new SaveOrderFailedP(WaterDeliveryApplication.resources.getString(R.string.txt_79)));
            }
        });
    }

    /**
     * It saves the new order in local database
     */
    @Override
    public void saveOrderInLocal(JSONObject response) {
        try {
            final Realm realm = Realm.getDefaultInstance();
            final JSONArray result = response.getJSONArray("result");
            final JSONArray products = result.getJSONObject(0).getJSONArray("details");
            final int clientId = result.getJSONObject(0).getInt("oaccli");
            final int orederId = result.getJSONObject(0).getInt("oanumi");

            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.createAllFromJson(RequestHeader.class, result);
                }
            },
            new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Log.e("onSuccess: ", "SI");
                    bindClientWithOrder(clientId, orederId, realm);
                    bindProductsWithDetail(products, realm);
                    mHomeLeftRepository.getAllForRecyclerView(realm);
                    EventBusMask.post(new SaveOrderSucceededP(WaterDeliveryApplication.resources.getString(R.string.txt_78)));
                }
            },
            new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    Log.e("onError: ", error.getMessage());
                    realm.close();
                    EventBusMask.post(new SaveOrderFailedP(WaterDeliveryApplication.resources.getString(R.string.txt_79)));
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void bindClientWithOrder(int id, int orderId, Realm realm) {
        realm.beginTransaction();

        RequestHeader requestHeader = realm.where(RequestHeader.class)
                .equalTo("oanumi", orderId)
                .findFirst();
        Client client = realm.where(Client.class)
                .equalTo("ccnumi", id)
                .findFirst();

        requestHeader.setClient(client);

        realm.commitTransaction();
    }

    public void bindProductsWithDetail(JSONArray products, Realm realm) {
        try {

            realm.beginTransaction();

            for (int i = 0; i < products.length(); i++) {
                JSONObject product = products.getJSONObject(i);

                RequestDetail detail = realm.where(RequestDetail.class)
                        .equalTo("obnumi", product.getInt("obnumi"))
                        .equalTo("obcprod", product.getString("obcprod"))
                        .findFirst();

                Product productR = realm.where(Product.class)
                        .equalTo("canumi", product.getInt("obcprod"))
                        .findFirst();

                detail.setProduct(productR);
            }

            realm.commitTransaction();
        } catch (JSONException e) {
            realm.commitTransaction();
            e.printStackTrace();
        }
    }
}
