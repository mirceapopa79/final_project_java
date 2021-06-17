package com.sda.javaremote18.spring_boot.models.auth;

import com.sda.javaremote18.spring_boot.utils.Utils;

public class RegisterModel {
    private String email;
    private String password;
    private String retypePassword;
    private String firstName;
    private String lastName;

    public boolean isValid() {
        int counter = 0;
        if(!Utils.isValidEmail(this.email)){
            counter++;
        }
        if(!(Utils.isValidString(this.password) && Utils.isValidString(retypePassword) && this.password.equals(retypePassword))){
            counter++;
        }
        if(!(Utils.isValidString(firstName) && Utils.isValidString(lastName))){
            counter++;
        }
        return counter == 0;
    }

    public String getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
