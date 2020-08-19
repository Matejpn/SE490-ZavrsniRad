package com.shop.shoppify.service.impl;

import com.shop.shoppify.dto.IdNameDTO;
import com.shop.shoppify.model.Brand;
import com.shop.shoppify.repository.BrandRepository;
import com.shop.shoppify.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepository brandRepository;

    @Override
    public IdNameDTO addBrand(IdNameDTO brandDTO) {
        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        brandRepository.save(brand);
        return new IdNameDTO(brand);
    }

    @Override
    public List<IdNameDTO> getBrands() {
        List<Brand> categories = brandRepository.findAll();
        return categories.stream()
                .map(IdNameDTO::new)
                .collect(Collectors.toList());
    }
}
