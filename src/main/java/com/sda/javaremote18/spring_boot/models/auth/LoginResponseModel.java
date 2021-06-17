package com.sda.javaremote18.spring_boot.models.auth;

import com.sda.javaremote18.spring_boot.models.UserModel;

public class LoginResponseModel {
    private String token;
    private UserModel user;

    public LoginResponseModel(UserModel user) {
        this.user = user;
        this.token = "";
    }

    public LoginResponseModel(String token, UserModel user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

}
