package com.shop.shoppify.dto;

import com.shop.shoppify.model.Brand;
import com.shop.shoppify.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IdNameDTO {

    private int id;

    private String name;

    public  IdNameDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public  IdNameDTO(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
    }
}
