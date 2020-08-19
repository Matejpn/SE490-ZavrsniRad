package com.shop.shoppify.dto.response;

import com.shop.shoppify.model.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemResponseDTO {

    private int id;

    private ProductResponseItemDTO product;

    private int quantity;

    private String size;

    public ItemResponseDTO(Item item) {
        this.id = item.getId();
        this.product = new ProductResponseItemDTO(item.getProduct());
        this.quantity = item.getQuantity();
        this.size = item.getSize();
    }

}