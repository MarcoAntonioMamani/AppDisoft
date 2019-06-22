package com.luisbar.waterdelivery.data.model.local;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class InvoiceHeader extends RealmObject {

    @PrimaryKey
    private Integer fvanumi;
    private String fvafec;
    private Integer fvanfac;
    private Integer fvaautoriz;
    private Integer fvaest;
    private String fvanitcli;
    private Integer fvacodcli;
    private String fvadescli1;
    private String fvadescli2;
    private Integer fvastot;
    private Integer fvaimpsi;
    private Integer fvaimpeo;
    private Integer fvaimptc;
    private Integer fvasubtotal;
    private Integer fvadesc;
    private Integer fvatotal;
    private Integer fvadebfis;
    private String fvaccont;
    private String fvaflim;
    private Integer fvaalm;
    private Integer fvanumi2;

    /**
     *
     * @return
     * The fvanumi
     */
    public Integer getFvanumi() {
        return fvanumi;
    }

    /**
     *
     * @param fvanumi
     * The fvanumi
     */
    public void setFvanumi(Integer fvanumi) {
        this.fvanumi = fvanumi;
    }

    /**
     *
     * @return
     * The fvafec
     */
    public String getFvafec() {
        return fvafec;
    }

    /**
     *
     * @param fvafec
     * The fvafec
     */
    public void setFvafec(String fvafec) {
        this.fvafec = fvafec;
    }

    /**
     *
     * @return
     * The fvanfac
     */
    public Integer getFvanfac() {
        return fvanfac;
    }

    /**
     *
     * @param fvanfac
     * The fvanfac
     */
    public void setFvanfac(Integer fvanfac) {
        this.fvanfac = fvanfac;
    }

    /**
     *
     * @return
     * The fvaautoriz
     */
    public Integer getFvaautoriz() {
        return fvaautoriz;
    }

    /**
     *
     * @param fvaautoriz
     * The fvaautoriz
     */
    public void setFvaautoriz(Integer fvaautoriz) {
        this.fvaautoriz = fvaautoriz;
    }

    /**
     *
     * @return
     * The fvaest
     */
    public Integer getFvaest() {
        return fvaest;
    }

    /**
     *
     * @param fvaest
     * The fvaest
     */
    public void setFvaest(Integer fvaest) {
        this.fvaest = fvaest;
    }

    /**
     *
     * @return
     * The fvanitcli
     */
    public String getFvanitcli() {
        return fvanitcli;
    }

    /**
     *
     * @param fvanitcli
     * The fvanitcli
     */
    public void setFvanitcli(String fvanitcli) {
        this.fvanitcli = fvanitcli;
    }

    /**
     *
     * @return
     * The fvacodcli
     */
    public Integer getFvacodcli() {
        return fvacodcli;
    }

    /**
     *
     * @param fvacodcli
     * The fvacodcli
     */
    public void setFvacodcli(Integer fvacodcli) {
        this.fvacodcli = fvacodcli;
    }

    /**
     *
     * @return
     * The fvadescli1
     */
    public String getFvadescli1() {
        return fvadescli1;
    }

    /**
     *
     * @param fvadescli1
     * The fvadescli1
     */
    public void setFvadescli1(String fvadescli1) {
        this.fvadescli1 = fvadescli1;
    }

    /**
     *
     * @return
     * The fvadescli2
     */
    public String getFvadescli2() {
        return fvadescli2;
    }

    /**
     *
     * @param fvadescli2
     * The fvadescli2
     */
    public void setFvadescli2(String fvadescli2) {
        this.fvadescli2 = fvadescli2;
    }

    /**
     *
     * @return
     * The fvastot
     */
    public Integer getFvastot() {
        return fvastot;
    }

    /**
     *
     * @param fvastot
     * The fvastot
     */
    public void setFvastot(Integer fvastot) {
        this.fvastot = fvastot;
    }

    /**
     *
     * @return
     * The fvaimpsi
     */
    public Integer getFvaimpsi() {
        return fvaimpsi;
    }

    /**
     *
     * @param fvaimpsi
     * The fvaimpsi
     */
    public void setFvaimpsi(Integer fvaimpsi) {
        this.fvaimpsi = fvaimpsi;
    }

    /**
     *
     * @return
     * The fvaimpeo
     */
    public Integer getFvaimpeo() {
        return fvaimpeo;
    }

    /**
     *
     * @param fvaimpeo
     * The fvaimpeo
     */
    public void setFvaimpeo(Integer fvaimpeo) {
        this.fvaimpeo = fvaimpeo;
    }

    /**
     *
     * @return
     * The fvaimptc
     */
    public Integer getFvaimptc() {
        return fvaimptc;
    }

    /**
     *
     * @param fvaimptc
     * The fvaimptc
     */
    public void setFvaimptc(Integer fvaimptc) {
        this.fvaimptc = fvaimptc;
    }

    /**
     *
     * @return
     * The fvasubtotal
     */
    public Integer getFvasubtotal() {
        return fvasubtotal;
    }

    /**
     *
     * @param fvasubtotal
     * The fvasubtotal
     */
    public void setFvasubtotal(Integer fvasubtotal) {
        this.fvasubtotal = fvasubtotal;
    }

    /**
     *
     * @return
     * The fvadesc
     */
    public Integer getFvadesc() {
        return fvadesc;
    }

    /**
     *
     * @param fvadesc
     * The fvadesc
     */
    public void setFvadesc(Integer fvadesc) {
        this.fvadesc = fvadesc;
    }

    /**
     *
     * @return
     * The fvatotal
     */
    public Integer getFvatotal() {
        return fvatotal;
    }

    /**
     *
     * @param fvatotal
     * The fvatotal
     */
    public void setFvatotal(Integer fvatotal) {
        this.fvatotal = fvatotal;
    }

    /**
     *
     * @return
     * The fvadebfis
     */
    public Integer getFvadebfis() {
        return fvadebfis;
    }

    /**
     *
     * @param fvadebfis
     * The fvadebfis
     */
    public void setFvadebfis(Integer fvadebfis) {
        this.fvadebfis = fvadebfis;
    }

    /**
     *
     * @return
     * The fvaccont
     */
    public String getFvaccont() {
        return fvaccont;
    }

    /**
     *
     * @param fvaccont
     * The fvaccont
     */
    public void setFvaccont(String fvaccont) {
        this.fvaccont = fvaccont;
    }

    /**
     *
     * @return
     * The fvaflim
     */
    public String getFvaflim() {
        return fvaflim;
    }

    /**
     *
     * @param fvaflim
     * The fvaflim
     */
    public void setFvaflim(String fvaflim) {
        this.fvaflim = fvaflim;
    }

    /**
     *
     * @return
     * The fvaalm
     */
    public Integer getFvaalm() {
        return fvaalm;
    }

    /**
     *
     * @param fvaalm
     * The fvaalm
     */
    public void setFvaalm(Integer fvaalm) {
        this.fvaalm = fvaalm;
    }

    /**
     *
     * @return
     * The fvanumi2
     */
    public Integer getFvanumi2() {
        return fvanumi2;
    }

    /**
     *
     * @param fvanumi2
     * The fvanumi2
     */
    public void setFvanumi2(Integer fvanumi2) {
        this.fvanumi2 = fvanumi2;
    }
}
