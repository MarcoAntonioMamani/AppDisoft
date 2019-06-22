package com.luisbar.waterdelivery.common.eventbus;

public class SignedInFailedV {

    private Object error;

    public SignedInFailedV() {
    }

    public SignedInFailedV(Object error) {
        this.error = error;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}
