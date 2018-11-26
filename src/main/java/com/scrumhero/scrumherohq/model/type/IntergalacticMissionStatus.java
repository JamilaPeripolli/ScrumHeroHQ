package com.scrumhero.scrumherohq.model.type;

public enum IntergalacticMissionStatus {

    CREATED("CREATED"),
    STARTED("STARTED"),
    FINISHED("FINISHED");

    private String value;

    IntergalacticMissionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}