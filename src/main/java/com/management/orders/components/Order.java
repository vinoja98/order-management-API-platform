package com.management.orders.components;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {
    @JsonProperty("referenceNumber")
    private String referenceNumber;
    @JsonProperty("email")
    private String userEmail;

    @JsonProperty("itemName")
    private String itemName;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("shippingAddress")
    private String shippingAddress;
    private OrderStatus status = OrderStatus.NEW;

    public Order(String referenceNumber, String userEmail, String itemName, Integer quantity, String shippingAddress) {
        this.referenceNumber = referenceNumber;
        this.userEmail = userEmail;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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