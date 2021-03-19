package com.example.orderbackupapi.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BackupOrder {

    /*
     * Backup Order object represents the same object which exist in the product-api
     * component
     */

    @Id
    private String id;
    private String orderId;
    private String userId;
    private List<OrderedProduct> orderedProducts = new ArrayList<>();
    private Date orderCreated;

    public BackupOrder() {
        this.orderCreated = new Date();
    }

    public BackupOrder(String orderId, String userId, List<OrderedProduct> orderedProducts) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderedProducts = orderedProducts;
        this.orderCreated = new Date();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return this.orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public Date getOrderCreated() {
        return this.orderCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BackupOrder)) {
            return false;
        }
        BackupOrder backupOrder = (BackupOrder) o;
        return Objects.equals(id, backupOrder.id) && Objects.equals(orderId, backupOrder.orderId)
                && Objects.equals(userId, backupOrder.userId)
                && Objects.equals(orderedProducts, backupOrder.orderedProducts)
                && Objects.equals(orderCreated, backupOrder.orderCreated);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", orderId='" + getOrderId() + "'" + ", userId='" + getUserId() + "'"
                + ", orderedProducts='" + getOrderedProducts() + "'" + ", orderCreated='" + getOrderCreated() + "'"
                + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, userId, orderedProducts, orderCreated);
    }

    public void addProduct(OrderedProduct orderedProduct) {
        orderedProducts.add(orderedProduct);
    }

}
