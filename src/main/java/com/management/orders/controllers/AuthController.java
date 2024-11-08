package com.management.orders.controllers;

import com.management.orders.components.SignInRequest;
import com.management.orders.components.SignUpRequest;
import com.management.orders.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("signup")
    public Object signup(@RequestBody SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }
    @PostMapping("login")
    public Object getToken(@RequestBody SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }
}
