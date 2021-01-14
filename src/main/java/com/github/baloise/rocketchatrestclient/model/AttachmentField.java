package com.github.baloise.rocketchatrestclient.model;

import com.fasterxml.jackson.annotation.JsonGetter;

public class AttachmentField {
    private boolean isShort = false;
    private String title, value;

    public void setShort(boolean isShort) {
        this.isShort = isShort;
    }

    @JsonGetter("short")
    public boolean isShort() {
        return this.isShort;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
