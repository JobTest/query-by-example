package com.luxoft.alfabank2.domain;

public enum ClientStatus {
    IMPORTED,
    IDENTIFIED,
    PROFILE_CREATED,
    REGISTERED;

    public String localizedName() {
        return "ClientStatus." + name().toLowerCase();
    }

}
