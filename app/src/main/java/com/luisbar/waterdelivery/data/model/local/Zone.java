package com.luisbar.waterdelivery.data.model.local;

import io.realm.RealmObject;

public class Zone extends RealmObject {

    private int zone;

    public Zone() {

    }

    public Zone(int zone) {
        this.zone = zone;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return zone + "";
    }
}
