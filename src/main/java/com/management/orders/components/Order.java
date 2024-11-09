package com.management.orders.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class Order {
    @NotNull
    @JsonProperty("referenceNumber")
    private String referenceNumber;

    @NotNull
    @JsonProperty("email")
    private String userEmail;

    @NotNull
    @NotEmpty
    @JsonProperty("itemName")
    private String itemName;

    @NotNull
    @Min(1)
    @JsonProperty("quantity")
    private Integer quantity;

    @NotNull
    @NotEmpty
    @JsonProperty("shippingAddress")
    private String shippingAddress;

    private OrderStatus status = OrderStatus.NEW;

    @JsonProperty("placementTimestamp")
    private LocalDateTime placementTimestamp;

    public Order(String referenceNumber, String userEmail,  String itemName,
                  Integer quantity,  String shippingAddress) {
        this.referenceNumber = referenceNumber;
        this.userEmail = userEmail;
        this.itemName = itemName;
        this.quantity = quantity;
        this.shippingAddress = shippingAddress;
        this.placementTimestamp = LocalDateTime.now(); // Set timestamp during creation
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber( String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail( String userEmail) {
        this.userEmail = userEmail;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName( String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity( Integer quantity) {
        this.quantity = quantity;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress( String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getPlacementTimestamp() {
        return placementTimestamp;
    }

    public void setPlacementTimestamp(LocalDateTime placementTimestamp) {
        this.placementTimestamp = placementTimestamp;
    }
}