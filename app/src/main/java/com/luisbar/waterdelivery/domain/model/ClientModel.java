package com.luisbar.waterdelivery.domain.model;

import com.google.android.gms.maps.model.LatLng;

public class ClientModel {

    private String name;
    private String clientNit;
    private String reason;
    private String nit;
    private String address;
    private String phone;
    private LatLng location;

    public ClientModel(String name, String clientNit, String reason, String nit, String address, String phone, LatLng location) {
        this.name = name;
        this.clientNit = clientNit;
        this.reason = reason;
        this.nit = nit;
        this.address = address;
        this.phone = phone;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getClientNit() {
        return clientNit;
    }

    public void setClientNit(String clientNit) {
        this.clientNit = clientNit;
    }
}
