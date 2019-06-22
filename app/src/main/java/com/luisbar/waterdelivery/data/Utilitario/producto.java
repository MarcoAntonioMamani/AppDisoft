package com.luisbar.waterdelivery.data.Utilitario;

/**
 * Created by usuario on 11/07/2018.
 */

public class producto {
    private int id;
    private String name;
    private String price;
private double cant;

    public producto(int id, String name, String price, double cant) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.cant = cant;
    }

    public double getCant() {
        return cant;
    }

    public void setCant(double cant) {
        this.cant = cant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
