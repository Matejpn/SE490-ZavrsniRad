package com.shop.shoppify.service.impl;

import com.shop.shoppify.dto.IdNameDTO;
import com.shop.shoppify.model.Category;
import com.shop.shoppify.repository.CategoryRepository;
import com.shop.shoppify.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public IdNameDTO addCategory(IdNameDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        return new IdNameDTO(category);
    }

    @Override
    public List<IdNameDTO> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(IdNameDTO::new)
                .collect(Collectors.toList());
    }

}
