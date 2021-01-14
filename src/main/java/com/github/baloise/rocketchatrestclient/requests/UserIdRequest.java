package com.github.baloise.rocketchatrestclient.requests;

public class UserIdRequest {
    private String username, userId;

    public UserIdRequest(String userName, String userId) {
        this.username = userName;
        this.userId = userId;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUserId() {
        return this.userId;
    }
}
