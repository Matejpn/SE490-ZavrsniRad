package com.shop.shoppify.controller;

import com.shop.shoppify.dto.request.ProductFilterDTO;
import com.shop.shoppify.dto.request.ProductRequestDTO;
import com.shop.shoppify.dto.response.ProductResponseDTO;
import com.shop.shoppify.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTO productDTO) {
        ProductResponseDTO product = productService.addProduct(productDTO);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable("id") int id) {
        ProductResponseDTO product = productService.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductResponseDTO> editProduct(@PathVariable("id") int id, @RequestBody ProductRequestDTO productDTO) {
        ProductResponseDTO product = productService.editProduct(id, productDTO);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    
    @PutMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductResponseDTO> Product(@PathVariable("id") int id) {
        ProductResponseDTO product = productService.deleteProduct(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/upload-photo/{id}")
    public ResponseEntity<Boolean> uploadPhoto(@RequestParam("file") MultipartFile file, @PathVariable("id") int productId) {
        boolean response = productService.uploadPhoto(file, productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/remove-photo/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Boolean> removePhoto(@PathVariable("id") int imageId) {
        boolean response = productService.removePhoto(imageId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponseDTO>> getProductsFiltered(ProductFilterDTO productFilterDTO) {
        List<ProductResponseDTO> products = productService.findAllFiltered(productFilterDTO);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}