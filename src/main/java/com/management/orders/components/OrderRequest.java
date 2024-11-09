package com.management.orders.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;


public class OrderRequest {
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
}
