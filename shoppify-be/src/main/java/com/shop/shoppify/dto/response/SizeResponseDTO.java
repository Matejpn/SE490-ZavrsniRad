package com.shop.shoppify.dto.response;

import com.shop.shoppify.model.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SizeResponseDTO {

    private int id;

    private String value;

    private String type;

    public SizeResponseDTO(Size size) {
        this.id = size.getId();
        this.value = size.getValue();
        this.type = size.getType();
    }
}
