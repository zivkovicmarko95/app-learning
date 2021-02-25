package productapi.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Order {
    
    /*
        Order object reprsents order and it is created when user buy one or more
        products. It has order id, userId and the products and quantities which are bought 
        by the user
    */

    @Id
    private String id;
    private String userId;
    private List<OrderedProduct> orderedProducts = new ArrayList<>();

    public Order(String userId, List<OrderedProduct> orderedProducts) {
        this.userId = userId;
        this.orderedProducts = orderedProducts;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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
        if (!(o instanceof Order)) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(userId, order.userId) && Objects.equals(orderedProducts, order.orderedProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, orderedProducts);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", orderedProducts='" + getOrderedProducts() + "'" +
            "}";
    }

}
