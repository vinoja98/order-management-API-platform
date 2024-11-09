package com.management.orders.services;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.management.orders.components.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final Cache<String, Order> orderCache;
    private long orderReferenceCounter = 1;
    @Autowired
    UserCacheService userCacheService;

    public OrderService() {
        this.orderCache = CacheBuilder.newBuilder()
                .expireAfterAccess(1, TimeUnit.HOURS)
                .build();
    }

    public Order createOrder(OrderRequest request) {
        return userCacheService.getUser(request.getUserEmail())
                .map(user -> {
                    String referenceNumber = generateOrderReferenceNumber();
                    Order order = new Order(
                            referenceNumber,
                            user.getEmail(),
                            request.getItemName(),
                            request.getQuantity(),
                            request.getShippingAddress());
                    orderCache.put(referenceNumber, order);
                    return order;
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    }

    public Order cancelOrder(String referenceNumber) {
        Order order = orderCache.getIfPresent(referenceNumber);
        if (order != null && order.getStatus() == OrderStatus.NEW) {
            order.setStatus(OrderStatus.CANCELLED);
            orderCache.put(referenceNumber, order);
        }
        return order;
    }

    public List<Order> getOrderHistory(int page, int size) {
        List<Order> orders = new ArrayList<>(orderCache.asMap().values());
        return orders.stream()
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    public void dispatchPendingOrders() {
        orderCache.asMap().values().stream()
                .filter(order -> order.getStatus() == OrderStatus.NEW)
                .forEach(order -> {
                    order.setStatus(OrderStatus.DISPATCHED);
                    orderCache.put(order.getReferenceNumber(), order);
                });
    }

    private String generateOrderReferenceNumber() {
        return "ORD-" + orderReferenceCounter++;
    }
}