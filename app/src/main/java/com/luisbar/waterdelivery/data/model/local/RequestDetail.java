package com.luisbar.waterdelivery.data.model.local;

import io.realm.RealmObject;

public class RequestDetail extends RealmObject {

    private Integer obnumi;
    private String obcprod;
    private double obpcant;
    private double obpbase;
    private double obptot;

    private Product product;

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
    public double getObpcant() {
        return obpcant;
    }

    /**
     *
     * @param obpcant
     * The obpcant
     */
    public void setObpcant(double obpcant) {
        this.obpcant = obpcant;
    }

    /**
     *
     * @return
     * The obpbase
     */
    public double getObpbase() {
        return obpbase;
    }

    /**
     *
     * @param obpbase
     * The obpbase
     */
    public void setObpbase(double obpbase) {
        this.obpbase = obpbase;
    }

    /**
     *
     * @return
     * The obptot
     */
    public double getObptot() {
        return obptot;
    }

    /**
     *
     * @param obptot
     * The obptot
     */
    public void setObptot(double obptot) {
        this.obptot = obptot;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
