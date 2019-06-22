package com.luisbar.waterdelivery.common.eventbus;

import org.greenrobot.eventbus.EventBus;

public class EventBusMask {

    public static void subscribe(Object object) {
        if (!EventBus.getDefault().isRegistered(object))
            EventBus.getDefault().register(object);
    }

    public static void unsubscribe(Object object) {
        EventBus.getDefault().unregister(object);
    }

    public static void post(Object object) {
        EventBus.getDefault().post(object);
    }
}
