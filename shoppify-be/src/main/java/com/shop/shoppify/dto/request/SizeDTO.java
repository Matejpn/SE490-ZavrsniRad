package com.shop.shoppify.dto.request;

import com.shop.shoppify.model.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SizeDTO {

    private int id;

    private String value;

    private String type;

    public SizeDTO(Size size) {
        this.id = size.getId();
        this.value = size.getValue();
        this.type = size.getType();
    }

}
