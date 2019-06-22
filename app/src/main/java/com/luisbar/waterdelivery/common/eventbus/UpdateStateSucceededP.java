package com.luisbar.waterdelivery.common.eventbus;

public class UpdateStateSucceededP {

    private Object object;

    public UpdateStateSucceededP(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
