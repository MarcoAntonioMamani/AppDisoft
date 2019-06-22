package com.luisbar.waterdelivery.domain.model;

public class UserModel {

    private String code;
    private String identityCard;

    public UserModel() {
    }

    public UserModel(String code, String identityCard) {
        this.code = code;
        this.identityCard = identityCard;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }
}
