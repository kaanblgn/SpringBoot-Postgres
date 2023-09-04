package com.sekom.banking.enums;

public enum EntityType {
    USER("User"),
    BANK("Bank"),
    ACCOUNT("Account"),
    TRANSACTION("Transaction");

    private final String typeName;

    EntityType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
