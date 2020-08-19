package com.shop.shoppify.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequestDTO {

    private String gender;

    private String name;

    private double price;

    private int categoryId;

    private int brandId;

    private List<ProductSizeDTO> sizes;
}