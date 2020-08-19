package com.shop.shoppify.dto.response;

import com.shop.shoppify.model.ProductSize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSizeResponseDTO {

    private int sizeId;

    private String size;

    private int count;

    public ProductSizeResponseDTO(ProductSize productSize) {
        this.sizeId = productSize.getSize().getId();
        this.size = productSize.getSize().getValue();
        this.count = productSize.getCount();
    }

}