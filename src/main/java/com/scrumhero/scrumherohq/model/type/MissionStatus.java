package com.scrumhero.scrumherohq.model.type;

public enum MissionStatus {

    CREATED("CREATED"),
    STARTED("STARTED"),
    FINISHED("FINISHED");

    private String value;

    MissionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
