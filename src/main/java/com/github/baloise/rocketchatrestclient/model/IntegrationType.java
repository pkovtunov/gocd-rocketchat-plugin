package com.github.baloise.rocketchatrestclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum IntegrationType {
    @JsonProperty("webhook-outgoing")
    OUTGOING_WEBHOOK("webhook-outgoing"),
    
    @JsonProperty("webhook-incoming")
    INCOMING_WEBHOOK("webhook-incoming");
    
    private String name;
    private IntegrationType(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}
