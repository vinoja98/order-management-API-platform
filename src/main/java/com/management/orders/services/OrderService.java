package com.management.orders.services;

import com.management.orders.components.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

    public List<Order> getOrderHistory(String email, int page, int size) {
        return orderCacheService.getOrders(email)
                .sorted((o1, o2) -> {
                    try {
                        String ref1 = o1.getReferenceNumber();
                        String ref2 = o2.getReferenceNumber();

                        // Extract numbers from references
                        int num1 = extractNumber(ref1);
                        int num2 = extractNumber(ref2);

                        return Integer.compare(num2, num1);
                    } catch (Exception e) {
                        // Fall back to string comparison if parsing fails
                        return o2.getReferenceNumber().compareTo(o1.getReferenceNumber());
                    }
                })
                .skip((long) (page-1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    private int extractNumber(String reference) {
        try {
            return Integer.parseInt(reference.substring(reference.indexOf("-") + 1));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

//    @Scheduled(cron = "0 0 * * * *")
    @Scheduled(cron = "0 */2 * * * *")
    public void dispatchPendingOrders() {
        System.out.println("Dispatch Started");
        orderCacheService.getOrders()
                .filter(order -> order.getStatus() == OrderStatus.NEW)
                .forEach(order -> order.setStatus(OrderStatus.DISPATCHED));
    }

    private String generateOrderReferenceNumber() {
        return "ORD-" + orderReferenceCounter++;
    }
}