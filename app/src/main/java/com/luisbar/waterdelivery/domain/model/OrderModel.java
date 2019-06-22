package com.luisbar.waterdelivery.domain.model;

import java.util.List;

public class OrderModel {

    private String clientId;
    private String date;
    private String Observation;
    private List products;
    private Double total;

    public OrderModel(String clientId, String date, String observation, List products, Double total) {
        this.clientId = clientId;
        this.date = date;
        Observation = observation;
        this.products = products;
        this.total = total;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getObservation() {
        return Observation;
    }

    public void setObservation(String observation) {
        Observation = observation;
    }

    public List getProducts() {
        return products;
    }

    public void setProducts(List products) {
        this.products = products;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
