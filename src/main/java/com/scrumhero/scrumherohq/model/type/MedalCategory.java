package com.scrumhero.scrumherohq.model.type;

public enum MedalCategory {

    GOLD("GOLD"),
    SILVER("SILVER"),
    BRONZE("BRONZE");

    private String value;

    MedalCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
