package com.github.baloise.rocketchatrestclient.model;

public class Channel extends Room {
    public Channel() {
        super();
        this.setType(RoomType.PUBLIC_CHANNEL);
    }
    
    public Channel(String name) {
        super();
        this.setType(RoomType.PUBLIC_CHANNEL);
        this.setName(name);
    }
    
    public Channel(String id, String name) {
        super();
        this.setType(RoomType.PUBLIC_CHANNEL);
        this.setId(id);
        this.setName(name);
    }
}
