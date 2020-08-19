package com.shop.shoppify.service;

import com.shop.shoppify.dto.IdNameDTO;

import java.util.List;

public interface CategoryService {

    IdNameDTO addCategory(IdNameDTO categoryDTO);

    List<IdNameDTO> getCategories();

}
