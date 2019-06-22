package com.luisbar.waterdelivery.common.eventbus;

public class RequestLeftP {

    private Object object;

    public RequestLeftP(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
