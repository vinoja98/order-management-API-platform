package com.management.orders.services;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.management.orders.components.User;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Service
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserCacheService {
    private final Cache<String, User> userCache;

    public UserCacheService() {
        this.userCache = CacheBuilder.newBuilder()
                .expireAfterAccess(24, TimeUnit.HOURS)  // Cache entries expire after 24 hours of inactivity
                .maximumSize(10000)  // Maximum number of entries
                .build();
    }

    public void addUser(User user) {
        if (user != null && user.getEmail() != null) {
            userCache.put(user.getEmail(), user);
        }
    }

    public Optional<User> getUser(String email) {
        return Optional.ofNullable(userCache.getIfPresent(email));
    }

    public void removeUser(String email) {
        userCache.invalidate(email);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userCache.asMap().values());
    }

    public boolean exists(String email) {
        return userCache.getIfPresent(email) != null;
    }

    public void clearCache() {
        userCache.invalidateAll();
    }

    public Map<String, User> getCacheSnapshot() {
        return userCache.asMap();
    }
}