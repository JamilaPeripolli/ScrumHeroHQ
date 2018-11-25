package com.scrumhero.scrumherohq.model.type;

public enum AuthorityType {

    USER("USER"),
    ADMIN("ADMIN");

    private String value;

    AuthorityType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}