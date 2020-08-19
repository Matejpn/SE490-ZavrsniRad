package com.shop.shoppify.service;

import com.shop.shoppify.dto.IdNameDTO;

import java.util.List;

public interface BrandService {

    IdNameDTO addBrand(IdNameDTO brandDTO);

    List<IdNameDTO> getBrands();

}
