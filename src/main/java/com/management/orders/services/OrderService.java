package com.management.orders.services;

import com.management.orders.components.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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


    public Order createOrder(OrderRequest request,String email) {
        return userCacheService.getUser(email)
                .map(user -> {
                    String referenceNumber = generateOrderReferenceNumber();
                    Order order = new Order(
                            referenceNumber,
                            email,
                            request.getItemName(),
                            request.getQuantity(),
                            request.getShippingAddress());
                    orderCacheService.addOrder(order);
                    return order;
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    }

    public Order cancelOrder(String email,String referenceNumber) {
        return orderCacheService.getOrders(email)
                .filter(order -> order.getReferenceNumber().equals(referenceNumber))
                .findFirst()
                .map(order -> {
                    if (order.getStatus() == OrderStatus.NEW) {
                        order.setStatus(OrderStatus.CANCELLED);
                        return order;
                    } else {
                        throw new IllegalStateException("Order cannot be canceled as it is not in NEW status");
                    }
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or reference number"));
    }

    public List<Order> getOrderHistory(String email,int page, int size) {
        return orderCacheService.getOrders(email)
                .skip((long) (page-1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 0 * * * *")
//    @Scheduled(cron = "0 */2 * * * *")
    public void dispatchPendingOrders() {
        System.out.println("Dispatch Started");
        orderCacheService.getOrders()
                .filter(order -> order.getStatus() == OrderStatus.NEW)
                .forEach(order -> {
                    order.setStatus(OrderStatus.DISPATCHED);
                });
    }

    private String generateOrderReferenceNumber() {
        return "ORD-" + orderReferenceCounter++;
    }
}