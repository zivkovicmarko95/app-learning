package orderbackupapi.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BackupOrder {
    
    /*
        Backup Order object represents the same object which exist in the product-api component
    */

    @Id
    private String id;
    private String orderId;
    private String userId;
    private List<OrderedProduct> orderedProducts = new ArrayList<>();


    public BackupOrder() {
    }

    public BackupOrder(String orderId, String userId, List<OrderedProduct> orderedProducts) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderedProducts = orderedProducts;
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BackupOrder)) {
            return false;
        }
        BackupOrder backupOrder = (BackupOrder) o;
        return Objects.equals(id, backupOrder.id) && Objects.equals(orderId, backupOrder.orderId) && Objects.equals(userId, backupOrder.userId) && Objects.equals(orderedProducts, backupOrder.orderedProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, userId, orderedProducts);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", orderId='" + getOrderId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", orderedProducts='" + getOrderedProducts() + "'" +
            "}";
    }


    public void addProduct(OrderedProduct orderedProduct) {
        orderedProducts.add(orderedProduct);
    }

}
