package com.luisbar.waterdelivery.common.eventbus;

public class SignedInSucceededV {

    private Object result;

    public SignedInSucceededV() {
    }

    public SignedInSucceededV(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
