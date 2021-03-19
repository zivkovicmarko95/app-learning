package com.example.orderbackupapi.models;

import java.util.Objects;

public class OrderedProduct {
    
    /*
        This class is used as list of ordered products with product ids and quantities
    */

    private String productId;
    private int qty;

    public OrderedProduct() {
    }

    public OrderedProduct(String productId, int qty) {
        this.productId = productId;
        this.qty = qty;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQty() {
        return this.qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public OrderedProduct productId(String productId) {
        setProductId(productId);
        return this;
    }

    public OrderedProduct qty(int qty) {
        setQty(qty);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof OrderedProduct)) {
            return false;
        }
        OrderedProduct orderedProduct = (OrderedProduct) o;
        return Objects.equals(productId, orderedProduct.productId) && qty == orderedProduct.qty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, qty);
    }

    @Override
    public String toString() {
        return "{" +
            " productId='" + getProductId() + "'" +
            ", qty='" + getQty() + "'" +
            "}";
    }

}
