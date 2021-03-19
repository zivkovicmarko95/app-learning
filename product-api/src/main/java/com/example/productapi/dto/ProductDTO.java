package com.example.productapi.dto;

import java.util.Objects;

public class ProductDTO {

    /*
        This class is used as Product Data Trasnsfer Object
    */

    private String id;
    private String title;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private CategoryDTO category;

    public ProductDTO() {
    }

    public ProductDTO(String id, String title, String name, String description, double price, int quantity, CategoryDTO category) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CategoryDTO getCategory() {
        return this.category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public ProductDTO id(String id) {
        setId(id);
        return this;
    }

    public ProductDTO title(String title) {
        setTitle(title);
        return this;
    }

    public ProductDTO name(String name) {
        setName(name);
        return this;
    }

    public ProductDTO description(String description) {
        setDescription(description);
        return this;
    }

    public ProductDTO price(double price) {
        setPrice(price);
        return this;
    }

    public ProductDTO quantity(int quantity) {
        setQuantity(quantity);
        return this;
    }

    public ProductDTO category(CategoryDTO category) {
        setCategory(category);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProductDTO)) {
            return false;
        }
        ProductDTO productDTO = (ProductDTO) o;
        return Objects.equals(id, productDTO.id) && Objects.equals(title, productDTO.title) 
                && Objects.equals(name, productDTO.name) && Objects.equals(description, productDTO.description) 
                && price == productDTO.price && quantity == productDTO.quantity 
                && Objects.equals(category, productDTO.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, name, description, price, quantity, category);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", price='" + getPrice() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
    
}
