package com.github.baloise.rocketchatrestclient.requests;

public class RoomAndUserRequest {
    private String roomId, userId;
    
    public RoomAndUserRequest(String roomId, String userId) {
        this.roomId = roomId;
        this.userId = userId;
    }
    
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    
    public String getRoomId() {
        return this.roomId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUserId() {
        return this.userId;
    }
}
