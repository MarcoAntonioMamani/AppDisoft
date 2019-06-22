package com.luisbar.waterdelivery.common.eventbus;

public class SaveOrderSucceededP {

    private Object mObject;

    public SaveOrderSucceededP(Object mObject) {
        this.mObject = mObject;
    }

    public Object getmObject() {
        return mObject;
    }

    public void setmObject(Object mObject) {
        this.mObject = mObject;
    }
}
