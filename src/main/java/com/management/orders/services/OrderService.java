package com.management.orders.services;

import com.management.orders.components.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private long orderReferenceCounter = 1;
    @Autowired
    UserCacheService userCacheService;
    @Autowired
    OrderCacheService orderCacheService;


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
                    orderCacheService.addOrder(order);
                    return order;
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    }

//    public Order cancelOrder(String referenceNumber) {
//        Order order = orderCacheService.getIfPresent(referenceNumber);
//        if (order != null && order.getStatus() == OrderStatus.NEW) {
//            order.setStatus(OrderStatus.CANCELLED);
//            orderCache.put(referenceNumber, order);
//        }
//        return order;
//    }

    public List<Order> getOrderHistory(String email,int page, int size) {
        return orderCacheService.getOrders(email)
                .skip((long) (page-1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }

//    public void dispatchPendingOrders() {
//        orderCache.asMap().values().stream()
//                .filter(order -> order.getStatus() == OrderStatus.NEW)
//                .forEach(order -> {
//                    order.setStatus(OrderStatus.DISPATCHED);
//                    orderCache.put(order.getReferenceNumber(), order);
//                });
//    }

    private String generateOrderReferenceNumber() {
        return "ORD-" + orderReferenceCounter++;
    }
}