package com.luisbar.waterdelivery.common.eventbus;

public class UserLoggedV {
    private String name;

    public UserLoggedV(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
