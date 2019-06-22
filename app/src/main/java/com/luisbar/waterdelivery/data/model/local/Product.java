package com.luisbar.waterdelivery.data.model.local;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Product extends RealmObject {

    @PrimaryKey
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
