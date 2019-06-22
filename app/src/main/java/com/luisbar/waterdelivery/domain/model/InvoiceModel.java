package com.luisbar.waterdelivery.domain.model;

import java.util.List;

public class InvoiceModel {

    private int id;
    private String identityCard;
    private String amount;
    private String clientName;
    private String idrepa;
    private List products;

    public InvoiceModel(int id, String identityCard, String amount, String clientName, String idrepa, List products) {
        this.id = id;
        this.identityCard = identityCard;
        this.amount = amount;
        this.clientName = clientName;
        this.idrepa=idrepa;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public  String getIdrepa(){return  this.idrepa;}

    public  void setIdrepa(String idrepa){this.idrepa=idrepa;}

    public List getProducts() {
        return products;
    }

    public void setProducts(List products) {
        this.products = products;
    }
}
