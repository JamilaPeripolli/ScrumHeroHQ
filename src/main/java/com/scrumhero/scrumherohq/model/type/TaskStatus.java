package com.scrumhero.scrumherohq.model.type;

public enum TaskStatus {

    CREATED("CREATED"),
    STARTED("STARTED"),
    BLOCKED("BLOCKED"),
    FINISHED("FINISHED");

    private String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
