package com.scrumhero.scrumherohq.model.type;

public enum EvolutionItemStatus {

    PENDING("PENDING"),
    DONE("DONE"),
    CANCELED("CANCELED");

    private String value;

    EvolutionItemStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
