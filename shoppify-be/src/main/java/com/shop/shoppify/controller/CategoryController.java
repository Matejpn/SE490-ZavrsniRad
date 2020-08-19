package com.shop.shoppify.controller;

import com.shop.shoppify.dto.IdNameDTO;
import com.shop.shoppify.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<IdNameDTO> addCategory(@RequestBody IdNameDTO categoryDTO) {
        IdNameDTO category = categoryService.addCategory(categoryDTO);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<IdNameDTO>> getCategories() {
        List<IdNameDTO> categories = categoryService.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
