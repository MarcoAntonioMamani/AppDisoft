package com.luisbar.waterdelivery.common.eventbus;

public class UpdateStateSucceededV {

    private Object object;

    public UpdateStateSucceededV(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
