package com.luisbar.waterdelivery.data.model.local;

import io.realm.RealmObject;

public class InvoiceDetail extends RealmObject {

    private Integer fvbnumi;
    private String fvbcprod;
    private String fvbdesprod;
    private Integer fvbcant;
    private Integer fvbprecio;
    private Integer fvbnumi2;

    /**
     *
     * @return
     * The fvbnumi
     */
    public Integer getFvbnumi() {
        return fvbnumi;
    }

    /**
     *
     * @param fvbnumi
     * The fvbnumi
     */
    public void setFvbnumi(Integer fvbnumi) {
        this.fvbnumi = fvbnumi;
    }

    /**
     *
     * @return
     * The fvbcprod
     */
    public String getFvbcprod() {
        return fvbcprod;
    }

    /**
     *
     * @param fvbcprod
     * The fvbcprod
     */
    public void setFvbcprod(String fvbcprod) {
        this.fvbcprod = fvbcprod;
    }

    /**
     *
     * @return
     * The fvbdesprod
     */
    public String getFvbdesprod() {
        return fvbdesprod;
    }

    /**
     *
     * @param fvbdesprod
     * The fvbdesprod
     */
    public void setFvbdesprod(String fvbdesprod) {
        this.fvbdesprod = fvbdesprod;
    }

    /**
     *
     * @return
     * The fvbcant
     */
    public Integer getFvbcant() {
        return fvbcant;
    }

    /**
     *
     * @param fvbcant
     * The fvbcant
     */
    public void setFvbcant(Integer fvbcant) {
        this.fvbcant = fvbcant;
    }

    /**
     *
     * @return
     * The fvbprecio
     */
    public Integer getFvbprecio() {
        return fvbprecio;
    }

    /**
     *
     * @param fvbprecio
     * The fvbprecio
     */
    public void setFvbprecio(Integer fvbprecio) {
        this.fvbprecio = fvbprecio;
    }

    /**
     *
     * @return
     * The fvbnumi2
     */
    public Integer getFvbnumi2() {
        return fvbnumi2;
    }

    /**
     *
     * @param fvbnumi2
     * The fvbnumi2
     */
    public void setFvbnumi2(Integer fvbnumi2) {
        this.fvbnumi2 = fvbnumi2;
    }
}
