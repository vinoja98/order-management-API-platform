package com.management.orders.components;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignInRequest {
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;

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
