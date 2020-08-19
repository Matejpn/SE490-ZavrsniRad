package com.shop.shoppify.controller;

import com.shop.shoppify.dto.IdNameDTO;
import com.shop.shoppify.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<IdNameDTO> addBrand(@RequestBody IdNameDTO brandDto) {
        IdNameDTO brand = brandService.addBrand(brandDto);
        return new ResponseEntity<>(brand, HttpStatus.OK);
    }

    @GetMapping("/brands")
    public ResponseEntity<List<IdNameDTO>> getBrands() {
        List<IdNameDTO> brands = brandService.getBrands();
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }
}
