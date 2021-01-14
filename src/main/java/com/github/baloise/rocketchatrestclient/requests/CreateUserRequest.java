package com.github.baloise.rocketchatrestclient.requests;

public class CreateUserRequest {
    private String email, name, password, username;
    private boolean verified;
    private String[] roles;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public CreateUserRequest(String email, String name, String password, String username, boolean verified, String[] roles) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.username = username;
        this.verified = verified;
        this.roles = roles;
    }


}
