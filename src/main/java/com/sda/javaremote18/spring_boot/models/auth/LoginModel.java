package com.sda.javaremote18.spring_boot.models.auth;

import com.sda.javaremote18.spring_boot.utils.Utils;

public class LoginModel {
    private String email;
    private String password;

    public boolean isValid(){
        int counter = 0;
        if(!Utils.isValidEmail(this.email)){
            counter++;
        }
        if(!Utils.isValidString(this.password)){
            counter++;
        }

        return counter == 0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
