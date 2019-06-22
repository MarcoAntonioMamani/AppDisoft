package com.luisbar.waterdelivery.common.eventbus;

public class SaveOrderSucceededV {

    private Object mObject;

    public SaveOrderSucceededV(Object mObject) {
        this.mObject = mObject;
    }

    public Object getmObject() {
        return mObject;
    }

    public void setmObject(Object mObject) {
        this.mObject = mObject;
    }
}
