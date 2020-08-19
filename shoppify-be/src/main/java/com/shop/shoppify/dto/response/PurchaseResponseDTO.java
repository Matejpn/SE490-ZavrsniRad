package com.shop.shoppify.dto.response;

import com.shop.shoppify.dto.UserDTO;
import com.shop.shoppify.model.Purchase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseResponseDTO {

    private int id;

    private double totalPrice;

    private UserDTO user;

    private List<ItemResponseDTO> items;

    private Date date;

    private Boolean isConfirmed;

    public PurchaseResponseDTO(Purchase purchase) {
        this.id = purchase.getId();
        this.totalPrice = purchase.getTotalPrice();
        this.user = new UserDTO(purchase.getUser());
        this.date = purchase.getDate();
        this.isConfirmed = purchase.getIsConfirmed();
        this.items = purchase.getItems().stream().map(ItemResponseDTO::new).collect(Collectors.toList());
    }

}
