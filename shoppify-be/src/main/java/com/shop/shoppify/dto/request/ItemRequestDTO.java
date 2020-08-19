package com.shop.shoppify.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemRequestDTO {

    private int productId;

    private int quantity;

    private int sizeId;

}
