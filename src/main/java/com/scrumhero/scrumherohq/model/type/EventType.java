package com.scrumhero.scrumherohq.model.type;

public enum EventType {

    MISSION_START("MISSION_START"),
    MISSION_END("MISSION_END"),
    DAILY_CHALLENGE("DAILY_CHALLENGE"),
    EVOLUTION_TIME("EVOLUTION_TIME");

    private String value;

    EventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
