package com.luxoft.alfabank.domain;

//import lime.i18n.Resources;

public enum ClientStatus {
    IMPORTED,
    IDENTIFIED,
    PROFILE_CREATED,
    REGISTERED;

    public String localizedName() {
//        return Resources.getText("ClientStatus." + name().toLowerCase());
        return "ClientStatus." + name().toLowerCase();
    }

}