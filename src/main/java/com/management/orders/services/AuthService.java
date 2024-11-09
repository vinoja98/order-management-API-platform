package com.management.orders.services;

import com.management.orders.components.LoginResponse;
import com.management.orders.components.SignInRequest;
import com.management.orders.components.SignUpRequest;
import com.management.orders.components.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    public JwtService jwtService;
    @Autowired
    UserCacheService userCacheService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User signUp(SignUpRequest request) {
        // Validate if user already exists
        if (userCacheService.exists(request.getEmail())) {
            throw new IllegalArgumentException("User already exists with email: " + request.getEmail());
        }
        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFirstName(),
                request.getLastName());
        userCacheService.addUser(user);
        return user;
    }

    public LoginResponse signIn(SignInRequest request) {
        return userCacheService.getUser(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(user -> {
                    String token = jwtService.generateToken(user.getEmail());
                    LoginResponse response = new LoginResponse();
                    response.setToken(token);
                    return response;
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    }
}
