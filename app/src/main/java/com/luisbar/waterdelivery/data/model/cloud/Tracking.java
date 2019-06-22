package com.luisbar.waterdelivery.data.model.cloud;

import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import org.json.JSONObject;

public class Tracking extends ModelRepository<Tracking.TrackingNested> {

    public Tracking() {
        super("TrackingNested", "Tl002s", TrackingNested.class);
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/saveLocation", "POST"),
                getClassName() + ".saveLocation");

        return contract;
    }

    public void saveLocation(int id, String latitud, String longitud, final Adapter.JsonObjectCallback callback) {
        invokeStaticMethod("saveLocation",
                ImmutableMap.of("employeeId", id, "lat", latitud, "lng", longitud),
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

    public static class TrackingNested extends Model {
        private Integer ldnumi;
        private Integer ldchof;
        private String ldfec;
        private String ldhora;
        private Integer lblat;
        private Integer lblongi;

        public Integer getLdnumi() {
            return ldnumi;
        }

        public void setLdnumi(Integer ldnumi) {
            this.ldnumi = ldnumi;
        }

        public Integer getLdchof() {
            return ldchof;
        }

        public void setLdchof(Integer ldchof) {
            this.ldchof = ldchof;
        }

        public String getLdfec() {
            return ldfec;
        }

        public void setLdfec(String ldfec) {
            this.ldfec = ldfec;
        }

        public String getLdhora() {
            return ldhora;
        }

        public void setLdhora(String ldhora) {
            this.ldhora = ldhora;
        }

        public Integer getLblat() {
            return lblat;
        }

        public void setLblat(Integer lblat) {
            this.lblat = lblat;
        }

        public Integer getLblongi() {
            return lblongi;
        }

        public void setLblongi(Integer lblongi) {
            this.lblongi = lblongi;
        }
    }
}
