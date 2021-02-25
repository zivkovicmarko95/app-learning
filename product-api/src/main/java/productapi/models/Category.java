package productapi.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Category {
    
    /*
        Class which is used for representing Category which has id, name, description and product ids for the category
    */

    @Id
    private String id;
    private String name;
    private String description;
    private List<String> productIds = new ArrayList<>();

    public Category() {
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Category(String name, String description, List<String> productIds) {
        this.name = name;
        this.description = description;
        this.productIds = productIds;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getProductIds() {
        return this.productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    public Category id(String id) {
        setId(id);
        return this;
    }

    public Category name(String name) {
        setName(name);
        return this;
    }

    public Category description(String description) {
        setDescription(description);
        return this;
    }

    public Category productIds(List<String> productIds) {
        setProductIds(productIds);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Category)) {
            return false;
        }
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(description, category.description) && Objects.equals(productIds, category.productIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, productIds);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", productIds='" + getProductIds() + "'" +
            "}";
    }

    public void add(String productId) {
        productIds.add(productId);
    }
    
}
