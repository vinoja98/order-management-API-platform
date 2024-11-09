package com.management.orders.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public class OrderRequest {
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
}
