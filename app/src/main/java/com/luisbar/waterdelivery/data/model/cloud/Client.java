package com.luisbar.waterdelivery.data.model.cloud;

import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import org.json.JSONObject;

import java.util.List;

public class Client extends ModelRepository<Client.ClientNested> {

    public Client() {
        super("ClientNested", "Tc004s", ClientNested.class);
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/getClientsByZones", "POST"),
                getClassName() + ".getClientsByZones");

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/saveClient", "POST"),
                getClassName() + ".saveClient");

        return contract;
    }

    public void getClientsByZones(List zones, final Adapter.JsonObjectCallback callback) {
        invokeStaticMethod("getClientsByZones",
                ImmutableMap.of("zones", zones),
                new Adapter.Callback() {
                    @Override
                    public void onSuccess(String response) {
                        callback.onSuccess(response);
                    }
                    @Override
                    public void onError(Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public void saveClient(String data, final Adapter.JsonObjectCallback callback) {
        invokeStaticMethod("saveClient",
                ImmutableMap.of("param", data),
                new Adapter.JsonObjectCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public class ClientNested extends Model {

        private List<Integer> result;

        /**
         *
         * @return
         * The result
         */
        public List<Integer> getResult() {
            return result;
        }

        /**
         *
         * @param result
         * The result
         */
        public void setResult(List<Integer> result) {
            this.result = result;
        }
    }
}
