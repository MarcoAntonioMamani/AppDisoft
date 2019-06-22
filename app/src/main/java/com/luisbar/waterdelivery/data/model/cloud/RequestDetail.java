package com.luisbar.waterdelivery.data.model.cloud;


import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import java.util.List;

public class RequestDetail extends ModelRepository<RequestDetail.RequestDetailNested> {

    public RequestDetail() {
        super("RequestDetailNested", "To0011s", RequestDetailNested.class);
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/getDetailRequestByIds", "POST"),
                getClassName() + ".getDetailRequestByIds");

        return contract;
    }

    public void getDetailRequestByIds(List ids, final Adapter.JsonObjectCallback callback) {

        invokeStaticMethod("getDetailRequestByIds", ImmutableMap.of("ids", ids), new Adapter.Callback() {

            @Override
            public void onError(Throwable t) {
                callback.onError(t);
            }

            @Override
            public void onSuccess(String response) {
                callback.onSuccess(response);
            }
        });
    }

    public static class RequestDetailNested extends Model {

        private Integer obnumi;
        private String obcprod;
        private Integer obpcant;
        private Integer obpbase;
        private Integer obptot;

        /**
         *
         * @return
         * The obnumi
         */
        public Integer getObnumi() {
            return obnumi;
        }

        /**
         *
         * @param obnumi
         * The obnumi
         */
        public void setObnumi(Integer obnumi) {
            this.obnumi = obnumi;
        }

        /**
         *
         * @return
         * The obcprod
         */
        public String getObcprod() {
            return obcprod;
        }

        /**
         *
         * @param obcprod
         * The obcprod
         */
        public void setObcprod(String obcprod) {
            this.obcprod = obcprod;
        }

        /**
         *
         * @return
         * The obpcant
         */
        public Integer getObpcant() {
            return obpcant;
        }

        /**
         *
         * @param obpcant
         * The obpcant
         */
        public void setObpcant(Integer obpcant) {
            this.obpcant = obpcant;
        }

        /**
         *
         * @return
         * The obpbase
         */
        public Integer getObpbase() {
            return obpbase;
        }

        /**
         *
         * @param obpbase
         * The obpbase
         */
        public void setObpbase(Integer obpbase) {
            this.obpbase = obpbase;
        }

        /**
         *
         * @return
         * The obptot
         */
        public Integer getObptot() {
            return obptot;
        }

        /**
         *
         * @param obptot
         * The obptot
         */
        public void setObptot(Integer obptot) {
            this.obptot = obptot;
        }
    }
}
