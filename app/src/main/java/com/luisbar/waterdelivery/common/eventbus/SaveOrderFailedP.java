package com.luisbar.waterdelivery.common.eventbus;

public class SaveOrderFailedP {

    private Object mObject;

    public SaveOrderFailedP(Object mObject) {
        this.mObject = mObject;
    }

    public Object getmObject() {
        return mObject;
    }

    public void setmObject(Object mObject) {
        this.mObject = mObject;
    }
}
