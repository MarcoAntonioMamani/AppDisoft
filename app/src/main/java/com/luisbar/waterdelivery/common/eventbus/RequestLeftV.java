package com.luisbar.waterdelivery.common.eventbus;

public class RequestLeftV {

    private Object object;

    public RequestLeftV(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
