package com.luisbar.waterdelivery.common.eventbus;

public class UserLoggedP {
    private String name;

    public UserLoggedP(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
