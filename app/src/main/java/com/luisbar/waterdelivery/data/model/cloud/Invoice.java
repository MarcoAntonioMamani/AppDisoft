package com.luisbar.waterdelivery.data.model.cloud;

import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import org.json.JSONArray;

public class Invoice extends ModelRepository<Invoice.InvoiceNested> {

    public Invoice() {
        super("InvoiceNested", "Ts002s", InvoiceNested.class);
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/getDataForTheInvoice", "POST"),
                getClassName() + ".getDataForTheInvoice");

        return contract;
    }

    public void getDataForTheInvoice(int id, String identityCard, String amount, String clientName,
                                     JSONArray products, final Adapter.JsonObjectCallback callback) {

        invokeStaticMethod("getDataForTheInvoice",
                ImmutableMap.of("id", id, "identityCard", identityCard, "amount", amount,
                                "clientName", clientName, "detail", products),
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

    public static class InvoiceNested extends Model {

        private Integer yenumi;
        private Integer yecia;
        private Integer yealm;
        private Integer yesfc;
        private Integer yeautoriz;
        private Integer yenunf;
        private String yekey;
        private String yefdel;
        private String yefal;
        private String yenota;
        private String yeap;
        private String yefact;
        private String yehact;
        private String yeuact;

        /**
         *
         * @return
         * The yenumi
         */
        public Integer getYenumi() {
            return yenumi;
        }

        /**
         *
         * @param yenumi
         * The yenumi
         */
        public void setYenumi(Integer yenumi) {
            this.yenumi = yenumi;
        }

        /**
         *
         * @return
         * The yecia
         */
        public Integer getYecia() {
            return yecia;
        }

        /**
         *
         * @param yecia
         * The yecia
         */
        public void setYecia(Integer yecia) {
            this.yecia = yecia;
        }

        /**
         *
         * @return
         * The yealm
         */
        public Integer getYealm() {
            return yealm;
        }

        /**
         *
         * @param yealm
         * The yealm
         */
        public void setYealm(Integer yealm) {
            this.yealm = yealm;
        }

        /**
         *
         * @return
         * The yesfc
         */
        public Integer getYesfc() {
            return yesfc;
        }

        /**
         *
         * @param yesfc
         * The yesfc
         */
        public void setYesfc(Integer yesfc) {
            this.yesfc = yesfc;
        }

        /**
         *
         * @return
         * The yeautoriz
         */
        public Integer getYeautoriz() {
            return yeautoriz;
        }

        /**
         *
         * @param yeautoriz
         * The yeautoriz
         */
        public void setYeautoriz(Integer yeautoriz) {
            this.yeautoriz = yeautoriz;
        }

        /**
         *
         * @return
         * The yenunf
         */
        public Integer getYenunf() {
            return yenunf;
        }

        /**
         *
         * @param yenunf
         * The yenunf
         */
        public void setYenunf(Integer yenunf) {
            this.yenunf = yenunf;
        }

        /**
         *
         * @return
         * The yekey
         */
        public String getYekey() {
            return yekey;
        }

        /**
         *
         * @param yekey
         * The yekey
         */
        public void setYekey(String yekey) {
            this.yekey = yekey;
        }

        /**
         *
         * @return
         * The yefdel
         */
        public String getYefdel() {
            return yefdel;
        }

        /**
         *
         * @param yefdel
         * The yefdel
         */
        public void setYefdel(String yefdel) {
            this.yefdel = yefdel;
        }

        /**
         *
         * @return
         * The yefal
         */
        public String getYefal() {
            return yefal;
        }

        /**
         *
         * @param yefal
         * The yefal
         */
        public void setYefal(String yefal) {
            this.yefal = yefal;
        }

        /**
         *
         * @return
         * The yenota
         */
        public String getYenota() {
            return yenota;
        }

        /**
         *
         * @param yenota
         * The yenota
         */
        public void setYenota(String yenota) {
            this.yenota = yenota;
        }

        /**
         *
         * @return
         * The yeap
         */
        public String getYeap() {
            return yeap;
        }

        /**
         *
         * @param yeap
         * The yeap
         */
        public void setYeap(String yeap) {
            this.yeap = yeap;
        }

        /**
         *
         * @return
         * The yefact
         */
        public String getYefact() {
            return yefact;
        }

        /**
         *
         * @param yefact
         * The yefact
         */
        public void setYefact(String yefact) {
            this.yefact = yefact;
        }

        /**
         *
         * @return
         * The yehact
         */
        public String getYehact() {
            return yehact;
        }

        /**
         *
         * @param yehact
         * The yehact
         */
        public void setYehact(String yehact) {
            this.yehact = yehact;
        }

        /**
         *
         * @return
         * The yeuact
         */
        public String getYeuact() {
            return yeuact;
        }

        /**
         *
         * @param yeuact
         * The yeuact
         */
        public void setYeuact(String yeuact) {
            this.yeuact = yeuact;
        }
    }
}
