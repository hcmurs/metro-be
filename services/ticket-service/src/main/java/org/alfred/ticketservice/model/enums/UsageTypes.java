package org.alfred.ticketservice.model.enums;

public enum UsageTypes {
    ENTRY("entry"),
    EXIT("exit");

    private final String type;

    UsageTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
