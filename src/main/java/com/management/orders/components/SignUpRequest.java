package com.management.orders.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class SignUpRequest {
    @NotNull
    @JsonProperty("email")
    private String email;
    @NotNull
    @JsonProperty("password")
    private String password;
    @NotNull
    @JsonProperty("firstName")
    private String firstName;
    @NotNull
    @JsonProperty("lastName")
    private String lastName;

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
