package com.luisbar.waterdelivery.common.eventbus;

public class SignedInFailedP {

    private Object error;

    public SignedInFailedP() {
    }

    public SignedInFailedP(Object error) {
        this.error = error;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}
