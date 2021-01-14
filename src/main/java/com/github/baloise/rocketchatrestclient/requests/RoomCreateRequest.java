package com.github.baloise.rocketchatrestclient.requests;

public class RoomCreateRequest {
    private String name;
    private String[] members;

    public RoomCreateRequest(String name, String[] members) {
        this.name = name;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getMembers() {
        return members;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }
}
