package com.management.orders.components;

public class Order {
    private String referenceNumber;
    private User user;
    private String itemName;
    private Integer quantity;
    private String shippingAddress;
    private OrderStatus status = OrderStatus.NEW;

    public Order(String referenceNumber, User user, String itemName, Integer quantity, String shippingAddress) {
        this.referenceNumber = referenceNumber;
        this.user = user;
        this.itemName = itemName;
        this.quantity = quantity;
        this.shippingAddress = shippingAddress;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}