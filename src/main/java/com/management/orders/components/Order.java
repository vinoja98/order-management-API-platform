package com.management.orders.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public class Order {
    @Nonnull
    @JsonProperty("referenceNumber")
    private String referenceNumber;

    @Nonnull
    @JsonProperty("email")
    private String userEmail;

    @Nonnull
    @NotEmpty
    @JsonProperty("itemName")
    private String itemName;

    @Nonnull
    @Min(1)
    @JsonProperty("quantity")
    private Integer quantity;

    @Nonnull
    @NotEmpty
    @JsonProperty("shippingAddress")
    private String shippingAddress;

    private OrderStatus status = OrderStatus.NEW;

    @JsonProperty("placementTimestamp")
    private LocalDateTime placementTimestamp;

    public Order(@NonNull String referenceNumber, @NonNull String userEmail, @NonNull String itemName,
                 @NonNull Integer quantity, @NonNull String shippingAddress) {
        this.referenceNumber = referenceNumber;
        this.userEmail = userEmail;
        this.itemName = itemName;
        this.quantity = quantity;
        this.shippingAddress = shippingAddress;
        this.placementTimestamp = LocalDateTime.now(); // Set timestamp during creation
    }

    @NonNull
    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(@NonNull String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    @NonNull
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@NonNull String userEmail) {
        this.userEmail = userEmail;
    }

    @NonNull
    public String getItemName() {
        return itemName;
    }

    public void setItemName(@NonNull String itemName) {
        this.itemName = itemName;
    }

    @NonNull
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@NonNull Integer quantity) {
        this.quantity = quantity;
    }

    @NonNull
    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(@NonNull String shippingAddress) {
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