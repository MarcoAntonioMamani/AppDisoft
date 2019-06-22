package com.luisbar.waterdelivery.data.model.cloud;

import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

public class VrMoGetpedidos extends ModelRepository<VrMoGetpedidos.RequestHeaderNested> {

    public VrMoGetpedidos() {
        super("RequestHeaderNested", "VrMoGetpedidos", RequestHeaderNested.class);
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/getRequestHeaderByEmployeId", "POST"),
                getClassName() + ".getRequestHeaderByEmployeId");

        return contract;
    }

    public void getRequestHeaderByEmployeId(int id, final Adapter.JsonObjectCallback callback) {
        invokeStaticMethod("getRequestHeaderByEmployeId",
                ImmutableMap.of("id", id),
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


    public static class RequestHeaderNested extends Model {

        private Integer oanumi;
        private String oafdoc;
        private String oahora;
        private Integer oaccli;
        private Integer oazona;
        private Integer oarepa;
        private String oaobs;
        private String oaobs2;
        private Integer oaest;
        private Integer oaap;
        private Integer oapg;
        private String oafact;
        private String oahact;
        private String oauact;

        /**
         *
         * @return
         * The oanumi
         */
        public Integer getOanumi() {
            return oanumi;
        }

        /**
         *
         * @param oanumi
         * The oanumi
         */
        public void setOanumi(Integer oanumi) {
            this.oanumi = oanumi;
        }

        /**
         *
         * @return
         * The oafdoc
         */
        public String getOafdoc() {
            return oafdoc;
        }

        /**
         *
         * @param oafdoc
         * The oafdoc
         */
        public void setOafdoc(String oafdoc) {
            this.oafdoc = oafdoc;
        }

        /**
         *
         * @return
         * The oahora
         */
        public String getOahora() {
            return oahora;
        }

        /**
         *
         * @param oahora
         * The oahora
         */
        public void setOahora(String oahora) {
            this.oahora = oahora;
        }

        /**
         *
         * @return
         * The oaccli
         */
        public Integer getOaccli() {
            return oaccli;
        }

        /**
         *
         * @param oaccli
         * The oaccli
         */
        public void setOaccli(Integer oaccli) {
            this.oaccli = oaccli;
        }

        /**
         *
         * @return
         * The oazona
         */
        public Integer getOazona() {
            return oazona;
        }

        /**
         *
         * @param oazona
         * The oazona
         */
        public void setOazona(Integer oazona) {
            this.oazona = oazona;
        }

        /**
         *
         * @return
         * The oarepa
         */
        public Integer getOarepa() {
            return oarepa;
        }

        /**
         *
         * @param oarepa
         * The oarepa
         */
        public void setOarepa(Integer oarepa) {
            this.oarepa = oarepa;
        }

        /**
         *
         * @return
         * The oaobs
         */
        public String getOaobs() {
            return oaobs;
        }

        /**
         *
         * @param oaobs
         * The oaobs
         */
        public void setOaobs(String oaobs) {
            this.oaobs = oaobs;
        }

        /**
         *
         * @return
         * The oaobs2
         */
        public String getOaobs2() {
            return oaobs2;
        }

        /**
         *
         * @param oaobs2
         * The oaobs2
         */
        public void setOaobs2(String oaobs2) {
            this.oaobs2 = oaobs2;
        }

        /**
         *
         * @return
         * The oaest
         */
        public Integer getOaest() {
            return oaest;
        }

        /**
         *
         * @param oaest
         * The oaest
         */
        public void setOaest(Integer oaest) {
            this.oaest = oaest;
        }

        /**
         *
         * @return
         * The oaap
         */
        public Integer getOaap() {
            return oaap;
        }

        /**
         *
         * @param oaap
         * The oaap
         */
        public void setOaap(Integer oaap) {
            this.oaap = oaap;
        }

        /**
         *
         * @return
         * The oapg
         */
        public Integer getOapg() {
            return oapg;
        }

        /**
         *
         * @param oapg
         * The oapg
         */
        public void setOapg(Integer oapg) {
            this.oapg = oapg;
        }

        /**
         *
         * @return
         * The oafact
         */
        public String getOafact() {
            return oafact;
        }

        /**
         *
         * @param oafact
         * The oafact
         */
        public void setOafact(String oafact) {
            this.oafact = oafact;
        }

        /**
         *
         * @return
         * The oahact
         */
        public String getOahact() {
            return oahact;
        }

        /**
         *
         * @param oahact
         * The oahact
         */
        public void setOahact(String oahact) {
            this.oahact = oahact;
        }

        /**
         *
         * @return
         * The oauact
         */
        public String getOauact() {
            return oauact;
        }

        /**
         *
         * @param oauact
         * The oauact
         */
        public void setOauact(String oauact) {
            this.oauact = oauact;
        }
    }
}
