package com.shop.shoppify.dto.response;

import com.shop.shoppify.model.Product;

import org.apache.commons.io.FileUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDTO {

    private int id;

    private String genre;

    private String name;

    private double price;

    private int categoryId;

    private int brandId;

    private String brandName;

    private String categoryName;

    private boolean isDeleted;

    private Date releseDate;

    private int popularity;

    private List<ProductSizeResponseDTO> productSizes;

    private List<ImageResponseDTO> images;

    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.genre = product.getGender();
        this.name = product.getName();
        this.price = product.getPrice();
        this.categoryName = product.getCategory().getName();
        this.categoryId = product.getCategory().getId();
        this.brandId = product.getBrand().getId();
        this.brandName = product.getBrand().getName();
        this.isDeleted = product.getIsDeleted();
        this.releseDate = product.getReleaseDate();
        this.productSizes = product.getProductSizes().stream()
                .sorted((ps1, ps2) -> ps1.getSize().getId() - ps2.getSize().getId()).map(ProductSizeResponseDTO::new)
                .collect(Collectors.toList());
        if (product.getImages() != null) {
            this.images = product.getImages().stream().map(image -> {
                try {
                    return new ImageResponseDTO(image.getId(), convertImageToBase64(image.getPath()));
                } catch (Exception e) {
                    return null;
                }
            }).filter(image -> image != null).collect(Collectors.toList());
        }

    }

    private static String convertImageToBase64(String imagePath) throws IOException {
        byte[] fileContent = FileUtils.readFileToByteArray(new File(imagePath));
        return Base64.getEncoder().encodeToString(fileContent);
    }

}
