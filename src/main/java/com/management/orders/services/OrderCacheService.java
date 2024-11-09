package com.management.orders.services;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.management.orders.components.Order;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class OrderCacheService {
    private final Cache<String, Order> orderCache;

    public OrderCacheService() {
        this.orderCache = CacheBuilder.newBuilder()
                .expireAfterAccess(1, TimeUnit.HOURS)
                .build();
    }

    public void addOrder(Order order) {
        if (order != null && order.getReferenceNumber() != null) {
            orderCache.put(order.getReferenceNumber(), order);
        }
    }

    public Optional<Order> getOrder(String referenceNumber) {
        return Optional.ofNullable(orderCache.getIfPresent(referenceNumber));
    }

    public boolean exists(String referenceNumber) {
        return orderCache.getIfPresent(referenceNumber) != null;
    }
}
