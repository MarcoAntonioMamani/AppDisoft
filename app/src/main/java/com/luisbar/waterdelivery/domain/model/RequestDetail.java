package com.luisbar.waterdelivery.domain.model;


public class RequestDetail {

    private Integer obnumi;
    private String obcprod;
    private Integer obpcant;
    private String obpbase;
    private String obptot;

    public RequestDetail(Integer obnumi, String obcprod, Integer obpcant, String obpbase, String obptot) {
        this.obnumi = obnumi;
        this.obcprod = obcprod;
        this.obpcant = obpcant;
        this.obpbase = obpbase;
        this.obptot = obptot;
    }

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
    public String getObpbase() {
        return obpbase;
    }

    /**
     *
     * @param obpbase
     * The obpbase
     */
    public void setObpbase(String obpbase) {
        this.obpbase = obpbase;
    }

    /**
     *
     * @return
     * The obptot
     */
    public String getObptot() {
        return obptot;
    }

    /**
     *
     * @param obptot
     * The obptot
     */
    public void setObptot(String obptot) {
        this.obptot = obptot;
    }
}
