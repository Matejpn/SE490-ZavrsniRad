package com.shop.shoppify.service.impl;

import com.shop.shoppify.dto.request.ProductFilterDTO;
import com.shop.shoppify.dto.request.ProductRequestDTO;
import com.shop.shoppify.dto.request.ProductSizeDTO;
import com.shop.shoppify.dto.response.ProductResponseDTO;
import com.shop.shoppify.enums.GenreEnum;
import com.shop.shoppify.model.Brand;
import com.shop.shoppify.model.Category;
import com.shop.shoppify.model.Image;
import com.shop.shoppify.model.Product;
import com.shop.shoppify.model.Size;
import com.shop.shoppify.repository.BrandRepository;
import com.shop.shoppify.repository.CategoryRepository;
import com.shop.shoppify.repository.ImageRepository;
import com.shop.shoppify.repository.ProductRepository;
import com.shop.shoppify.repository.SizeRepository;
import com.shop.shoppify.service.ProductService;
import com.shop.shoppify.util.ShoppifyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    SizeRepository sizeRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    @Transactional
    public ProductResponseDTO addProduct(ProductRequestDTO productDTO) {
        Product product = new Product();
        if (!GenreEnum.checkGenre(productDTO.getGender())) {
            throw new ShoppifyException(HttpStatus.BAD_REQUEST, "Genre not exist!");
        }

        product.setGender(productDTO.getGender());
        product.setName(productDTO.getName());
        product.setIsDeleted(false);
        product.setPrice(productDTO.getPrice());
        product.setReleaseDate(new Date());
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Category not exist!"));
        product.setCategory(category);
        Brand brand = brandRepository.findById(productDTO.getBrandId())
                .orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Brand not exist!"));
        product.setBrand(brand);
        productRepository.save(product);

        for (ProductSizeDTO productSizeDto : productDTO.getSizes()) {
            Size size = sizeRepository.findById(productSizeDto.getSizeId())
                    .orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Size don't exist!"));
            product.addSize(size, productSizeDto.getCount());
        }
        productRepository.save(product);
        return new ProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO findById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Product not exist!"));
        ProductResponseDTO response = new ProductResponseDTO(product);
        response.setPopularity(productRepository.getPopularityByProductId(product.getId()));
        response.setProductSizes(
                response.getProductSizes().stream().filter(ps -> ps.getCount() > 0).collect(Collectors.toList()));
        return response;
    }

    @Override
    @Transactional
    public ProductResponseDTO editProduct(int id, ProductRequestDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Product not exist!"));
        if (!GenreEnum.checkGenre(productDTO.getGender())) {
            throw new ShoppifyException(HttpStatus.BAD_REQUEST, "Genre not exist!");
        }
        product.setGender(productDTO.getGender());
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Category not exist!"));
        product.setCategory(category);
        Brand brand = brandRepository.findById(productDTO.getBrandId())
                .orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Brand not exist!"));
        product.setBrand(brand);
        product.setPrice(productDTO.getPrice());
        product.setName(productDTO.getName());
        productRepository.removeProductSizesByProductId(product.getId());
        product.setProductSizes(new HashSet<>());

        for (ProductSizeDTO productSizeDto : productDTO.getSizes()) {
            Size size = sizeRepository.findById(productSizeDto.getSizeId())
                    .orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Size don't exist!"));
            product.addSize(size, productSizeDto.getCount());
        }
        productRepository.save(product);
        return new ProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO deleteProduct(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Product not exist!"));
        product.setIsDeleted(true);
        productRepository.save(product);
        return new ProductResponseDTO(product);
    }

    @Override
    @Transactional
    public boolean uploadPhoto(MultipartFile file, int productId) {
        if (file.isEmpty()) {
            throw new ShoppifyException(HttpStatus.BAD_REQUEST, "File does not exist!");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Product not exist!"));
        String[] fileNameSplited = file.getOriginalFilename().split("\\.");
        String fileExtension = "." + fileNameSplited[fileNameSplited.length - 1];
        if (file.getSize() > 5000000) {
            throw new ShoppifyException(HttpStatus.BAD_REQUEST, "File can not exceed 5mb!");
        }
        if (!fileExtension.equals(".png") && !fileExtension.equals(".jpg") && !fileExtension.equals(".jpeg")) {
            throw new ShoppifyException(HttpStatus.BAD_REQUEST, "File must be a image!");
        }
        try {
            File photoFolder = new File(
                    System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "Shoppify-photos");
            if (!photoFolder.exists()) {
                photoFolder.mkdir();
            }
            String imagePath = photoFolder.getAbsolutePath() + File.separator + productId + '-'
                    + (int) (Math.random() * 100000) + fileExtension;
            Path path = Paths.get(imagePath);
            file.transferTo(path);
            productRepository.save(product);
            Image image = new Image();
            image.setPath(imagePath);
            image.setProduct(product);
            imageRepository.save(image);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean removePhoto(int imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ShoppifyException(HttpStatus.NOT_FOUND, "Image doesn't exist!"));
        File file = new File(image.getPath());
        if (file.delete()) {
            imageRepository.delete(image);
            return true;
        }
        return false;
    }

    @Override
    public List<ProductResponseDTO> findAllFiltered(ProductFilterDTO productFilterDTO) {
        List<Product> products = productRepository.findAllFiltered(productFilterDTO.getIdCategory(),
                productFilterDTO.getIdBrand(), productFilterDTO.getDateFrom(), productFilterDTO.getDateTo(),
                productFilterDTO.getPriceFrom(), productFilterDTO.getPriceTo());

        return products.stream().map(ProductResponseDTO::new).map(product -> {
            product.setPopularity(productRepository.getPopularityByProductId(product.getId()));
            product.setProductSizes(
                    product.getProductSizes().stream().filter(ps -> ps.getCount() > 0).collect(Collectors.toList()));
            return product;
        }).collect(Collectors.toList());
    }
}
