package com.shop.shoppify.service;

import com.shop.shoppify.dto.request.ProductFilterDTO;
import com.shop.shoppify.dto.request.ProductRequestDTO;
import com.shop.shoppify.dto.response.ProductResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductResponseDTO addProduct(ProductRequestDTO productDTO);

    ProductResponseDTO findById(int id);

    ProductResponseDTO editProduct(int id, ProductRequestDTO productDTO);

    ProductResponseDTO deleteProduct(int id);

    boolean uploadPhoto(MultipartFile file, int productId);

    boolean removePhoto(int removePhoto);

    List<ProductResponseDTO> findAllFiltered(ProductFilterDTO productFilterDTO);
}
