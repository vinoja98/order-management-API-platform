package com.management.orders.services;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.management.orders.components.Order;
import com.management.orders.components.OrderRequest;
import com.management.orders.components.OrderStatus;
import com.management.orders.components.User;
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
    private final Map<Long, User> userCache;
    private long orderReferenceCounter = 1;

    public OrderService() {
        this.orderCache = CacheBuilder.newBuilder()
                .expireAfterAccess(1, TimeUnit.HOURS)
                .build();
        this.userCache = new ConcurrentHashMap<>();
    }

//    public Order createOrder(OrderRequest request) {
//        User user = userCache.computeIfAbsent(request.getUserId(), id -> new User(id, request.getUserEmail()));
//        String referenceNumber = generateOrderReferenceNumber();
//        Order order = new Order(referenceNumber, user, request.getItemName(), request.getQuantity(), request.getShippingAddress());
//        orderCache.put(referenceNumber, order);
//        return order;
//    }

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