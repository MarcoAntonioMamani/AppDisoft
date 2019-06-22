package com.luisbar.waterdelivery.data.model.cloud;

import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.ModelRepository;

public class Fcm extends ModelRepository<Fcm.FcmNested> {

    public Fcm() {
        super("FcmNested", "Tc0022s", Fcm.FcmNested.class);
    }

    public static class FcmNested extends Model {

        private Integer cknumi;
        private String ckidfsm;

        /**
         *
         * @return
         * The cknumi
         */
        public Integer getCknumi() {
            return cknumi;
        }

        /**
         *
         * @param cknumi
         * The cknumi
         */
        public void setCknumi(Integer cknumi) {
            this.cknumi = cknumi;
        }

        /**
         *
         * @return
         * The ckidfsm
         */
        public String getCkidfsm() {
            return ckidfsm;
        }

        /**
         *
         * @param ckidfsm
         * The ckidfsm
         */
        public void setCkidfsm(String ckidfsm) {
            this.ckidfsm = ckidfsm;
        }
    }
}
