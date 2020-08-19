package com.shop.shoppify.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductFilterDTO {

    private String idCategory;
    private String idBrand;
    private String dateFrom;
    private String dateTo;
    private String priceFrom;
    private String priceTo;

}
