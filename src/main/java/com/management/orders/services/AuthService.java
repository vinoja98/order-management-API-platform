package com.management.orders.services;

import com.management.orders.components.RequestResponse;
import com.management.orders.components.SignInRequest;
import com.management.orders.components.SignUpRequest;
import com.management.orders.components.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {
    @Autowired
    public JwtService jwtService;
    private final Map<String, User> userCache = new ConcurrentHashMap<>();

    public User signUp(SignUpRequest request) {
        User user = new User(request.getEmail(), request.getPassword(), request.getFirstName(), request.getLastName());
        userCache.put(request.getEmail(), user);
        return user;
    }

    public RequestResponse signIn(SignInRequest request) {
        User user = userCache.get(request.getEmail());
        if (user != null && user.getPassword().equals(request.getPassword())) {
            String loginName = user.getEmail();
            String tokenParams = this.jwtService.generateToken(loginName);
            RequestResponse requestResponse = new RequestResponse();
            requestResponse.setToken(tokenParams);
            return requestResponse;
        }
        return null;
    }
}
