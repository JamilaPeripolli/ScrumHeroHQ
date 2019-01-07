package com.scrumhero.scrumherohq.model.type;

public enum PlayerCategory {

    HERO("HERO"),
    MASTER("MASTER"),
    ORACLE("ORACLE");

    private String value;

    PlayerCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
