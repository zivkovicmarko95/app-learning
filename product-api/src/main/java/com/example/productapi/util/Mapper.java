package com.example.productapi.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.productapi.dto.CategoryDTO;
import com.example.productapi.dto.ProductDTO;
import com.example.productapi.models.Category;
import com.example.productapi.models.Product;

@Component
public class Mapper {

    public ProductDTO convertProductToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        CategoryDTO categoryDTO = new CategoryDTO();
        
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());

        if (product.getCategory() != null) {
            categoryDTO.setName(product.getCategory().getName());
            categoryDTO.setDescription(product.getCategory().getDescription());
            
            dto.setCategory(categoryDTO);
        }

        return dto;
    }

    public List<ProductDTO> convertProductToProductDTO(List<Product> products) {
        List<ProductDTO> dtos = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            dtos.add(convertProductToProductDTO(products.get(i)));
        }

        return dtos;
    }

	public Product convertDtoToProduct(ProductDTO productDTO) {
        Product product = new Product();
        
        product.setId(null);
        product.setTitle(productDTO.getTitle());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setCategory(new Category(productDTO.getCategory().getName(), productDTO.getCategory().getDescription()));
		
        return product;
	}

}
