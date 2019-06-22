package com.luisbar.waterdelivery.common.eventbus;

public class SignedInSucceededP {

    private Object result;

    public SignedInSucceededP() {
    }

    public SignedInSucceededP(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
