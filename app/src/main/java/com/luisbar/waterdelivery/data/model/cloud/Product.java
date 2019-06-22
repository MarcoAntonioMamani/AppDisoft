package com.luisbar.waterdelivery.data.model.cloud;

import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

public class Product extends ModelRepository<Product.ProductNested> {

    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/findAndMatchPrice", "POST"),
                getClassName() + ".findAndMatchPrice");

        return contract;
    }

    public void findAndMatchPrice(final Adapter.JsonObjectCallback callback) {
        invokeStaticMethod("findAndMatchPrice", null,
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

    public Product() {
        super("ProductNested", "Tc003s", ProductNested.class);
    }

    public static class ProductNested extends Model {

        private Integer canumi;
        private String cacod;
        private String cadesc;
        private String cadesc2;
        private Integer cacat;
        private String caimg;
        private Boolean castc;
        private Boolean caest;
        private Boolean caserie;
        private String cafact;
        private String cahact;
        private String cauact;
        private Double precio;

        /**
         *
         * @return
         * The canumi
         */
        public Integer getCanumi() {
            return canumi;
        }

        /**
         *
         * @param canumi
         * The canumi
         */
        public void setCanumi(Integer canumi) {
            this.canumi = canumi;
        }

        /**
         *
         * @return
         * The cacod
         */
        public String getCacod() {
            return cacod;
        }

        /**
         *
         * @param cacod
         * The cacod
         */
        public void setCacod(String cacod) {
            this.cacod = cacod;
        }

        /**
         *
         * @return
         * The cadesc
         */
        public String getCadesc() {
            return cadesc;
        }

        /**
         *
         * @param cadesc
         * The cadesc
         */
        public void setCadesc(String cadesc) {
            this.cadesc = cadesc;
        }

        /**
         *
         * @return
         * The cadesc2
         */
        public String getCadesc2() {
            return cadesc2;
        }

        /**
         *
         * @param cadesc2
         * The cadesc2
         */
        public void setCadesc2(String cadesc2) {
            this.cadesc2 = cadesc2;
        }

        /**
         *
         * @return
         * The cacat
         */
        public Integer getCacat() {
            return cacat;
        }

        /**
         *
         * @param cacat
         * The cacat
         */
        public void setCacat(Integer cacat) {
            this.cacat = cacat;
        }

        /**
         *
         * @return
         * The caimg
         */
        public String getCaimg() {
            return caimg;
        }

        /**
         *
         * @param caimg
         * The caimg
         */
        public void setCaimg(String caimg) {
            this.caimg = caimg;
        }

        /**
         *
         * @return
         * The castc
         */
        public Boolean getCastc() {
            return castc;
        }

        /**
         *
         * @param castc
         * The castc
         */
        public void setCastc(Boolean castc) {
            this.castc = castc;
        }

        /**
         *
         * @return
         * The caest
         */
        public Boolean getCaest() {
            return caest;
        }

        /**
         *
         * @param caest
         * The caest
         */
        public void setCaest(Boolean caest) {
            this.caest = caest;
        }

        /**
         *
         * @return
         * The caserie
         */
        public Boolean getCaserie() {
            return caserie;
        }

        /**
         *
         * @param caserie
         * The caserie
         */
        public void setCaserie(Boolean caserie) {
            this.caserie = caserie;
        }

        /**
         *
         * @return
         * The cafact
         */
        public String getCafact() {
            return cafact;
        }

        /**
         *
         * @param cafact
         * The cafact
         */
        public void setCafact(String cafact) {
            this.cafact = cafact;
        }

        /**
         *
         * @return
         * The cahact
         */
        public String getCahact() {
            return cahact;
        }

        /**
         *
         * @param cahact
         * The cahact
         */
        public void setCahact(String cahact) {
            this.cahact = cahact;
        }

        /**
         *
         * @return
         * The cauact
         */
        public String getCauact() {
            return cauact;
        }

        /**
         *
         * @param cauact
         * The cauact
         */
        public void setCauact(String cauact) {
            this.cauact = cauact;
        }

        public Double getPrecio() {
            return precio;
        }

        public void setPrecio(Double precio) {
            this.precio = precio;
        }
    }
}
