package com.github.baloise.rocketchatrestclient.model;

public class Group extends Room {
    public Group() {
        super();
        this.setType(RoomType.PRIVATE_GROUP);
    }
    
    public Group(String name) {
        super();
        this.setType(RoomType.PRIVATE_GROUP);
        this.setName(name);
    }
    
    public Group(String id, String name) {
        super();
        this.setType(RoomType.PRIVATE_GROUP);
        this.setId(id);
        this.setName(name);
    }

    public Group(String name,String[] usernames) {
        super();
        this.setType(RoomType.PRIVATE_GROUP);
        this.setName(name);
        this.setUsernames(usernames);
    }
}
