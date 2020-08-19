package com.shop.shoppify.dto.response;

import com.shop.shoppify.model.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseItemDTO {

    private int id;

    private String genre;

    private String name;

    private double price;

    private int categoryId;

    private String categoryName;

    private boolean isDeleted;

    public ProductResponseItemDTO(Product product) {
        this.id = product.getId();
        this.genre = product.getGender();
        this.name = product.getName();
        this.price = product.getPrice();
        this.categoryName = product.getCategory().getName();
        this.categoryId = product.getCategory().getId();
        this.isDeleted = product.getIsDeleted();
    }

}
