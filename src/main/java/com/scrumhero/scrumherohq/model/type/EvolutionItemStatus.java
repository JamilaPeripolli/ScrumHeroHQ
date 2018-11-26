package com.scrumhero.scrumherohq.model.type;

public enum EvolutionItemStatus {

    OPENED("OPENED"),
    CONCLUDED("CONCLUDED"),
    CLOSED("CLOSED");

    private String value;

    EvolutionItemStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
